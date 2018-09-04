package yourowngame.com.yourowngame.classes.actors.barriers.specializastions;

import android.app.Activity;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.actors.barriers.Barrier;

/**
 *
 * Hard Barrier, categorized by the possibility to pass by
 *
 * Hard Barriers are harder to pass (who actually guessed that one)
 *
 */

public class Barrier_Hard extends Barrier {
    public Barrier_Hard(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }
}
