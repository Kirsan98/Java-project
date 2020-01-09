package SampleGame;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Castle extends Sprite {

	private double maxY;
	private String duc;
	private Double treasure ;

	private int nbPikeman;
	private int nbKnight;
	private int nbOnager;

	/**
	 * Constructor of the class Castle
	 * @param layer of the castle 
	 * @param image of a castle 
	 * @param x (abscissa of the image)
	 * @param y (ordered of the image)
	 * @param health of a castle
	 * @param level of a castle
	 * @param treasure of a castle
	 * @param dukename, name of a castle
	 */
	public Castle(Pane layer, Image image, double x, double y, int health,int level, Double treasure,String dukename) {
		super(layer, image, x, y, health, level);
		this.duc = dukename ;
		this.treasure = treasure ;
		maxY = Settings.SCENE_HEIGHT - image.getHeight();
	}
	
	/**
	 * Set the duke name of a castle
	 * @param duke
	 */
	public void setDuc(String duke) {
		this.duc = duke;
	}

	/**
	 * 
	 * @return the actual quantities of treasure of the castle
	 */
	public Double getTreasure() {
		return treasure;
	}
	
	/**
	 * 
	 * @param treasure
	 * Set the treasure quantities of the castle
	 */
	public void setTreasure(Double treasure) {
		this.treasure = treasure;
	}
	
	/**
	 * 
	 * @return the number of pikeman in the castle
	 */
	public int getNbPikeman() {
		return nbPikeman;
	}
	
	/**
	 * 
	 * @param nbPikeman
	 * Set the number of Pikeman in the castle
	 */
	public void setNbPikeman(int nbPikeman) {
		this.nbPikeman = nbPikeman;
	}

	/**
	 * 
	 * @return the number of knights in the castle
	 */
	public int getNbKnight() {
		return nbKnight;
	}

	/**
	 * 
	 * @param nbKnight
	 * Set the number of Knight in the castle
	 */
	public void setNbKnight(int nbKnight) {
		this.nbKnight = nbKnight;
	}
	
	/**
	 * 
	 * @return the number of onager in the castle
	 */
	public int getNbOnager() {
		return nbOnager;
	}

	/**
	 * 
	 * @param nbOnager
	 * Set the number of Onager in the castle
	 */
	public void setNbOnager(int nbOnager) {
		this.nbOnager = nbOnager;
	}
}
