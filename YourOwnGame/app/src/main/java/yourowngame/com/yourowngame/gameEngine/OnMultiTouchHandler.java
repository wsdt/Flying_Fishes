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
    private boolean isMultiTouching = false;
    private boolean isHolding = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float gameWidth = GameViewActivity.GAME_WIDTH;
        int eventAction = event.getActionMasked();

        /** Check for Multi-Touch*/
        if(event.getPointerCount() > 1 ){
            event.getPointerCoords(0, firstTouch);
            event.getPointerCoords(1, secondTouch);

            if(eventAction == MotionEvent.ACTION_POINTER_DOWN) {
                Log.d(TAG, "MultiTouch, User moves and shoots");
                isMultiTouching = true;
                isShooting = true;
            }else if(eventAction == MotionEvent.ACTION_POINTER_UP) {
                isMultiTouching = false;
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



    public boolean isMultiTouched(){
        return isMultiTouching;
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
