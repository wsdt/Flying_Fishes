package yourowngame.com.yourowngame.gameEngine;


import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import yourowngame.com.yourowngame.activities.GameViewActivity;

/** manages multi-touch events, 17.04.2018
 *
 *  if isMoving returns true, player just moves
 *  if isShooting returns true, player just shoots
 *  if isMultiTouched() is true, player moves and shoots at the same time, that's what we want & i hope that shit works
 *
 * */

public class OnMultiTouchHandler implements View.OnTouchListener{
    private final String TAG = "OnMultiTouchHandler";

    private MotionEvent.PointerCoords firstTouch = new MotionEvent.PointerCoords();
    private MotionEvent.PointerCoords secondTouch = new MotionEvent.PointerCoords();
    private boolean isMoving = false;
    private boolean isShooting = false;
    private boolean isHolding = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float gameWidth = GameViewActivity.GAME_WIDTH;
        int eventAction = event.getActionMasked();

        /** Check for Multi-Touch*/
        if(event.getPointerCount() > 1 ) {
            event.getPointerCoords(0, firstTouch);
            event.getPointerCoords(1, secondTouch);

            /**
            * NORMAL CASE: he puts his left finger on the screen, then his right one. That indicates that he first wants to fly & then shoot
            * So Shooting and Moving must be true.
            */
            if (eventAction == MotionEvent.ACTION_POINTER_DOWN && (firstTouch.x < gameWidth / 2 && secondTouch.x > gameWidth / 2)) {
                Log.d(TAG, "MultiTouch, User shoots and moves");
                isShooting = true;
                isMoving = true;
            /**
            * SPECIAL CASE: The user first wants to shoot, so he puts his Finger on the right side, holds it (although holding doesnt have any effects)
            * and than wants to move upwards --> is Shooting should not be true, because the right finger is currently in MODE "HOLDING", which doesnt have an effect.
            *
            * Conclusion: he only goes upwards, without firing a single shot.
            */
            } else if (eventAction == MotionEvent.ACTION_POINTER_DOWN && firstTouch.x > gameWidth / 2 && secondTouch.x < gameWidth / 2) {
                Log.d(TAG, "MultiTouch, User shoots and moves");
                isMoving = true;
            }
        }
        /** Check for Single-Touch!*/
        if(event.getPointerCount() == 1) {
            event.getPointerCoords(0, firstTouch);

            /** Check if left*/
            if (eventAction == MotionEvent.ACTION_DOWN && firstTouch.x < gameWidth / 2) {
                Log.d(TAG, "SingleTouch, User moves");
                isMoving = true;
            } else if (eventAction == MotionEvent.ACTION_UP){
                isMoving = false;
            }

            /** Check if right */
            if(eventAction == MotionEvent.ACTION_DOWN && firstTouch.x > gameWidth / 2){
                Log.d(TAG, "SingleTouch, User shoots");
                isShooting = true;
            }else if (eventAction == MotionEvent.ACTION_UP){
                isShooting = false;
            }

            /** Check if he holds the touch for x-seconds */
            //..
        }
        return false;
    }

    public boolean isMoving(){
        return isMoving;
    }

    public boolean isShooting(){
        return isShooting;
    }

    public boolean isHolding(){
        return isHolding;
    }

    public void stopShooting(){
        isShooting = false;
    }

}
