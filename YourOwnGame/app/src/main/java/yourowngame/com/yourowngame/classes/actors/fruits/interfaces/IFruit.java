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
        float SPEED_X = 7f;
        float SPEED_Y = 7f;

        int DEFAULT_ROTATION = 0;
        int Y_UPLIFT = 75; //so fruits will not be half out of screen

    }

    interface AVOCI_FRUIT_PROPERTIES {
        int HIGHSCORE_REWARD = 50;
        float OFF_TIME = 500f;
    }
    interface MELOON_FRUIT_PROPERTIES {
        int HIGHSCORE_REWARD = 100;
        float OFF_TIME = 1000;

        int[] IMAGE_FRAMES = new int[] {R.drawable.meloon};
    }
    interface PINAPOS_FRUIT_PROPERTIES {
        int HIGHSCORE_REWARD = 200;
        float OFF_TIME = 500;
    }
}
