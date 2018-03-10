package yourowngame.com.yourowngame.gameEngine;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.Enemy;
import yourowngame.com.yourowngame.classes.actors.Player;
import yourowngame.com.yourowngame.classes.background.BackgroundManager;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.handler.DialogMgr;
import yourowngame.com.yourowngame.classes.handler.interfaces.ExecuteIfTrueSuccess_or_ifFalseFailure_afterCompletation;

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
    private EditText munition;
    //DO NOT USE THIS (use LevelManager.CURRENT_LEVEL) XX private int level = 0; /** for Background-drawing, amount of enemys etc. */

    private GameView(Context context) {
        super(context);
    } //dummy constructor for android tools

    public GameView(GameViewActivity context) {
        super(context);
        this.setActivityContext(context);

        /** Initialize View Components */
        layout = context.getView();

        /** Initialize GameObjects & eq here! After initializing, the GameLoop will start!*/
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



    //initialize components that match GameObject()
    private void initGameObjects() {
        /** Player creation*/
        setPlayerOne(new Player(100, getRootView().getHeight() / 4, 5, 2, new int[]{
                R.drawable.player_heli_blue_1, R.drawable.player_heli_blue_2, R.drawable.player_heli_blue_3, R.drawable.player_heli_blue_4,
                R.drawable.player_heli_blue_3, R.drawable.player_heli_blue_2},Constants.Actors.Player.defaultRotation, "Rezy"));

        /** Enemy creation */
        int[] enemyArray = new int[] {R.drawable.enemy};
        Enemy.createRandomEnemies(5, enemyArray);

        /** Initializing Player*/
        getPlayerOne().initialize(this.getActivityContext());

        /** Initializing Enemy */
        Enemy.getInstance(enemyArray).initialize(this.getActivityContext());

        /** Initializes() of backgrounds are in constructor itself */
    }

    //initialize components that do not match other categories
    private void initComponents() {
        /*Moved onclick listener to activity (where it belongs)*/

        /** create OnTouchHandler */
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
                // (1.) draw background
                drawDynamicBackground(canvas);

                // (2.) draw enemies
                Enemy.getInstance(null).draw(this.getActivityContext(), canvas, loopCount);

                // (3.) draw player
                getPlayerOne().draw(this.getActivityContext(), canvas, loopCount);

                // (4.) draw Projectiles
                getPlayerOne().drawProjectiles(this.getActivityContext(), canvas, loopCount);

            } catch (Exception e) {
                Log.e(TAG, "redraw: Could not draw images.");
                e.printStackTrace();
            }
            Log.d(TAG, "redraw: SUCCESS");
        } else {
            exitGame();
        }
    }

    /** Gets all Layers & draws them! */
    public void drawDynamicBackground(@NonNull Canvas canvas) {
        BackgroundManager.getInstance(this).drawAllBackgroundLayers(canvas);
    }


    /*****************************
     * 2. Update GameObjects here *
     *****************************/
    public void updateGameObjects() {
        /** (1.) update the Player*/                    //should only be true if player collects box or equivalent!
        getPlayerOne().update(null, this.touchHandler.isTouched(), false);

        /** (2.) update the Enemy*/
        for (Enemy e : Enemy.getInstance(null).getEnemys()) {
            e.aimToPlayer(getPlayerOne());

            Log.d(TAG, "X|Y = " + e.getPosX() + "|" + e.getPosY());

            if (CollisionManager.checkForCollision(this.getPlayerOne(), e)) {
                exitGame();
            }
        }

        /** update the Bullets*/
        this.getPlayerOne().updateProjectiles();

       /** Check Collision with Border */
        if (getPlayerOne().hitsTheGround(this)) {
            exitGame();
        }

        //Update background
        BackgroundManager.getInstance(this).updateAllBackgroundLayers();
    }


    /*********************************************************
     * 3. Game Over Methods *
     *********************************************************/
    public void exitGame() {
        Toast.makeText(this.getActivityContext(), "Game over", Toast.LENGTH_SHORT).show(); //TODO: why does this shit not show up
        boolean retry = true;
        thread.setRunning(false);
        Log.d(TAG, "exitGame: Trying to exit game."); //but this is logged?
        while (retry) {
            try {
                Log.d(TAG, "exitGame: Trying to join threads and showing dialog before.");
                Resources res = getActivityContext().getResources();
                //TODO: Sth has to be wrong with context
                (new DialogMgr(this.getActivityContext())).showDialog_Generic(
                        res.getString(R.string.dialog_generic_gameOver_title),
                        res.getString(R.string.dialog_generic_gameOver_msg),
                        res.getString(R.string.dialog_generic_button_positive_gameOverAccept),
                        res.getString(R.string.dialog_generic_button_negative_gameOverRevive),
                        R.drawable.app_icon_gameboy, new ExecuteIfTrueSuccess_or_ifFalseFailure_afterCompletation() {
                            @Override
                            public void success_is_true() {
                                //todo: End everything here in future, so we could resume game when entering failure_is_false :)
                            }

                            @Override
                            public void failure_is_false() {

                            }
                        }
                );
                thread.join();
                retry = false;
                this.getActivityContext().finish(); //todo: does not work
            } catch (InterruptedException | ClassCastException e) {
                e.printStackTrace();
            }
        }
    }


   /*********************************************************
     * 4. Getters & Setters and all of that annoying methods *
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

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }
}
