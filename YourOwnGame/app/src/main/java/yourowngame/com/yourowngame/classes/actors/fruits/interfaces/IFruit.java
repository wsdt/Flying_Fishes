package yourowngame.com.yourowngame.classes.actors.fruits.interfaces;

public interface IFruit {
    float SPEED_X_MIN = 5f;
    float SPEED_X_MAX = 10f; //could be also level dependent :) this will surely be level dependent :)
    float SPEED_Y_MIN = 5f;
    float SPEED_Y_MAX = 10f;

   // appearing time, in which time the fruits are visible on the screen
   // 0.1 indicates all 10 seconds, 1 would be all 100 seconds
    float appearingTimeMelons  = 0.3f;   //  30 secs
    float appearingTimeAvocis  = 0.5f;   //  50 secs
    float appearingTimePinapos = 0.9f;   //    90 secs

    interface REWARDS {
        int AVOCIS_FRUIT = 50;
        int MELOONS_FRUIT = 100;
        int PINAPOS_FRUIT = 200;
    }
}
