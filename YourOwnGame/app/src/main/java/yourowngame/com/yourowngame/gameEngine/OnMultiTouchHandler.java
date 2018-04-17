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
    private MotionEvent.PointerCoords firstTouch = new MotionEvent.PointerCoords();
    private MotionEvent.PointerCoords secondTouch = new MotionEvent.PointerCoords();
    private final String TAG = "OnMultiTouchHandler";
    private boolean isMoving = false;
    private boolean isShooting = false;
    private boolean isMultiTouching = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float gameWidth = GameViewActivity.GAME_WIDTH;
        int eventAction = event.getActionMasked();

        //at every start, set them to false

        /** check for Multi-Touch -> work just not-so-fine, my brain starts to glow :)*/
        if(event.getPointerCount() > 1 ){
            event.getPointerCoords(0, firstTouch);
            event.getPointerCoords(1, secondTouch);

            if(eventAction == MotionEvent.ACTION_POINTER_DOWN) {
                isMultiTouching = true;
                isShooting = true;
            }else if(eventAction == MotionEvent.ACTION_POINTER_UP) {
                isMultiTouching = false;
            }
        }

        /** Check for Single-Touch -> works just fine :) !*/

        if(event.getPointerCount() == 1) {
            event.getPointerCoords(0, firstTouch);

            if (eventAction == MotionEvent.ACTION_DOWN && firstTouch.x < gameWidth / 2) {
                isMoving = true;
            } else if (eventAction == MotionEvent.ACTION_UP){
                isMoving = false;
            }

            if(eventAction == MotionEvent.ACTION_DOWN && firstTouch.x > gameWidth / 2){
                isShooting = true;
            }else if (eventAction == MotionEvent.ACTION_UP){
                isShooting = false;
            }
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

    public void setShootingToFalse(){
        isShooting = false;
    }

}
