package SampleGame;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Player extends Sprite {

	private double minX;
	private double maxX;
	private double minY;
	private double maxY;

	private Input input;
	private Units unite;

	// List of ost
	private List<Units> piquiers= new ArrayList<>();
	private List<Units> chevaliers= new ArrayList<>();
	private List<Units> onagres= new ArrayList<>();

	// Number of units in the reserve
	private int nbPikeman;
	private int nbKnight;
	private int nbOnager;

	private String Dukename;
	private Double treasure ;
	private int level = 1 ;

	/**
	 * 
	 * @param layer
	 * @param image of the player
	 * @param x image position player
	 * @param y image position player
	 * @param health of the player
	 * @param damage of the player
	 * @param input of the player
	 * @param duc player duke
	 * @param treasure The treasure of our player
	 */
	public Player(Pane layer, Image image, double x, double y, int health,
				  double damage, Input input,String duc, Double treasure) {

		super(layer, image, x, y, health, damage);
		this.Dukename = duc ;
		this.treasure = treasure ;
		this.input = input;

		init();
	}
	
	/**
	 * calculate movement bounds of the player ship and allow half of the player to be outside of the screen
	 */
	private void init() {
		minX = 0 - getWidth() / 2.0;
		maxX = Settings.SCENE_WIDTH - getWidth() / 2.0;
		minY = 0 - getHeight() / 2.0;
		maxY = Settings.SCENE_HEIGHT - getHeight();
	}
	
	/**
	 * function returns the player duke
	 * @return duke name
	 */
	public String getDukename() {
		return Dukename;
	}
	/**
	 * function return the level of castle which disposes the duke
	 * @return level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * set the level of the player
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * set the duke name of the player
	 * @param dukename
	 */
	public void setDukename(String dukename) {
		this.Dukename = dukename;
	}
	/**
	 * return the treasure of the player
	 * @return treasure
	 */
	public Double getTreasure() {
		return treasure;
	}
	/**
	 * set the player treasure's
	 * @param treasure
	 */
	public void setTreasure(Double treasure) {
		this.treasure = treasure;
	}

	/**
	 * return the number of player pikeman's
	 * @return number of Pikeman
	 */
	public int getNbPikeman() {
		return nbPikeman;
	}
	/**
	 * set the number of the player pikman's
	 * @param nbPikeman
	 */
	public void setNbPikeman(int nbPikeman) {
		this.nbPikeman = nbPikeman;
	}
	/**
	 * return player knight number
	 * @return nbknight
	 */

	public int getNbKnight() {
		return nbKnight;
	}
	/**
	 * set the number of the player pikman's
	 * @param nbknight
	 */
	public void setNbKnight(int nbKnight) {
		this.nbKnight = nbKnight;
	}
	/**
	 * return  player onager number
	 * @return nbOnagre
	 */
	public int getNbOnager() {
		return nbOnager;
	}
	/**
	 * set the number of onager
	 * @param nbOnagre
	 */
	public void setNbOnager(int nbOnager) {
		this.nbOnager = nbOnager;
	}
	/**
	 * 
	 * @return list of pikemans
	 */
	public List<Units> getPikeman() {
		return piquiers;
	}
	/**
	 * set the list of pikemans
	 * @param pikemans
	 */
	public void setPikeman(List<Units> pikemans) {
		this.piquiers = pikemans;
	}
	/**
	 * 
	 * @return the list of knights
	 */
	public List<Units> getKnights() {
		return chevaliers;
	}
	/**
	 * set the list of knights
	 * @param knights
	 */
	public void setKnights(List<Units> knights) {
		this.chevaliers = knights;
	}
	/**
	 * @return the list of onagers
	 * @return onagers
	 */

	public List<Units> getOnagers() {
		return onagres;
	}
	/**
	 * set the list of knights
	 * @param onagers
	 */
	public void setOnagers(List<Units> onagers) {
		this.onagres = onagers;
	}
	
	/**
	 * production of pikemans 
	 */
	public void setProductPikeman(){
		//if we have not the production cost
		if(this.treasure<100){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("You don't have enough gold to product this unit!");

			alert.showAndWait();
		}
		else{
			unite = new Units();
			unite.setProductionCost(Settings.PIKEMANECOST);
			unite.setProductionTime(Settings.PIKEMANTIME);
			unite.setSpeed(Settings.PIKEMANSPEED);
			unite.setDamage(Settings.PIKEMANDAMAGE);
			unite.setHealth(Settings.PIKEMANHEALTH);
			this.treasure = this.treasure - unite.getProductionCost() ;
			this.nbPikeman++;
		}
	}

	/**
	 * knights production
	 */
	public void setProductKnight() {
		//if we have not the production cost
		if(this.treasure<500){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("You don't have enough gold to product this unit!");

			alert.showAndWait();
		}
		else{
			unite = new Units();
			unite.setProductionCost(Settings.KNIGHTCOST);
			unite.setProductionTime(Settings.KNIGHTTIME);
			unite.setSpeed(Settings.KNIGHTSPEED);
			unite.setDamage(Settings.KNIGHTDAMAGE);
			unite.setHealth(Settings.KNIGHTHEALTH);
			this.treasure = this.treasure - unite.getProductionCost();
			this.nbKnight++;
		}
	}
	
	/**
	 * Onagers production
	 */
	public void setProductOnager() {
		if(this.treasure<1000){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("You don't have enough gold to product this unit!");

			alert.showAndWait();
		}
		else{
			unite = new Units();
			unite.setProductionCost(Settings.ONAGERCOST);
			unite.setProductionTime(Settings.ONAGERTIME);
			unite.setSpeed(Settings.ONAGERSPEED);
			unite.setDamage(Settings.ONAGERDAMAGE);
			unite.setHealth(Settings.ONAGEREALTH);
			this.treasure = this.treasure - unite.getProductionCost();
			this.nbOnager++;
		}
	}
}