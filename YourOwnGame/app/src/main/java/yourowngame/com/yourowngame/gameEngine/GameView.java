package yourowngame.com.yourowngame.gameEngine;

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
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.BobaEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.HappenEnemy;
import yourowngame.com.yourowngame.classes.actors.enemy.specializations.RocketFish;
import yourowngame.com.yourowngame.classes.annotations.Bug;
import yourowngame.com.yourowngame.classes.background.BackgroundManager;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;
import yourowngame.com.yourowngame.classes.manager.DialogMgr;
import yourowngame.com.yourowngame.classes.storagemgr.SharedPrefStorageMgr;
import yourowngame.com.yourowngame.classes.manager.interfaces.ExecuteIfTrueSuccess_or_ifFalseFailure_afterCompletation;
import yourowngame.com.yourowngame.gameEngine.interfaces.IHighscore_Observer;

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
    
    private OnMultiTouchHandler multiTouchHandler = new OnMultiTouchHandler();
    private FrameLayout layout;
    private Highscore highscore = new Highscore();
    private Highscore coins = new Highscore(); //todo: might work, but yeah could cause confusion in future, you're totally right! should get its own class.

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
        getRootView().setOnTouchListener(multiTouchHandler);

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
        /** Prepare Highscore */
        this.getHighscore().addListener(new IHighscore_Observer() {
            @Override
            public void onHighscoreChanged() {
                getActivityContext().setNewHighscoreOnUI(getHighscore(), getCoinsHighscore());
            }
        });

        //LevelManager.getInstance(BackgroundManager.getInstance(this)).createDefaultLevelList(); //for recrafting level objs (unfortunately necessary)
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
                LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer().draw(this.getActivityContext(), canvas, loopCount);

                // (3.) draw Projectiles
                LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer().drawProjectiles(this.getActivityContext(), canvas, loopCount);

                // (4.) draw enemies
                HappenEnemy.drawAll(this.getActivityContext(), canvas, loopCount);
                RocketFish.drawAll(this.getActivityContext(), canvas, loopCount);
                BobaEnemy.drawAll(this.getActivityContext(), canvas, loopCount);

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
        /** (1.) update the Player*/           //^here we will later add a another boolean, for older devices, so each touch results in a move of the player!
        LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer().update(null, this.multiTouchHandler.isMultiTouched() || this.multiTouchHandler.isMoving(), false);

        /** (2.) update the Enemies*/
        /*TODO: We have a list for each enemy class, but also have one with all enemies in Level-Obj (getCurrent()),
        todo so we could save maybe memory if we dispose enemyList and do it all in Level-Enemy-List. Then we could
        todo maybe also do sth like this:

            todo: LevelMgr.getCurrentLevelObj().getAllEnemies.updateAll(this.playerOne, null, null);


        /** (2.) update the Enemies
        TODO: Placing the Enemies in their respective Place
            -> LevelMgr.getCurrentLevelObj().getAllEnemies.updateAll(this.playerOne, null, null);
        */
        HappenEnemy.updateAll(LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer(), null, null);
        RocketFish.updateAll(LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer(), null, null);
        BobaEnemy.updateAll(LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer(), null, null);

        /** Check Shooting */
        if(multiTouchHandler.isShooting()){
            LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer().addProjectiles(getActivityContext());
            multiTouchHandler.stopShooting();
        }

        /** update the Bullets*/
        LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer().updateProjectiles();

        /** check Collision with Border */
        if (LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer().hitsTheGround(this)) {
            exitGame();
        }

        /** update the background */
        BackgroundManager.getInstance(this).updateAllBackgroundLayers();

        /** check Player-to-Enemy collision */
        for (Enemy e : LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getAllEnemies()){
            if(CollisionManager.checkCollision(LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer(), e)){
                CollisionManager.playPlayerEnemyCollisionSound(this.getActivityContext());
                exitGame();
            }
        }

        /** check Projectile-to-Enemy collision */
        for (Enemy e : LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getAllEnemies()){
            for (int i = 0; i < LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer().getProjectiles().size(); i++){
                if(CollisionManager.checkCollision(e, LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer().getProjectileAtPosition(i))){
                    //enemy dies, spawns on the other side
                    e.resetWidthAndHeightOfEnemy();
                    //projectile needs to be deleted
                    LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer().getProjectiles().remove(LevelManager.getInstance(BackgroundManager.getInstance(this)).getCurrentLevelObj().getPlayer().getProjectileAtPosition(i));
                    //play sound when enemy dies
                    CollisionManager.playProjectileEnemyCollisionSound(this.getActivityContext());
                    //increment the players highScore
                    getHighscore().increment(e);
                    //if the highscore is over/equal 5_000, reset highscore, add 1 Coin (later on 10_000 be better)
                    if(getHighscore().getValue() >= 5_000){
                        getCoinsHighscore().increment();
                        getHighscore().resetCounter();
                    }

                    Log.d(TAG, "Highscore = " + getHighscore().getValue());
                }
            }
        }
    }

    /*********************************************************
     * 3. Game Over Methods                                  *
     *********************************************************/
    @Bug (byDeveloper = Constants.Developers.WSDT,
        problem = "Exit itself works and everything is cleaned. But the restart itself does not work, because game starts from the same point.")
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
                                String.format(res.getString(R.string.dialog_generic_gameOver_msg), getHighscore().getValue()),
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

                                        //save highscore before cleaning
                                        new SharedPrefStorageMgr(getActivityContext()).saveNewHighscoreEntry(getHighscore().getValue());

                                        //Cleanup all enemy objects etc. (so restart of game is possible without old enemy positions, etc.)
                                        LevelManager.getInstance(BackgroundManager.getInstance(GameView.this)).getCurrentLevelObj().cleanUpLevelProperties();

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

    public Highscore getHighscore() {
        return highscore;
    }

    public Highscore getCoinsHighscore() { return coins; }

    public void setHighscore(Highscore highscore) {
        this.highscore = highscore;
    }

    public void setCoinsHighscore(Highscore coins) {this.coins = coins; }
}
