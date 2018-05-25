package yourowngame.com.yourowngame.classes.actors.enemy.interfaces;

import yourowngame.com.yourowngame.R;

/** Please only implement subInterfaces and not this parent interface! */
public interface IEnemy {
    /**
     * HIGHSCORE_REWARD --> Used for modifying highscore, if e.g. enemy has been shot.
     *
     * If you want to use default properties in enemy subclasses, just do not overwrite them.
     * */
    interface PROPERTIES {
        interface DEFAULT {
            int ADDITIONAL_GAME_WIDTH = 750;  //So Enemys start GameWidt + 500, so they all dont spawn at the same point!
            int HIGHSCORE_REWARD = 100;

            float SPEED_X_MIN = 5f;
            float SPEED_X_MAX = 10f; //TODO: could be also level dependent :) this will surely be level dependent :)
            float SPEED_Y_MIN = 5f; //TODO: Just use these here and add a lvlConstant (e.g. a difficulty e.g. *1.1 etc.
            float SPEED_Y_MAX = 10f;
        }

        /** Following interfaces can overwrite params of DEFAULT interface. */
        interface BOBA {
            int HIGHSCORE_REWARD = 100;
            int[] IMAGE_FRAMES = new int[]{R.drawable.enemy_boba};
        }

        interface HAPPEN {
            int HIGHSCORE_REWARD = 75;
            int[] IMAGE_FRAMES = new int[] {R.drawable.enemy_happen_1, R.drawable.enemy_happen_2,
                    R.drawable.enemy_happen_3,R.drawable.enemy_happen_2};
        }

        interface ROCKETFISH {
            int HIGHSCORE_REWARD = 50;

            float SPEED_X_MIN = 10f;
            float SPEED_X_MAX = 15f; //TODO: could be also level dependent :) this will surely be level dependent :)
            float SPEED_Y_MIN = 5f; //TODO: Just use these here and add a lvlConstant (e.g. a difficulty e.g. *1.1 etc.
            float SPEED_Y_MAX = 10f;

            int[] IMAGE_FRAMES = new int[]{R.drawable.enemy_rocketfish};
        }
    }
}
