package yourowngame.com.yourowngame.classes.actors.fruits.interfaces;

public interface IFruit {
    float SPEED_X = 7f;
    float SPEED_Y = 7f; //could be also level dependent :) this will surely be level dependent

    float DEFAULT_ROTATION = 0f;

   // appearing time, in which time the fruits are visible on the screen
   // 0.1 indicates all 10 seconds, 1 would be all 100 seconds
    float OFF_TIME_MELOON = 2000f;   // + 2000
    float OFF_TIME_AVOCIS = 3000f;   // + 3000
    float OFF_TIME_PINAPOS = 5000f;   // + 4000

    interface REWARDS {
        int AVOCIS_FRUIT = 50;
        int MELOONS_FRUIT = 100;
        int PINAPOS_FRUIT = 200;
    }
}
