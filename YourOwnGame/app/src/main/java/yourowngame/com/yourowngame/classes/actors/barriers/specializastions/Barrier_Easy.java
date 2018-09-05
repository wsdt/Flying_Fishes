package yourowngame.com.yourowngame.classes.actors.barriers.specializastions;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.barriers.Barrier;

/**
 *
 * Easy Barrier, categorized by the possibility to pass by
 *
 * THIS IS A TEST-CLASS, IF TEST IS DONE -> reuse it for the other 2 barrier classes
 *
 */

public class Barrier_Easy extends Barrier {
    public static String TAG = "Barrier_Easy";
    public int[] possibleEasyBarriers = {R.drawable.barrier_easy}; //and so on, so we can create a randon number, depending on the size of this array.

    public Barrier_Easy(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
        /**initialize barrier */
        initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        try {
            if (!isInitialized()) {
                //We randomly pick one barrier out of the barrier-pool and use it for a single run
                setCurrentBitmap(getCraftedDynamicBitmap(this.getActivity(), possibleEasyBarriers[((int) (Math.random() * possibleEasyBarriers.length))], 0, null, null));

                Log.d(TAG, "Successfully initialized an " + this.getClass() + "-Object");
            }
        } catch (NullPointerException e) {
            Log.d(TAG, ""+ this.getClass() + " Initialize Failure!");
            e.printStackTrace(); }
    }

    @Override
    public void update() {
        super.update();
        Log.d(TAG, "Current X|Y" + this.getPosX() + " | " + this.getPosY());

        this.setPosX(this.getPosX() - this.getSpeedX());
        this.setPosY(getPosY());
    }

    @Override
    public void draw() {
        super.draw();
        getCanvas().drawBitmap(getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }
}
