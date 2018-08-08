package yourowngame.com.yourowngame.classes.actors.projectiles;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

/**
 * Created by Solution on 27.02.2018.
 * <p>
 * Projectiles which the player can shoot to kill enemys!
 * Later on, projectiles will be available to buy in the shop (f.e)
 * <p>
 * Projectiles will be used as Singletone
 * <p>
 * Everytime the player hits a button or something else, projectiles will be shown
 * <p>
 * So shall we create a Singletone with a List of Projectiles?
 */

public abstract class Projectile extends GameObject {
    private final String TAG = "Projectile";

    /**
     * Default projectile constants
     */
    protected static final double DEFAULT_SPEED_X = 10;
    protected static final double DEFAULT_SPEED_Y = 0; //0 otherwise it would fly down or up (could be cool in future)
    /**
     * Every 20th draw/update cycle a new bullet is issued (on-hold) AND on click.
     */
    protected static final short SHOOT_FREQUENCY = 20;

    public Projectile(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    // Short constr (e.g. for ProjectileMgr no posX/Y in constr needed)
    public Projectile(@NonNull Activity activity, double speedX, double speedY) {
        super(activity, 0, 0, speedX, speedY);
    }

    /**
     * Block simplified constructor call, bc. we don't want random Projectiles.
     */
    private Projectile(@NonNull Activity activity) {
        super(activity);
    }


    @Override
    public void resetPos() {
        //just hide, params are newly set on shoot (Important that it is out of screen!)
        this.setPosX(0-this.getWidthOfBitmap());
        this.setPosY(0-this.getHeightOfBitmap());
    }

    @Override
    public void resetSpeed() {
        this.setSpeedX(DEFAULT_SPEED_X);
        this.setSpeedY(DEFAULT_SPEED_Y);
    }

    /**
     * How many bullets at maximum simultaneously on screen?
     */
    public abstract short getShootFrequency();

}
