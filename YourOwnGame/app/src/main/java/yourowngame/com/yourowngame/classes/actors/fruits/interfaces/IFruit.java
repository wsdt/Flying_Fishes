package yourowngame.com.yourowngame.classes.actors.fruits.interfaces;

import yourowngame.com.yourowngame.R;

/** Never implement IFruit as parent interface, only sub interfaces! */
public interface IFruit {
    /** If you want to use default properties, just do not overwrite them.
     *
     * param HIGHSCORE_REWARD:
     *      appearing time, in which time the fruits are visible on the screen
     *      0.1 indicates all 10 seconds, 1 would be all 100 seconds
     */
    interface DEFAULT_FRUIT_PROPERTIES {
        int ADDITIONAL_GAME_WIDTH = 100;  //So Fruits start GameWidt + 100, so they all dont spawn at the same point!
        float OFF_TIME = 10000f;

        float SPEED_X = 7f;
        float SPEED_Y = 7f; //could be also level dependent :) this will surely be level dependent

        int DEFAULT_ROTATION = 0;
        int HIGHSCORE_REWARD = 15;
    }

    interface AVOCI_FRUIT_PROPERTIES {
        int HIGHSCORE_REWARD = 50;
        float OFF_TIME = 3000f;
    }
    interface MELOON_FRUIT_PROPERTIES {
        int HIGHSCORE_REWARD = 100;
        float OFF_TIME = 2000f;

        int[] IMAGE_FRAMES = new int[] {R.drawable.meloon};
    }
    interface PINAPOS_FRUIT_PROPERTIES {
        int HIGHSCORE_REWARD = 200;
        float OFF_TIME = 5000f;
    }
}
