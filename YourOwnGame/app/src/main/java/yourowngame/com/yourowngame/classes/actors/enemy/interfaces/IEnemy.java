package yourowngame.com.yourowngame.classes.actors.enemy.interfaces;


public interface IEnemy {
    //max/min values so enemies can be created dynamically
    float SPEED_X_MIN = 5f;
    float SPEED_X_MAX = 10f; //could be also level dependent :) this will surely be level dependent :)
    float SPEED_Y_MIN = 5f;
    float SPEED_Y_MAX = 10f;

    //for giving our game some difficulty, the rocketEnemys should be really fast (an be increasing if game gets further!)
    float ROCKET_SPEED_MIN = 10;
    float ROCKET_SPEED_MAX = 15;


    int DEFAULT_ROTATION = 0;
    int ROTATION_UP = 10;
    int ROTATION_DOWN = -10;
    float SCALED_WIDTH_PERCENTAGE = 0.35f;
    float SCALED_HEIGHT_PERCENTAGE = 0.35f;

    /*Highscore rewards for enemies here which are used in getReward() */
    interface REWARDS {
        int BOBA_ENEMY = 100;
        int DYNAMITE_ENEMY = 150;
        int HAPPEN_ENEMY = 75;
        int ROCKETFISH_ENEMY = 50;
    }
}
