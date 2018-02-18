package yourowngame.com.yourowngame.gameEngine;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;

import yourowngame.com.yourowngame.classes.configuration.Constants;

/**
 * Created by Solution on 16.02.2018.
 *
 * Thread to handle the GameView operations
 *
 */

public class GameLoopThread extends Thread {
    //refers to the view
    private GameView view;
    private Handler uiHandler; //necessary for redrawing view

    private boolean isRunning;
    private final String TAG = "Thread";

    public GameLoopThread(GameView view){
        this.view = view;
        this.uiHandler = new Handler(view.getContext().getMainLooper());
    }

    public void setRunning(boolean run){
        isRunning = run;
    }

    @Override
    public void run(){
        Canvas c;
        long beginTime; // begin time of cycle
        long timeDiff; //time it took for cycle to
        int sleepTime = 0; //ms to sleep (<0 when we are behind)
        int framesSkipped; //number of frames being skipped
        long countRenderedCycles = 0;

        while(isRunning){
            Log.d(TAG, "run: Game loop got started.");
            c = null;

            try{
                if (!view.getHolder().getSurface().isValid()) {
                    continue;
                }
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0; //resetting
                    view.updateGameObjects();

                    //draws canvas on panel
                    view.redraw(c, countRenderedCycles++);
                    timeDiff = System.currentTimeMillis() - beginTime;

                    if (sleepTime > 0) {
                        //if bigger > 0 then everything good
                        try {
                            Thread.sleep(sleepTime); //battery saving
                        } catch (InterruptedException e) {
                        }
                    }

                    while (sleepTime < 0 && framesSkipped < Constants.GameLogic.GameLoopThread.maxFramesSkipable) { //= Max frames skipped
                        //catch up, update without rendering
                        view.updateGameObjects();
                        //add frame period to check if in next frame
                        sleepTime += (1000 / Constants.GameLogic.GameLoopThread.maxFPS); //FRAMEPERIOD = 1000 / 50 [50 = MAX_FPS]
                        framesSkipped++;
                    }
                    Log.d(TAG, "run: Started cycle at "+beginTime+"\nTime-Difference (to ending): "+timeDiff);
                }
            } finally {
                if (c != null) {
                    Log.d(TAG, "run:finally: Trying to unlock canvas and post.");
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }
}
