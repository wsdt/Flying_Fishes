package yourowngame.com.yourowngame.classes.actors.player.interfaces;


import yourowngame.com.yourowngame.R;

public interface IPlayer {
    interface PROPERTIES {
        interface DEFAULT {
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
        /** Following interfaces can overwrite default interface. */
        interface HUGO {
            int[] IMAGE_FRAMES = new int[] {R.drawable.player_hugo};
        }
        interface ALBERT {
            int[] IMAGE_FRAMES = new int[] {R.drawable.player_albert};
        }
    }


}
