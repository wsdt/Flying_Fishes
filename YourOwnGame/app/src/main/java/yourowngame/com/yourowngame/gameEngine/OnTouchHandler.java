package yourowngame.com.yourowngame.gameEngine;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Solution on 16.02.2018.
 *
 */

public class OnTouchHandler implements View.OnTouchListener {

    private boolean isTouched = false;


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int action = event.getAction();

        //Should always start at false
        isTouched = false;

        if(action == MotionEvent.ACTION_DOWN){
            Toast.makeText(v.getContext(), "Touched", Toast.LENGTH_SHORT).show();
            isTouched = true;
        }
        return isTouched();
    }

    public boolean isTouched() {
        return this.isTouched;
    }
}
