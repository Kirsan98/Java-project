package SampleGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Sprite {

    private ImageView imageView;

    private Pane layer;

    protected double x;
    protected double y;

    protected double dx;
    protected double dy;

    private int health;
    private double damage;

    private boolean removable = false;

    private double w;
    private double h;

    /**
     * 
     * @param layer of the sprite
     * @param image of the sprite
     * @param x of the sprite
     * @param y of the sprite 
     * @param health of the sprite 
     * @param damage of the sprite 
     */
    public Sprite(Pane layer, Image image, double x, double y, int health, double damage) {

        this.layer = layer;
        this.x = x;
        this.y = y;

        this.health = health;
        this.damage = damage;

        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);

        this.w = image.getWidth();
        this.h = image.getHeight();

        addToLayer();

    }
    
    /**
     * Empty constructor 
     */
    public Sprite() {
    }

    /**
     * Add the image of the sprite to the layer
     */
    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    /**
     * Remove the image of the sprite from the layer
     */
    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }
    
    /**
     * Get the x of the sprite
     * @return x (double)
     */
    public double getX() {
        return x;
    }

    /**
     * Set the x of the sprite 
     * @param x (double)
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Get the y of the sprite
     * @return y (double)
     */
    public double getY() {
        return y;
    }

    /**
     * Set y of the sprite
     * @param y (double)
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Get the DX of the sprite
     * @return Dx (double) 
     */
    public double getDx() {
        return dx;
    }

    /**
     * Set the Dx of the sprite
     * @param Dx (double) 
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Get the Dy of the sprite 
     * @return Dy (double)
     */
    public double getDy() {
        return dy;
    }

    /**
     * Set the Dy of the sprite
     * @param dy (double)
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Get the health of the sprite
     * @return health (int)
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get the damage of the sprite 
     * @return damage (double)
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Set the damage of the sprite 
     * @param damage (double)
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     * Check if removable is true or false
     * @return true if removable is true else return false
     */
    public boolean isRemovable() {
        return removable;
    }

    /**
     * Check if the health is upper than 0
     * @return true if health is upper than 0 else return false
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Get the view of the image of the sprite
     * @return the image 
     */
    protected ImageView getView() {
        return imageView;
    }

    /**
     * Set the view of the image of the sprite
     * @param image 
     */
    protected void setView(Image image) {
        this.imageView = new ImageView(image);
    }
    
	/**
	 * Set the health of the sprite
	 * @param health of the sprite
	 */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Relocate the x and the y of the image of the sprite
     */
    public void updateUI() {
        imageView.relocate(x, y);
    }

    /**
     * Get the width of the sprite
     * @return width 
     */
    public double getWidth() {
        return w;
    }

    /**
     * Get the height of the sprite
     * @return height
     */
    public double getHeight() {
        return h;
    }

    /**
     * Get the center x of the sprite
     * @return the center x of the sprite
     */
    public double getCenterX() {
        return x + w * 0.5;
    }

    /**
     * Get the center y of the sprite
     * @return the center y of the sprite
     */
    public double getCenterY() {
        return y + h * 0.5;
    }

    /**
     * Reduces sprite health based on damage received
     * @param sprite
     */
    public void damagedBy(Sprite sprite) {
        health -= sprite.getDamage();
    }

    /**
     * Set the attribute removable to true  
     */
    public void remove() {
        this.removable = true;
    }
    
    
}
