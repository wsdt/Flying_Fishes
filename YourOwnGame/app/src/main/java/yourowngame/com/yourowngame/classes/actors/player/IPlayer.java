package yourowngame.com.yourowngame.classes.actors.player;


public interface IPlayer {

    /**Fire Rate settings */
    float fireRateMax = 1f;
    float fireRateMin = 0f;

    /**Jump Speed multiplied by MOVE_UP_MULTIPLIER*/
    int MOVE_UP_MULTIPLIER = 2;

    /** Default rotation of bitmap, if e.g. not flying up/down. */
    int DEFAULT_ROTATION = 0;

    /** Rotation of player flying up (simulating by tilting image) */
    int ROTATION_UP = 5;

    /** Rotation of player flying down (simulating by tilting img) */
    int ROTATION_DOWN = -5;

    /** Scaling of image in percentage of original size of bitmap
     * (todo: maybe scale img so this is not necessary?)*/
    float SCALED_WIDTH_PERCENTAGE = 0.35f;
    float SCALED_HEIGHT_PERCENTAGE = 0.35f;
}
