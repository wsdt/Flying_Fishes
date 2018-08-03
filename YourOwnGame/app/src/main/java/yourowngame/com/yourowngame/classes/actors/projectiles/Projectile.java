package yourowngame.com.yourowngame.classes.actors.projectiles;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.projectiles.interfaces.IProjectile;

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

public abstract class Projectile extends GameObject implements IProjectile.PROPERTIES.DEFAULT {
    private final String TAG = "Projectile";

    public Projectile(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    // Short constr (e.g. for ProjectileMgr no posX/Y in constr needed)
    public Projectile(@NonNull Activity activity, double speedX, double speedY) {
        super(activity, 0, 0, speedX, speedY);
    }

    /** Block simplified constructor call, bc. we don't want random Projectiles. */
    private Projectile(@NonNull Activity activity) {
        super(activity);
    }


    @Override
    public void resetPos(){
        setPosX(Resources.getSystem().getDisplayMetrics().widthPixels+ADDITIONAL_GAME_WIDTH);
    }


    @Override
    public boolean cleanup() {
        this.resetPos(); //outside of display to prevent projectiles hitting new enemies altough projectiles were fired in previous game
        return true;
    }

    /** How many bullets at maximum simultaneously on screen? */
    public abstract short getShortFrequency();

}
