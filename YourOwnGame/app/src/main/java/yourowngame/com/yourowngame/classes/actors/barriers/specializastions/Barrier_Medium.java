package yourowngame.com.yourowngame.classes.actors.barriers.specializastions;

import android.app.Activity;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.actors.barriers.Barrier;

/**
 *
 * Medium Barrier, categorized by the possibility to pass by
 *
 */

public class Barrier_Medium extends Barrier {
    public Barrier_Medium(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }
}
