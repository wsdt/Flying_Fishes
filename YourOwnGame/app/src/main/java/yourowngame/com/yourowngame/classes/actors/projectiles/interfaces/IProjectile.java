package yourowngame.com.yourowngame.classes.actors.projectiles.interfaces;

import yourowngame.com.yourowngame.R;

public interface IProjectile {
    interface PROPERTIES {
        interface DEFAULT {
            int ADDITIONAL_GAME_WIDTH = 15;  //So old Projectiles end outside of the screen (for restarting the game)
        }

        /* Following properties for subclasses can overwrite default properties. */
        interface IRON {
            int[] IMAGE_FRAMES = new int[] {R.drawable.color_player_bullet};
        }
    }

}
