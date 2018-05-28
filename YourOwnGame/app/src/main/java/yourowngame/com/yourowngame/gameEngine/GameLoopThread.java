package yourowngame.com.yourowngame.gameEngine;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import yourowngame.com.yourowngame.classes.manager.dialog.PauseGameDialog;
import yourowngame.com.yourowngame.gameEngine.interfaces.IGameLoopThread;

/**
 * Created by Solution on 16.02.2018.
 *
 * Thread to handle the GameView operations
 *
 */

public class GameLoopThread extends Thread implements IGameLoopThread {
    /** @link GameLoopThread#isRunning: {true}->Gameloop will be executed || {false}->Gameloop stopped/paused
      * */
    //refers to the view
    private GameView view;

    private boolean isRunning;
    private static final String TAG = "Thread";

    public GameLoopThread(@NonNull GameView view){
        this.view = view;
    }

    public void setRunning(boolean run){
        isRunning = run;
    }

    public void pauseGame(@Nullable Long sleepTime) {
        Log.d(TAG, "pauseGame: Tried to pause game.");
        //assumes that startGame() has been called before.
            try {
                while(isRunning) {
                    if (holdsLock(this)) {
                        if (sleepTime == null || sleepTime < 0) {
                            this.wait();
                        } else {
                            this.wait(sleepTime);
                        }

                        PauseGameDialog.show(this.view);
                    } else {
                        Log.w(TAG, "pauseGame: Thread does not hold lock.");
                    }
                }
                Log.d(TAG, "pauseGame: Game might have been successfully paused.");
            } catch (InterruptedException e) {
                Log.e(TAG, "pauseGame: Could not pause game.");
            }
    }

    public void resumeGame() {
        Thread.State state = this.getState();
        if (state == Thread.State.WAITING || state == Thread.State.TIMED_WAITING) {
            //Only notify when thread is really waiting (so no problem that onResume() is time multiple times)
            this.notify();
        }
    }

    @Override
    public void run(){
        Canvas c;
        long beginTime; // begin time of cycle
        long timeDiff; //time it took for cycle to
        int sleepTime = 0; //ms to sleep (<0 when we are behind)
        int framesSkipped; //number of frames being skipped
        long countRenderedCycles = 0;

        if(Looper.myLooper() == null) {
            Looper.prepare(); //necessary for handlers etc.
        }

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
                        } catch (InterruptedException e) {Log.i(TAG, "run: Gameloop stopped/interrupted.");}
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAMES_SKIPPABLE) { //= Max frames skipped
                        //catch up, update without rendering
                        view.updateGameObjects();
                        //add frame period to check if in next frame
                        sleepTime += (1000 / MAX_FPS); //FRAMEPERIOD = 1000 / 50 [50 = MAX_FPS]
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
