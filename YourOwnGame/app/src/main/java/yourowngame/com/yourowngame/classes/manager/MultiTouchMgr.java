package yourowngame.com.yourowngame.classes.manager;


import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.annotations.Idea;

/**
 * onLeftClick/Hold: Move upwards
 * onRightClick/Hold: Shoot
 *
 * Important: Frequency of bullets shouldn't be determined by MultiTouch (maybe add frequency member in
 *      Projectile.class and then calculate how many projectiles/second-hold.
 */

@Idea(idea = "Frequency of bullets shouldn't be determined by MultiTouch (maybe add frequency member in\n" +
        "Projectile.class and then calculate how many projectiles/second-hold.")
@Enhance (message = "Why is shooting frequency assymmetric? --> Maybe bc. frequency not limited (so longer method calls " +
        "are blocking the drawing procedures [high probability])")
public class MultiTouchMgr implements View.OnTouchListener {
    private static final String TAG = "MultiTouchMgr";

    //Currently only supporting two pointerCoords
    private MotionEvent.PointerCoords pointer = new MotionEvent.PointerCoords();
    private boolean isMoving = false;
    private boolean isShooting = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //event.getPointerCoords(event.getPointerId(actionIndex), pointer); //instead of this i'm using the following to avoid pointer out of range exception
        event.getPointerCoords(event.getActionIndex(), pointer);
        pushNewCommand(event.getActionMasked(), pointer.x);

        return v.performClick();
    }

    /**
     * Sets whether player is shooting or/and moving.
     *
     * @param maskedEvent: Is player clicking on screen or releasing screen.
     * @param pointerX:    On which site did the user click?
     */
    private void pushNewCommand(int maskedEvent, float pointerX) {
        if (pointerX < (GameViewActivity.GAME_WIDTH / 2)) {
            //player clicked on the left side
            isMoving = !(maskedEvent == MotionEvent.ACTION_UP || maskedEvent == MotionEvent.ACTION_POINTER_UP); //if user is currently pressing then isMoving = true!
            Log.d(TAG, "pushNewCommand: isMoving->"+isMoving);
        } else {
            //player clicked on the right side
            isShooting = !(maskedEvent == MotionEvent.ACTION_UP || maskedEvent == MotionEvent.ACTION_POINTER_UP); //if user currently pressing then isShooting = true
            Log.d(TAG, "pushNewCommand: isShooting->"+isShooting);
        }
    }


    //GETTER/SETTER -----------------------------------
    public boolean isMoving() {
        return isMoving;
    }

    public boolean isShooting() {
        return isShooting;
    }


    @Enhance(message = "Acc. to my understanding this method shouldn't be needed. Didn't test it though whether it works without it.")
    public void stopShooting() {
        isShooting = false;
    }

}
