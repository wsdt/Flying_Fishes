package yourowngame.com.yourowngame.gameEngine;

import android.graphics.Canvas;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.classes.manager.dialog.PauseGameDialog;
import yourowngame.com.yourowngame.gameEngine.surfaces.GameView;

/**
 * Created by Solution on 16.02.2018.
 * <p>
 * Thread to handle the GameView or e.g. WorldView operations
 */

public class CanvasDrawThread extends Thread {
    /*
     * @link GameLoopThread#isRunning: {true}->Gameloop will be executed || {false}->Gameloop stopped/paused
     */

    /** Can be e.g. GameView or WorldActivity. */
    private DrawableSurfaces view;

    private boolean isRunning;
    private static final String TAG = "Thread";

    /** Thread constants */
    private static final int MAX_FRAMES_SKIPPABLE = 5;
    private static final int MAX_FPS = 50;


    public CanvasDrawThread(@NonNull DrawableSurfaces view) {
        this.setView(view);
    }

    public void pauseThread() {
        setRunning(false);

        if (this.getView() instanceof GameView) {
            PauseGameDialog.show((GameView) CanvasDrawThread.this.getView());
        } //otherwise just do not show a pause dialog

        Log.d(TAG, "pauseThread: Tried to pause game.");
    }

    public void resumeThread() {
        setRunning(true);
        /* use run() to start updating/drawing again to use this thread and not a new thread.
        Start() would create a new one.*/
        run();
    }

    @Override
    public void run() {
        Canvas c;
        long beginTime; // begin time of cycle
        long timeDiff; //time it took for cycle to
        int sleepTime = 0; //ms to sleep (<0 when we are behind)
        int framesSkipped; //number of frames being skipped
        long countRenderedCycles = 0;

        if (Looper.myLooper() == null) {
            Looper.prepare(); //necessary for handlers etc.
        }

        while (isRunning()) {
            Log.d(TAG, "run: Game loop got started.");
            c = null;

            try {
                if (!getView().getHolder().getSurface().isValid()) {
                    continue;
                }
                c = getView().getHolder().lockCanvas();
                if (c==null) {setRunning(false);break;} //if canvas is gone setRunning to false and exit loop
                Log.e(TAG, "CANVAS: "+c);
                synchronized (getView().getHolder()) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0; //resetting
                    getView().updateAll();

                    //draws canvas on panel
                    getView().drawAll(c, countRenderedCycles++);
                    if (countRenderedCycles >= (Integer.MAX_VALUE-1)) {countRenderedCycles = 0;} //reset to avoid overflow
                    timeDiff = System.currentTimeMillis() - beginTime;

                    if (sleepTime > 0) {
                        //if bigger > 0 then everything good
                        try {
                            Thread.sleep(sleepTime); //battery saving
                        } catch (InterruptedException e) {
                            Log.i(TAG, "run: Gameloop stopped/interrupted.");
                        }
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAMES_SKIPPABLE) { //= Max frames skipped
                        //catch up, update without rendering
                        getView().updateAll();
                        //add frame period to check if in next frame
                        sleepTime += (1000 / MAX_FPS); //FRAMEPERIOD = 1000 / 50 [50 = MAX_FPS]
                        framesSkipped++;
                    }
                    Log.d(TAG, "run: Started cycle at " + beginTime + "\nTime-Difference (to ending): " + timeDiff);
                }
            } finally {
                if (c != null) {
                    Log.d(TAG, "run:finally: Trying to unlock canvas and post.");
                    getView().getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }

    //GETTER / SETTER  ++++++++++++++++++++++++++++++++++++++
    public DrawableSurfaces getView() {
        return view;
    }

    public void setView(DrawableSurfaces view) {
        this.view = view;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean run) {
        isRunning = run;
    }
}
