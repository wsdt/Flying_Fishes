package yourowngame.com.yourowngame.gameEngine;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.player.IPlayer;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.RoboticEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.SpawnEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.BomberEnemy;
import yourowngame.com.yourowngame.classes.background.BackgroundManager;
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
    private GameViewActivity activityContext;
    private SurfaceHolder holder;
    private GameLoopThread thread;
    private Player playerOne;
    private OnTouchHandler touchHandler = new OnTouchHandler();
    private FrameLayout layout;
    private Highscore highscore = new Highscore();

    // ENEMIES are level-dependent, so specific level obj should own a list with all enemies. (LevelMgr.CURRENT_LEVEL)

    /** Without this method our app will crash, keep it XML needs this constructor */
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    /** Constructor for creating in runtime (not used currently, but recommended to keep though)*/
    public GameView(Context context) {
        super(context);
    }

    /** This method MUST NOT be called in constructor,
     * moved GameView-Creation to XML so btns might be in foreground,
     * but there we can't call canvas.lock (so thread can't be started unless
     * activity:onCreate() is done.*/
    public void startGame(GameViewActivity context) {
        this.setActivityContext(context);

        /** Initialize View Components */
        layout = context.getView();

        /** Initialize GameObjects & eq here! After initializing, the GameLoop will start!*/
        initGameObjects();

        /** React to user input */
        getRootView().setOnTouchListener(touchHandler);

        /**************************************
         * Start of the Surface & Thread Page *
         **************************************/
        thread = new GameLoopThread(this);
        holder = getHolder();

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
                R.drawable.player_heli_blue_3, R.drawable.player_heli_blue_2}, IPlayer.DEFAULT_ROTATION, "Rezy"));

        /** Initializing Player*/
        getPlayerOne().initialize(this.getActivityContext());

        /** Initializes() of backgrounds are in constructor itself */

        /** Prepare Highscore */
        this.getHighscore().addListener(new IHighscore_Observer() {
            @Override
            public void onHighscoreChanged() {
                getActivityContext().setNewHighscoreOnUI(getHighscore());
            }
        });
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

                // (2.) draw player
                getPlayerOne().draw(this.getActivityContext(), canvas, loopCount);

                // (3.) draw Projectiles
                getPlayerOne().drawProjectiles(this.getActivityContext(), canvas, loopCount);

                // (4.) draw enemies
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
        /*TODO: We have a list for each enemy class, but also have one with all enemies in Level-Obj (getCurrent()),
        todo so we could save maybe memory if we dispose enemyList and do it all in Level-Enemy-List. Then we could
        todo maybe also do sth like this:

            todo: LevelMgr.getCurrentLevelObj().getAllEnemies.updateAll(this.playerOne, null, null);

            todo why not! :D
        */
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
                CollisionManager.playPlayerEnemyCollisionSound(this.getActivityContext());
                exitGame();
            }
        }

        /** check Projectile-to-Enemy collision */
        for (Enemy e : LevelManager.getCurrentLevelObj().getAllEnemies()){
            for (int i = 0; i < getPlayerOne().getProjectiles().size(); i++){
                if(CollisionManager.checkCollision(e, getPlayerOne().getProjectileAtPosition(i))){
                    //enemy dies, spawns on the other side
                    e.setPosX(GameViewActivity.GAME_WIDTH+100);
                    //projectile needs to be deleted
                    getPlayerOne().getProjectiles().remove(getPlayerOne().getProjectileAtPosition(i));
                    //play sound when enemy dies
                    CollisionManager.playProjectileEnemyCollisionSound(this.getActivityContext());
                    //increment the players highscore
                    getHighscore().increment(e);

                    Log.d(TAG, "Highscore = " + getHighscore().getValue());
                }
            }
        }
    }

    /*********************************************************
     * 3. Game Over Methods                                  *
     *********************************************************/
    public void exitGame() {
        boolean retry = true;
        thread.setRunning(false);
        Log.d(TAG, "exitGame: Trying to exit game."); //but this is logged?
        while (retry) {
            try {
                Log.d(TAG, "exitGame: Trying to join threads and showing dialog before.");
                getActivityContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Resources res = getActivityContext().getResources();
                        (new DialogMgr(getActivityContext())).showDialog_Generic(
                                res.getString(R.string.dialog_generic_gameOver_title),
                                String.format(res.getString(R.string.dialog_generic_gameOver_msg),getHighscore().getValue()),
                                res.getString(R.string.dialog_generic_button_positive_gameOverAccept),
                                res.getString(R.string.dialog_generic_button_negative_gameOverRevive),
                                R.drawable.app_icon_gameboy, new ExecuteIfTrueSuccess_or_ifFalseFailure_afterCompletation() {
                                    @Override
                                    public void success_is_true() {
                                        //End everything here in future, so we could resume game when entering failure_is_false :)
                                        try {
                                            thread.join();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        getActivityContext().finish(); //todo: does not work (also do it in runOnUI but in success_true() of dialog
                                    }

                                    @Override
                                    public void failure_is_false() {

                                    }
                                }
                        );
                    }
                });
                retry = false;
            } catch (ClassCastException e) {
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

    public GameViewActivity getActivityContext() {
        return activityContext;
    }

    public void setActivityContext(GameViewActivity activityContext) {
        this.activityContext = activityContext;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Highscore getHighscore() {
        return highscore;
    }

    public void setHighscore(Highscore highscore) {
        this.highscore = highscore;
    }
}
