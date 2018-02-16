package yourowngame.com.yourowngame.loopPackage;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

/**
 * Created by Solution on 16.02.2018.
 *
 * Thread to handle the GameView operations
 *
 */

public class GameLoopThread extends Thread {
    //refers to the view
    private GameView view;

    private boolean isRunning;
    private final String TAG = "Thread";

    public GameLoopThread(GameView view){
        this.view = view;
    }

    public void setRunning(boolean run){
        isRunning = run;
    }

    @Override
    public void run(){
        while(isRunning){
            Canvas c = null;
            try{
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()){
                    view.updateGameObjects();
                    view.onDraw(c); //<-- calls the drawMethod, but void draw(Canvas c) wont work! ??
                    Log.d(TAG, "");
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }

        }
    }

}
