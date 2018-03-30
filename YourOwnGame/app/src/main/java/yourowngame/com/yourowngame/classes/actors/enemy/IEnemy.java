package yourowngame.com.yourowngame.classes.actors.enemy;


public interface IEnemy {
    //max/min values so enemies can be created dynamically
    float SPEED_X_MIN = 1.5f;
    float SPEED_X_MAX = 5; //could be also level dependent :) this will surely be level dependent :)
    float SPEED_Y_MIN = 1.5f;
    float SPEED_Y_MAX = 5;


    int DEFAULT_ROTATION = 0;
    int ROTATION_UP = 10;
    int ROTATION_DOWN = -10;
    float SCALED_WIDTH_PERCENTAGE = 0.35f;
    float SCALED_HEIGHT_PERCENTAGE = 0.35f;
}
