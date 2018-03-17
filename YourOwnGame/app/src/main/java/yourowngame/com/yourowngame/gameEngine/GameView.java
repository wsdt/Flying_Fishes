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
import android.widget.FrameLayout;
import android.widget.Toast;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.Enemy;
import yourowngame.com.yourowngame.classes.actors.Player;
import yourowngame.com.yourowngame.classes.actors.RoboticEnemy;
import yourowngame.com.yourowngame.classes.actors.SpawnEnemy;
import yourowngame.com.yourowngame.classes.actors.BomberEnemy;
import yourowngame.com.yourowngame.classes.background.BackgroundManager;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;
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
    private Highscore highscore;

    //That little list will later hold all the enemys, iterate through them and draw them all!
    //DO NOT USE THIS AS MENTIONED BEFORE: private List<Enemy> enemyContainer = new ArrayList<>();
    // ENEMIES are level-dependent, so specific level obj should own a list with all enemies. (LevelMgr.CURRENT_LEVEL)

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

        /** Initializing Player*/
        getPlayerOne().initialize(this.getActivityContext());

        /** Initializes() of backgrounds are in constructor itself */
    }

    //initialize components that do not match other categories
    private void initComponents() {
        /** create OnTouchHandler */
        touchHandler = new OnTouchHandler();

        /** create Highscore Counter */
        highscore = new Highscore();
    }

    /********************************
     * 1. D R A W I N G   - A R E A *
     ********************************/
    public void redraw(Canvas canvas, long loopCount) { //Create separate method, so we could add some things here
        Log.d(TAG, "redraw: Trying to invalidate/redraw GameView.");
        if (canvas != null) {
            //in loop, BUT we don't do anything if already set
            canvas.drawColor(0, PorterDuff.Mode.CLEAR); //remove previous bitmaps etc. (it does not work to set here only bg color!, because of mode)

            try {
                // (1.) draw background
                drawDynamicBackground(canvas);

                // (3.) draw player
                getPlayerOne().draw(this.getActivityContext(), canvas, loopCount);

                // (4.) draw Projectiles
                getPlayerOne().drawProjectiles(this.getActivityContext(), canvas, loopCount);

                // (2.) draw enemies
                RoboticEnemy.drawAll(this.getActivityContext(), canvas, loopCount);
                BomberEnemy.drawAll(this.getActivityContext(), canvas, loopCount);
                SpawnEnemy.drawAll(this.getActivityContext(), canvas, loopCount);

            } catch (Exception e) {
                //Log.e(TAG, "redraw: Could not draw images.");
                e.printStackTrace();
            }
            //Log.d(TAG, "redraw: SUCCESS");
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

        /** (2.) update the Enemies*/
        RoboticEnemy.updateAll(this.playerOne, null, null);
        BomberEnemy.updateAll(this.playerOne, null, null);
        SpawnEnemy.updateAll(this.playerOne, null, null);

        /** update the Bullets*/
        this.getPlayerOne().updateProjectiles();

        /** check Collision with Border */
        if (getPlayerOne().hitsTheGround(this)) {
            exitGame();
        }

        /** update the background */
        BackgroundManager.getInstance(this).updateAllBackgroundLayers();

        /** check Player-to-Enemy collision */
        for (Enemy e : LevelManager.getCurrentLevelObj().getAllEnemies()){
            if(CollisionManager.checkCollision(getPlayerOne(), e)){
                // (1) Player does not die immediately, but looses lifepoints
                // (2) Player dies immediately, makes it much harder!
                exitGame();
            }
        }

        /** check Projectile-to-Enemy collision */
        for (Enemy e : LevelManager.getCurrentLevelObj().getAllEnemies()){
            for (int i = 0; i < getPlayerOne().getProjectiles().size(); i++){
                if(CollisionManager.checkCollision(e, getPlayerOne().getProjectileAtPosition(i))){
                    e.setPosX(GameViewActivity.GAME_WIDTH+100); //after enemy died, spawn 'em a bit outside
                    highscore.increment(e); //increment the highscore
                    Log.d(TAG, "Highscore = " + highscore.value());
                }
            }
        }
    }

    /*********************************************************
     * 3. Game Over Methods *
     *********************************************************/
    public void exitGame() {
        //TODO guess the thread blocks it!
        Toast.makeText(this.getActivityContext(), "Game over", Toast.LENGTH_SHORT).show(); //TODO: why does this shit not show up
        boolean retry = true;
        thread.setRunning(false);
        Log.d(TAG, "exitGame: Trying to exit game."); //but this is logged?
        Toast.makeText(this.getActivityContext(), "Game over", Toast.LENGTH_SHORT).show();
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
