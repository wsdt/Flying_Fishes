package yourowngame.com.yourowngame.gameEngine;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Solution on 16.02.2018.
 *
 * Checks, whether the screen has ben touched or not
 *
 */

public class OnTouchHandler implements View.OnTouchListener {
    private static final String TAG = "OnTouchHandler";
    private boolean isTouched = false;


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "onTouch: Screen got touched!");
            isTouched = true;
        } else if (action == MotionEvent.ACTION_UP) {
            isTouched = false; //set to false if finger removed
        }
        return isTouched();
    }

    public boolean isTouched() {
        return this.isTouched;
    }
}
