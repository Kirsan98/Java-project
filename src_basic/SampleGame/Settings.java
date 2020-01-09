package SampleGame;

public class Settings {

	// Scene settings
	public static final double SCENE_WIDTH = 1050;
    public static final double SCENE_HEIGHT = 800;
	public static final double STATUS_BAR_HEIGHT = 50;

	// Player settings
    public static final double PLAYER_SPEED = 4.0;
    public static final int    PLAYER_HEALTH = 3;
    public static final double PLAYER_DAMAGE = 1;

    // Enemy spawn settings
    public static final int ENEMY_SPAWN_RANDOMNESS = 100;
    public static final int NB_CASTLE_ENEMY = 5 ;
    public static final int MINIMAL_DISTANCE = 10 ;
    
    // Pikeman settings
    public static final Double PIKEMANECOST = 100.0; 
    public static final int PIKEMANTIME = 5;
    public static final int PIKEMANSPEED = 2;
    public static final int PIKEMANHEALTH = 1;
    public static final int PIKEMANDAMAGE = 1;
    
    // Knight settings
    public static final Double KNIGHTCOST = 500.0;
    public static final int KNIGHTTIME= 20; 
    public static final int KNIGHTSPEED = 4; 
    public static final int KNIGHTHEALTH = 3;
    public static final int KNIGHTDAMAGE = 5;
    
    // Onager settings
    public static final Double ONAGERCOST = 1000.0;
    public static final int ONAGERTIME = 50;
    public static final int ONAGERSPEED = 1;
    public static final int ONAGEREALTH = 5;
    public static final int ONAGERDAMAGE = 10;
}
