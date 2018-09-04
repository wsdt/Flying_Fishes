package yourowngame.com.yourowngame.classes.actors.barriers;

import android.app.Activity;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

/**
 *
 * Basically, a Barrier lays with its hole body within the gameView, moving from max to min, or rotating on a specific position
 *
 * Structural approach would be:
 *
 * Creating all kind of barriers (keeping it simple, using the collision manager & testing whether transparent or not)
 *
 * Easy - Marked as easy, because easy to pass by. NO rotation, NO moving
 * Medium - Marked as medium, thread of colliding. CAN rotate, NO moving
 * Hard - Marked as hard, absolute thread of colliding, CAN Rotate, CAN spin, CAN move (<-- nice feature would be a timer,
 *                                                                           after that it accidentally explodes, specific range (pixels) of explosion means Game-Over)
 *
 * We should keep these as "unique" --> means if a barrier leaves the field, it should not be reproduced (after itself)
 * So now, (until we finally get a style-set) we should create a minimum of 2 barriers/difficulty. (can also just be other bitmaps..)
 *
 * These objects should only spawn <-- kill the player --> or vanish.
 * Barriers will not mover after the player, they will stay in their position and wait for colliding.
 *
 */

public class Barrier extends GameObject {

    protected static final float SPEED_X_MIN = 5f;
    protected static final float SPEED_X_MAX = 10f;
    protected static final float SPEED_Y_MIN = 2f;
    protected static final float SPEED_Y_MAX = 3f;

    public Barrier(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    @Override
    public void resetPos() {
        // this time, we shouldn't do that. the same barrier should never appear again, maybe after a specific time, but not after leaving the fielod!
    }

    @Override
    public void resetSpeed() {
        setSpeedX(RandomMgr.getRandomFloat(SPEED_X_MIN, SPEED_X_MAX));
        setSpeedY(RandomMgr.getRandomFloat(SPEED_Y_MIN, SPEED_Y_MAX));
    }

    @Override
    public void draw() {

    }

    @Override
    public void initialize() {

    }
}
