package yourowngame.com.yourowngame.classes.actors.projectiles.interfaces;

import yourowngame.com.yourowngame.R;

public interface IProjectile {
    interface PROPERTIES {
        interface DEFAULT {
            int ADDITIONAL_GAME_WIDTH = 15;  //So old Projectiles end outside of the screen (for restarting the game)
            /** Every 20th draw/update cycle a new bullet is issued (on-hold) AND on click.*/
            short SHOOT_FREQUENCY = 20;
        }

        /* Following properties for subclasses can overwrite default properties. */
        interface IRON {
            int[] IMAGE_FRAMES = new int[] {R.drawable.color_player_bullet};
            short SHOOT_FREQUENCY = 5;
        }
    }

}
