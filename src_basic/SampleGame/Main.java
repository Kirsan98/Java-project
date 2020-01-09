package SampleGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.lang.Double;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Main extends Application {

	private Pane playfieldLayer;
	private Image playerImage;
	private Image enemyImage;
	private Image pikemanImage;
	private Image knightImage;
	private Image onagerImage;
	private Castle target;
	Alert alert;
	private Player player;
	Units randomAttacker;
	private List<Units> allieAttackers = new ArrayList<>();
	private List<Units> knights = new ArrayList<>();
	private List<Units> onagers = new ArrayList<>();
	private List<Units> pikemans = new ArrayList<>();
	private List<Castle> enemies = new ArrayList<>();
	private List<Player> playerenemy = new ArrayList<>();
	private Text scoreMessage = new Text();
	private Scene scene;
	private Input input;
	private AnimationTimer gameLoop;
	private boolean Pause = false;
	private Double turn = 0.0 ;
	private int turnbis;
	private Double treasure = 0.0 ;
	private double tab[][] = {{0.1,0.1}, {0.1, 0.6}, {0.8,0.1}, {0.8, 0.6}, {0.2, 0.3}};
	int i , j;
	Group root;

	public void start(Stage primaryStage) {

		root = new Group();
		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT + Settings.STATUS_BAR_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		// create layers
		playfieldLayer = new Pane();
		root.getChildren().add(playfieldLayer);
		
		loadGame();

		gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				processInput(input, now);

				if(!Pause)
				{	
					// set treasure win per tour
					turnbis=turn.intValue();
					turn +=0.02;
					if(turn.intValue()>turnbis) {
						player.setTreasure(player.getTreasure()+(player.getLevel()*10));
						enemies.forEach(sprite -> sprite.setTreasure(sprite.getTreasure()+10));
						playerenemy.forEach(sprite -> sprite.setTreasure(sprite.getTreasure()+10));
					}

					// update sprites in scene
					player.updateUI();

					// movement of the image
					allieAttackers.forEach(sprite -> sprite.move(target));
					allieAttackers.forEach(sprite -> sprite.updateUI());
					enemies.forEach(sprite -> sprite.updateUI()); 
					playerenemy.forEach(sprite -> sprite.updateUI());

					// remove removables from list, layer, etc
					removeSprites(pikemans);
					removeSprites(onagers);
					removeSprites(knights);
					removeSprites(enemies);
					
					// update score, health, etc
					update();

					// end of game
					if(playerenemy.size()==Settings.NB_CASTLE_ENEMY){
						gameOver();
					}
				}
			}

			private void processInput(Input input, long now) {
				if (input.isExit()) {
					Platform.exit();
					System.exit(0);
				}  else if (input.isPause() && Pause == false) {
					Pause = true;

				} else if(input.isUnpause() && Pause == true)
				{
					Pause = false;
				}
			}
		};
		gameLoop.start();
	}
	
	/**
	 * Load the game with the different resources needed
	 */
	private void loadGame() {
		playerImage = new Image(getClass().getResource("/images/castle.png").toExternalForm(), (Settings.SCENE_WIDTH/7), (Settings.SCENE_HEIGHT/7), true, true);
		enemyImage = new Image(getClass().getResource("/images/castle_enemy.png").toExternalForm(), (Settings.SCENE_WIDTH/7), (Settings.SCENE_HEIGHT/7), true, true);
		pikemanImage = new Image(getClass().getResource("/images/piquier.png").toExternalForm(), 40, 40, true, true);
		knightImage = new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 40, 40, true, true);
		onagerImage = new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 40, 40, true, true);

		input = new Input(scene);
		input.addListeners();

		createPlayer();
		createStatusBar();

		// add random enemies
		while(enemies.size()<Settings.NB_CASTLE_ENEMY) {
			spawnEnemies();
		}
	}
	
	/**
	 * Create status bar with the different informations needed
	 */
	public void createStatusBar() {
		HBox statusBar = new HBox();
		scoreMessage.setText("Click on a Castle\n");
		statusBar.getChildren().addAll(scoreMessage);
		statusBar.getStyleClass().add("statusBar");
		statusBar.relocate(0, Settings.SCENE_HEIGHT);
		statusBar.setPrefSize(Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT);
		root.getChildren().add(statusBar);
		buildOst();
	}
	
	/**
	 * Create the button "Build ost" to build an ost
	 */
	private void buildOst() {
		{
			Button button = new Button("Build ost");
			button.setLayoutX(700);
			button.setLayoutY(575);
			root.getChildren().addAll(button);
			button.setOnContextMenuRequested(e -> {
				ContextMenu contextMenu = new ContextMenu();
				MenuItem piq = new MenuItem("Piquier");
				MenuItem chev= new MenuItem("Chevalier");
				MenuItem orn= new MenuItem("Ornagre");
				piq.setOnAction(evt -> setdimPikeman(turn.intValue()));
				chev.setOnAction(evt -> setdimKnight(turn.intValue()));
				orn.setOnAction(evt -> setdimOnager(turn.intValue()));
				contextMenu.getItems().addAll(piq, chev, orn);
				contextMenu.show(player.getView(), e.getScreenX(), e.getScreenY());
			});
		}
	}
	
	/**
	 * Create a new Units with the knight settings and decrements the number of knight in the reserve
	 * @param turnbis (needed for the time production)
	 */
	public void setdimKnight(int turnbis) {
		if (player.getNbKnight()<1){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning!");
			alert.setHeaderText(null);
			alert.setContentText("You don't have this troop in reserve!");

			alert.showAndWait();
		}
		else{
			if(knights.size()+pikemans.size()+onagers.size()>=3) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning!");
				alert.setHeaderText(null);
				alert.setContentText("You can't send more than 3 troop per the door!");

				alert.showAndWait();
			}
			else {
				player.setNbKnight(player.getNbKnight()-1);
				while(turn.intValue()-turnbis<20) {
					System.out.println("");
					turn +=0.0002;
				}
				Units chevalier = new Units(playfieldLayer, knightImage, player.getCenterX()-10, player.getCenterY()+18, Settings.KNIGHTHEALTH, Settings.KNIGHTDAMAGE,
						Settings.KNIGHTCOST, Settings.KNIGHTTIME, Settings.KNIGHTSPEED);
				knights.add(chevalier);
			}
		}
	}
	/**
	 * Create a new Units with the onager settings and decrements the number of onager in the reserve
	 * @param turnbis (needed for the time production)
	 */
	public void setdimOnager(int turnbis) {
		if(player.getNbOnager()<1){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning!");
			alert.setHeaderText(null);
			alert.setContentText("You don't have this troop in reserve!");

			alert.showAndWait();
		}
		else{
			if(knights.size()+pikemans.size()+onagers.size()>=3) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning!");
				alert.setHeaderText(null);
				alert.setContentText("You can't send more than 3 troop per the door!");

				alert.showAndWait();
			}
			else {
				while(turn.intValue()-turnbis<50) {
					System.out.println("");
					turn +=0.0002;
				}
				player.setNbOnager(player.getNbOnager()-1);
				Units onagre = new Units(playfieldLayer, onagerImage, player.getCenterX()-8, player.getCenterY()+22, Settings.ONAGEREALTH, Settings.ONAGERDAMAGE,
						Settings.ONAGERCOST, Settings.ONAGERTIME, Settings.ONAGERSPEED);
				onagers.add(onagre);
			}
		}
	}

	/**
	 * Create a new Units with the pikeman settings and decrements the number of pikeman in the reserve
	 * @param turnbis (needed for the time production)
	 */
	public void setdimPikeman(int turnbis) {
		if(player.getNbPikeman()<1){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning!");
			alert.setHeaderText(null);
			alert.setContentText("You don't have this troop in reserve!");

			alert.showAndWait();
		}
		else{
			if(knights.size()+pikemans.size()+onagers.size()>=3) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning!");
				alert.setHeaderText(null);
				alert.setContentText("You can't send more than 3 troop per the door!");

				alert.showAndWait();
			}
			else {
				while(turn.intValue()-turnbis<5) {
					System.out.println("");
					turn +=0.0002;
				}
				player.setNbPikeman(player.getNbPikeman()-1);
				Units piquier = new Units(playfieldLayer, pikemanImage, player.getCenterX()-12, player.getCenterY()+20, Settings.PIKEMANHEALTH, Settings.PIKEMANDAMAGE,
						Settings.PIKEMANECOST, Settings.PIKEMANTIME, Settings.PIKEMANSPEED);
				pikemans.add(piquier);
			}
		}
	}
	/**
	 * Create the player with the appropriate settings and build the different buttons needed
	 */
	private void createPlayer() {
		double x = (Settings.SCENE_WIDTH-playerImage.getWidth()+1)/2;
		double y = (Settings.SCENE_HEIGHT-playerImage.getHeight()+1)/2;
		player = new Player(playfieldLayer, playerImage, x, y, Settings.PLAYER_HEALTH, Settings.PLAYER_DAMAGE,input,"",treasure);

		player.getView().setOnMousePressed(e -> {
			System.out.println("Click on player");
			e.consume();
		});
		player.setNbPikeman(3);
		player.setNbKnight(2);
		player.setNbOnager(1);
		player.setLevel(1);
		player.setTreasure(0.0);
		player.setDukename("Player 1");

		player.getView().setOnContextMenuRequested(e -> {
			ContextMenu contextMenu = new ContextMenu();
			MenuItem piq = new MenuItem("Piquier");
			MenuItem chev= new MenuItem("Chevalier");
			MenuItem orn= new MenuItem("Ornagre");
			MenuItem levelup = new MenuItem("Level up");
			levelup.setOnAction(evt -> levelUp(turn.intValue()));
			piq.setOnAction(evt -> player.setProductPikeman());
			chev.setOnAction(evt -> player.setProductKnight());
			orn.setOnAction(evt -> player.setProductOnager());
			contextMenu.getItems().addAll(piq, chev, orn,levelup);
			contextMenu.show(player.getView(), e.getScreenX(), e.getScreenY());
		});
	}
	
	/**
	 * Spawn the different enemies on the map with the appropriate settings and build the button attack needed
	 */
	private void spawnEnemies() {
		j = 0;
		double x =  tab[i][j]*(Settings.SCENE_WIDTH+Settings.MINIMAL_DISTANCE - enemyImage.getWidth());j++;
		double y = 	tab[i][j]*(Settings.SCENE_WIDTH+Settings.MINIMAL_DISTANCE - enemyImage.getHeight());i++;
		Castle enemy = new Castle(playfieldLayer, enemyImage, x, y, 1, 1, (double) 0, "Bot");
		enemies.add(enemy);
		enemy.getView().setOnMousePressed(e -> {
			System.out.println("Click on player");
			e.consume();
		});
		
		// Set the units in the reserve of the castle
		enemy.setNbPikeman(3);
		enemy.setNbKnight(2);
		enemy.setNbOnager(1);
		enemy.setTreasure(0.0);

		enemy.getView().setOnContextMenuRequested(e -> {
			ContextMenu contextMenu = new ContextMenu();
			MenuItem att = new MenuItem("Attack");
			att.setOnAction(evt -> attack(enemy));
			contextMenu.getItems().addAll(att);
			contextMenu.show(enemy.getView(), e.getScreenX(), e.getScreenY());
		});
	}

	/**
	 * Generate a random integer between two bounds
	 * @param borneInf
	 * @param borneSup
	 * @return the random integer
	 */
	int genererInt(int borneInf, int borneSup){
		   Random random = new Random();
		   int nb;
		   nb = borneInf+random.nextInt(borneSup-borneInf);
		   return nb;
		}
	
	/**
	 * Set the different settings to attack the enemy
	 * @param enemy to target
	 */
	private void attack(Castle enemy)
	{
		// target the enemy to move the units
		target = enemy ;

		// Add the different units ready to attack 
		pikemans.forEach(sprite -> allieAttackers.add(sprite));
		onagers.forEach(sprite -> allieAttackers.add(sprite));
		knights.forEach(sprite -> allieAttackers.add(sprite));
		
		if(allieAttackers.size()!=0) {
			
			// Decrement the enemy reserve
			enemy.setNbKnight(enemy.getNbKnight()-knights.size());
			enemy.setNbOnager(enemy.getNbOnager()-onagers.size());
			enemy.setNbPikeman(enemy.getNbPikeman()-pikemans.size());
			
			//TOCOMPLETE HEALTH OF UNITS
			
			// remove the units arrived at destination
			for (int i=0 ; i<allieAttackers.size() ; i++) {
				if (allieAttackers.get(i).isRemovable()==true){
					allieAttackers.remove(i);
				}
			}
			// won the enemy castle 
			if(enemy.getNbKnight()==0 && enemy.getNbOnager()==0 && enemy.getNbPikeman()==0) {
				Player newplayer = new Player(playfieldLayer, playerImage, enemy.getX(), enemy.getY(), Settings.PLAYER_HEALTH, Settings.PLAYER_DAMAGE,input,player.getDukename(),enemy.getTreasure());
				playerenemy.add(newplayer);
				enemy.remove();
			}
		}else{
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning!");
			alert.setHeaderText(null);
			alert.setContentText("You can't attack without troop!");

			alert.showAndWait();
				
		}
	}

	/**
	 * Check if we can remove sprite from layer and from list
	 * @param spriteList to remove
	 */
	private void removeSprites(List<? extends Sprite> spriteList) {
		Iterator<? extends Sprite> iter = spriteList.iterator();
		while (iter.hasNext()) {
			Sprite sprite = iter.next();

			if (sprite.isRemovable()) {
				// remove from layer
				sprite.removeFromLayer();
				// remove from list
				iter.remove();
			}
		}
	}

	/**
	 * Create a message when the game is end and stop the game
	 */
	private void gameOver() {
		HBox hbox = new HBox();
		hbox.setPrefSize(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		hbox.getStyleClass().add("message");
		Text message = new Text();
		message.getStyleClass().add("message");
		message.setText("Well played!");
		hbox.getChildren().add(message);
		root.getChildren().add(hbox);
		gameLoop.stop();
	}
	
	/**
	 * Level up the castle of the player 
	 * @param turnbis (time to wait (turn) for the level up)
	 */
	public void levelUp(int turnbis) {
		if(player.getTreasure()>=(1000*player.getLevel())){
			while(turn.intValue()-turnbis<100+(50*player.getLevel())) {
				break; 
			}
			player.setTreasure(player.getTreasure()-(1000*player.getLevel()));
			player.setLevel(player.getLevel()+1);
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION); 
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("You don't have enough gold to level up!");

			alert.showAndWait();
		}
	}
	
	/**
	 * Update the different attribute of the status bar 
	 */
	private void update() {
		player.getView().setOnMousePressed(e -> {
			
			scoreMessage.setText("Turn: "+turn.intValue()+"    Duc : "+player.getDukename()+"   Treasure"+"("+player.getLevel()+"x10/tours): "+player.getTreasure().intValue()+"   Level: "+player.getLevel()+"    Piquier: "+player.getNbPikeman() +"   Chevalier: "+ player.getNbKnight() +"   Onagre: "+ player.getNbOnager());
		});
		enemies.forEach(sprite -> sprite.getView().setOnMousePressed(e->{
			scoreMessage.setText("Duc : Bot"+"    Treasure : "+sprite.getTreasure().intValue()+"     Piquier: "+ sprite.getNbPikeman()+"   Chevalier: "+ sprite.getNbKnight() +"   Onagre: "+ sprite.getNbOnager());
		}));
		playerenemy.forEach(sprite -> sprite.getView().setOnMouseEntered(e->{
			scoreMessage.setText("Duc : Bot"+"     Treasure : "+treasure.intValue()+"     Piquier: "+ sprite.getNbPikeman()+"   Chevalier: "+ sprite.getNbKnight() +"   Onagre: "+ sprite.getNbOnager());
		}));
	}

	/**
	 * Launch the game 
	 * @param args of the game 
	 */
	public static void main(String[] args) {
		launch(args);
	}
}