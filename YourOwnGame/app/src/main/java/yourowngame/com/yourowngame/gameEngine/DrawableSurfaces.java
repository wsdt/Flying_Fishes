package yourowngame.com.yourowngame.gameEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.activities.GameViewActivity;

/** Abstract class for GameView and WorldView etc. */
public abstract class DrawableSurfaces extends SurfaceView {
    private DrawableSurfaceActivity drawableSurfaceActivity;
    private SurfaceHolder surfaceHolder;
    private CanvasDrawThread thread;

    public DrawableSurfaces(Context context) {
        super(context);
    }

    public DrawableSurfaces(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableSurfaces(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /** How to exit thread/view (abstract bc. every surfaceView is different) */
    public abstract void exitNow();
    /** If you want to show a warning dialogue or similar you can do it here. */
    public abstract void startExitProcedure();
    /** Drawing procedures executed with every frame (also levelPoints here, bc. otherwise gone) */
    public abstract void drawAll(@NonNull Canvas canvas, long loopCount);
    /** Updating procedures executed with every frame*/
    public abstract void updateAll();

    public void initializeDrawing() {
        /**************************************
         * Start of the Surface & Thread Page *
         **************************************/
        setThread(new CanvasDrawThread(this));
        this.setSurfaceHolder(getHolder());

        //Callback-method: 3 Types
        this.getSurfaceHolder().addCallback(new SurfaceHolder.Callback() {

            //Surface gets created, Thread starts
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                getThread().setRunning(true);
                getThread().start();
            }

            //No need
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                startExitProcedure();
            }
        });
    }

    //GETTER / SETTER +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public DrawableSurfaceActivity getDrawableSurfaceActivity() {
        return drawableSurfaceActivity;
    }

    public void setDrawableSurfaceActivity(DrawableSurfaceActivity drawableSurfaceActivity) {
        this.drawableSurfaceActivity = drawableSurfaceActivity;
    }

    public CanvasDrawThread getThread() {
        return thread;
    }

    public void setThread(CanvasDrawThread thread) {
        this.thread = thread;
    }

    public SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;
    }

    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }
}
