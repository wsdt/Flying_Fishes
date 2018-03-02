package yourowngame.com.yourowngame.gameEngine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.Toast;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.Enemy;
import yourowngame.com.yourowngame.classes.actors.Player;
import yourowngame.com.yourowngame.classes.background.BackgroundManager;
import yourowngame.com.yourowngame.classes.configuration.Constants;

/**
 * Created by Solution on 16.02.2018.
 * <p>
 * GameView Surface, draw players here and in the end add it to the GameViewActivity
 */

public class GameView extends SurfaceView {
    private static final String TAG = "GameView";
    private Activity activityContext;
    private SurfaceHolder holder;
    private GameLoopThread thread;
    private Player playerOne;
    private OnTouchHandler touchHandler;
    private FrameLayout layout;
    //DO NOT USE THIS (use LevelManager.CURRENT_LEVEL) XX private int level = 0; /** for Background-drawing, amount of enemys etc. */

    private GameView(Context context) {
        super(context);
    } //dummy constructor for android tools

    public GameView(GameViewActivity context) {
        super(context);
        this.setActivityContext(context);

        /** Get the layout-res */
        layout = context.getView();

        /** Initialize GameObjects & eq here! */
        initGameObjects();
        initComponents();


        /**************************************
         * Start of the Surface & Thread Page *
         **************************************/
        thread = new GameLoopThread(this);
        holder = getHolder();

        getRootView().setOnTouchListener(touchHandler);

        //Callback-method: 3 Types
        holder.addCallback(new SurfaceHolder.Callback() {

            //Surface gets created, Thread starts
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                thread.setRunning(true);
                thread.start();
            }

            //No need
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                exitGame();
            }
        });
    }

    public void exitGame() {
        Toast.makeText(this.getActivityContext(), "Game over", Toast.LENGTH_SHORT).show(); //TODO: why does this shit not show up
        boolean retry = true;
        thread.setRunning(false);
        Log.d(TAG, "exitGame: Trying to exit game."); //but this is logged?
        while (retry) {
            try {
                Log.d(TAG, "exitGame: Trying to join threads.");
                thread.join();
                retry = false;
                this.getActivityContext().finish(); //todo: does not work
            } catch (InterruptedException | ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    //initialize components that match GameObject()
    private void initGameObjects() {
        /** Player creation*/
        playerOne = new Player(100, getRootView().getHeight() / 4, 5, 2, new int[]{
                R.drawable.player_heli_blue_1, R.drawable.player_heli_blue_2, R.drawable.player_heli_blue_3, R.drawable.player_heli_blue_4,
                R.drawable.player_heli_blue_3, R.drawable.player_heli_blue_2},Constants.Actors.Player.defaultRotation, "Rezy");

        /** Enemy creation */
        Enemy.getInstance().createRandomEnemies(this, 5, new int[] {R.drawable.player_heli_blue_1}, "Enemy");

    }

    //initialize components that do not match other categories
    private void initComponents() {
        touchHandler = new OnTouchHandler();
    }

    /***************************
     * 1. Draw GameObjects here *
     ***************************/

    public void redraw(Canvas canvas, long loopCount) { //Create separate method, so we could add some things here
        Log.d(TAG, "redraw: Trying to invalidate/redraw GameView.");
        if (canvas != null) {
            //in loop, BUT we don't do anything if already set
            canvas.drawColor(0, PorterDuff.Mode.CLEAR); //remove previous bitmaps etc. (it does not work to set here only bg color!, because of mode)

            try {
                //draw background
                drawDynamicBackground(canvas);

                //draw enemies
                Enemy.getInstance().drawAllEnemies(this.getActivityContext(), canvas, loopCount);

                //draw player
                this.playerOne.draw(this.getActivityContext(), canvas, loopCount);

            } catch (Exception e) {
                Log.e(TAG, "redraw: Could not draw images.");
                e.printStackTrace();
            }
            Log.d(TAG, "Tried to draw animationdrawable.");
        } else {
            exitGame();
        }
    }


/** Please read: ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *  I strongly recommend that we always draw and update ALL backgroundlayers (arraylist in BackgroundManager).
 *  Which this we can have more amazing backgrounds and can also reuse single layers by just adding them to the BackgroundManager arraylist.
 *  So, for level-change (at least for the background) we just have to change/rearrange the Arraylist in BackgroundManager.
 *
 *  With this procedure we can e.g. use the cloud layer in level 0, 5, 8
 *  [also maybe in different orders --> as example: we could let other layers behind or before the cloud layer and so on! :)]
 *
 *  Sounds nice, lets keep on track
 * */


    /** Gets all Layers & draws them! */
    public void drawDynamicBackground(@NonNull Canvas canvas) {
        //Get current Layer
        BackgroundManager.getInstance(this).drawAllBackgroundLayers(canvas);
    }


    /*****************************
     * 2. Update GameObjects here *
     *****************************/
    public void updateGameObjects() {
        //Update the player handling                    should only be true if player collects box or equivalent!
        playerOne.update(null, this.touchHandler.isTouched(), false);

        //Update enemies
        for (Enemy e : Enemy.getInstance().getEnemys()) {
            e.aimToPlayer(playerOne);

            /** playerOne.getBitmap is not null, but the width/height might be.. but why?
             * --> Presumably, because you call this in GameLoopThread also on top, so the first execution the bitmap
             * might not drawn yet or similar */
            if (CollisionManager.checkForCollision(this.playerOne, e)) {
                exitGame();
            }
        }

        //Check if player hits the view's border
        if (playerOne.hitsTheGround(this, playerOne)) {
            exitGame();
        }

        //Update background
        BackgroundManager.getInstance(this).updateAllBackgroundLayers();
    }



   /*********************************************************
     * 2. Getters & Setters and all of that annoying methods *
     *********************************************************/


    public double randomY() {
        return Math.random() * getRootView().getHeight();
    }

    public FrameLayout getLayout() {
        return layout;
    }

    public Activity getActivityContext() {
        return activityContext;
    }

    public void setActivityContext(Activity activityContext) {
        this.activityContext = activityContext;
    }
}
