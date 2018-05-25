package yourowngame.com.yourowngame.classes.actors.player.interfaces;


public interface IPlayer {

    /**Fire Rate settings */
    float fireRateMax = 1f;
    float fireRateMin = 0f;

    /**Jump Speed multiplied by MOVE_UP_MULTIPLIER*/
    int MOVE_UP_MULTIPLIER = 2;

    /** Rotation of player flying up (simulating by tilting image) */
    int ROTATION_UP = 5;

    /** Rotation of player flying down (simulating by tilting img) */
    int ROTATION_DOWN = -5;

    /** Scaling of image in percentage of original size of bitmap
     * (todo: maybe scale img so this is not necessary?)*/
    float SCALED_WIDTH_PERCENTAGE = 1f;
    float SCALED_HEIGHT_PERCENTAGE = 1f;
}
