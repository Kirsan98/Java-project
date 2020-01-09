package SampleGame;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Units extends Sprite{
	
	Double productionCost;
	int productionTime;
	int speed;

	/**
	 * Constructor of the class Units
	 * @param layer of the units
	 * @param image of the units
	 * @param x of the units image 
	 * @param y of the units image
	 * @param health of the units
 	 * @param damage of the units
	 * @param productionCost of the units 
	 * @param productionTime of the units
	 * @param speed of the units
	 */
	
	public Units(Pane layer, Image image, double x, double y, int health, int damage,
				 Double productionCost , int productionTime, int speed) {
		super(layer, image, x, y, health, damage);
		this.productionCost = productionCost ;
		this.productionTime = productionTime ;
		this.speed = speed;
	}
	
	/**
	 * Empty constructor to be able to call it in the player class
	 */
	public Units() {
		super();
	}

	/**
	 * Get the cost of productio of a unit
	 * @return the production cost of the units
	 */
	public Double getProductionCost() {
		return productionCost;
	}
	/**
	 * Set the production cost of a unit
	 * @param productionCost
	 */
	public void setProductionCost(Double productionCost) {
		this.productionCost = productionCost;
	}
	
	/**
	 * Get the time of production of a unit.
	 * @return production time
	 */
	public int getProductionTime() {
		return productionTime;
	}

	/**
	 * Set the production time of a unit
	 * @param productionTime
	 */
	public void setProductionTime(int productionTime) {
		this.productionTime = productionTime;
	}

	/**
	 * Get the speed of a unit
	 * @return speed of unit
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Set the speed of a unit 
	 * @param speed 
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Target an enemy of type Castle and move the x and y of the unit image to the x and y of the enemy image
	 * @param enemy
	 */
	public void move(Castle enemy) {
		if(x<enemy.getX()) {
			if(getHealth()==Settings.KNIGHTHEALTH) {
				x +=Settings.KNIGHTSPEED ;
			}
			if(getHealth()==Settings.PIKEMANHEALTH) {
				x +=Settings.PIKEMANSPEED ;
			}
			if(getHealth()==Settings.ONAGEREALTH) {
				x +=Settings.ONAGERSPEED;
			}
		}
		if(y<enemy.getY()) {
			if(getHealth()==Settings.KNIGHTHEALTH) {
				y +=Settings.KNIGHTSPEED;
			}
			if(getHealth()==Settings.PIKEMANHEALTH) {
				y +=Settings.PIKEMANSPEED;
			}
			if(getHealth()==Settings.ONAGEREALTH) {
				y +=Settings.ONAGERSPEED;
			}
		}
		if(x>enemy.getX()) {
			if(getHealth()==Settings.KNIGHTHEALTH) {
				x -=Settings.KNIGHTSPEED;
			}
			if(getHealth()==Settings.PIKEMANHEALTH) {
				x -=Settings.PIKEMANSPEED;
			}
			if(getHealth()==Settings.ONAGEREALTH) {
				x -=Settings.ONAGERSPEED;
			}
		}
		if(y>enemy.getY()) {
			if(getHealth()==Settings.KNIGHTHEALTH) {
				y -=Settings.KNIGHTSPEED;
			}
			if(getHealth()==Settings.PIKEMANHEALTH) {
				y -=Settings.PIKEMANSPEED;
			}
			if(getHealth()==Settings.ONAGEREALTH) {
				y -=Settings.ONAGERSPEED;
			}
		}
		// Check if the x and y of the unit are next to the enemy x and y about 4 pixels to remove the units from the layer
		if(x-enemy.getX()<4 && y-enemy.getY()<4) {	 
			remove();
		}
	}
}
