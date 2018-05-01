package yourowngame.com.yourowngame.classes.actors.enemy.interfaces;


public interface IEnemy {

    //GENERAL SETTINGS
    int ADDITIONAL_GAME_WIDTH = 750;  //So Enemys start GameWidt + 500, so they all dont spawn at the same point!


    //SPEED VALUES
    float SPEED_X_MIN = 5f;
    float SPEED_X_MAX = 10f; //could be also level dependent :) this will surely be level dependent :)
    float SPEED_Y_MIN = 5f;
    float SPEED_Y_MAX = 10f;

    //ROCKET SPEED
    float ROCKET_SPEED_MIN = 10;
    float ROCKET_SPEED_MAX = 15;

    //ANIMATION VALUES
    int DEFAULT_ROTATION = 0;
    int ROTATION_UP = 10;
    int ROTATION_DOWN = -10;
    float SCALED_WIDTH_PERCENTAGE = 0.35f;
    float SCALED_HEIGHT_PERCENTAGE = 0.35f;

    /*HIGHSCORE REWARDS*/
    interface REWARDS {
        int BOBA_ENEMY = 100;
        int DYNAMITE_ENEMY = 150;
        int HAPPEN_ENEMY = 75;
        int ROCKETFISH_ENEMY = 50;
    }
}
