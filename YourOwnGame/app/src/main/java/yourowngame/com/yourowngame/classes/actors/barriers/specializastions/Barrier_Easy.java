package yourowngame.com.yourowngame.classes.actors.barriers.specializastions;

import android.app.Activity;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.actors.barriers.Barrier;

/**
 *
 * Easy Barrier, categorized by the possibility to pass by
 *
 */

public class Barrier_Easy extends Barrier {
    public Barrier_Easy(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }
}
