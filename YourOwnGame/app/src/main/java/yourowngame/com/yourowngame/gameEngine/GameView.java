package yourowngame.com.yourowngame.gameEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.projectiles.Projectile;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.counters.FruitCounter;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;
import yourowngame.com.yourowngame.classes.manager.CollisionMgr;
import yourowngame.com.yourowngame.classes.manager.dialog.GameOverDialog;
import yourowngame.com.yourowngame.classes.manager.dialog.LevelAchievedDialog;
import yourowngame.com.yourowngame.classes.manager.storage.SharedPrefStorageMgr;
import yourowngame.com.yourowngame.classes.counters.HighScore;
import yourowngame.com.yourowngame.gameEngine.interfaces.IHighscore_Observer;

/**
 * Created by Solution on 16.02.2018.
 * <p>
 * GameView Surface, draw players here and in the end add it to the GameViewActivity
 */

public class GameView extends SurfaceView {
    private static final String TAG = "GameView";
    private GameViewActivity activityContext;
    private LevelManager levelManager;
    private SurfaceHolder holder;
    private GameLoopThread thread;
    private CollisionMgr collisionMgr;
    
    private OnMultiTouchHandler multiTouchHandler = new OnMultiTouchHandler();
    private FrameLayout layout;

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

        //Set LvlMgr
        this.setLevelManager(new LevelManager(this.getActivityContext()));

        /** At every Gamestart, get the metrics from screen, otherwise hole Game will crash in future!,
         *  because we used the metric nearly everywhere!! */

        /** Initialize View Components */
        layout = (FrameLayout) context.findViewById(R.id.gameViewLayout);

        /** Initialize GameObjects & eq here! After initializing, the GameLoop will start!*/
        initGameObjects();

        /** React to user input */
        getRootView().setOnTouchListener(getMultiTouchHandler());

        /**************************************
         * Start of the Surface & Thread Page *
         **************************************/
        setThread(new GameLoopThread(this));
        holder = getHolder();

        //Callback-method: 3 Types
        holder.addCallback(new SurfaceHolder.Callback() {

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
                startExitGameProcedure();
            }
        });
    }

    @Enhance(byDeveloper = "Solution",
    message = {"We shouldnt clean Objs AFTER Game exit, we should maybe just clean it before? would make much more sense",
        "Is this already changed in all classes?"},
    priority = Enhance.Priority.LOW)
    private void initGameObjects() {
        /**current starting level */
        final Level currLevel = this.getLevelManager().getCurrentLevelObj();

        /** cleanup Level Properties */
        currLevel.cleanUpLevelProperties();
        /** clean the fruitCounter*/
        FruitCounter.getInstance().cleanUpFruitCounter();
        /** Create CollisionManager*/
        collisionMgr = new CollisionMgr(currLevel, getActivityContext(), getHighscore());


        this.getHighscore().addListener(new IHighscore_Observer() {
            @Override
            public void onHighscoreChanged() {
                Log.d(TAG, "initGameObjects:onHighscoreChanged: HighScore has changed!");

                /*Refresh HighScore lbl*/
                GameView.this.getActivityContext().setNewHighscoreOnUI();

                /*Evaluate whether user achieved level or not. */
                if (currLevel.areLevelAssignmentsAchieved()) {
                    getThread().setRunning(false);
                    getActivityContext().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LevelAchievedDialog.show(GameView.this);
                        }
                    });
                }
            }
        });
    }

    /********************************
     * 1. Draw Objects here         *
     ********************************/
    @Enhance(byDeveloper = "Solution49", message = "why passing the loopcount and the canvas in an extra method? " +
            "we could just add params to the draw method by draw(int amount, canvas canvas), surely every" +
            "draw method will need to have those params")

    public void redraw(Canvas canvas, long loopCount) { //Create separate method, so we could add some things here
        Log.d(TAG, "redraw: Trying to invalidate/redraw GameView.");
        if (canvas != null) {
            //in loop, BUT we don't do anything if already set
            canvas.drawColor(0, PorterDuff.Mode.CLEAR); //remove previous bitmaps etc. (it does not work to set here only bg color!, because of mode)

            try {
                Level currLevel = getLevelManager().getCurrentLevelObj();

                // (1.) draw background
                for (Background background : currLevel.getAllBackgroundLayers()) {
                    background.setCanvas(canvas);
                    background.draw();
                }

                // (2.) draw player
                currLevel.getPlayer().setCanvas(canvas);
                currLevel.getPlayer().setLoopCount(loopCount);
                currLevel.getPlayer().draw();

                // (3.) draw Projectiles
                for (Projectile projectile : currLevel.getPlayer().getProjectiles()) {
                    projectile.setLoopCount(loopCount);
                    projectile.setCanvas(canvas);
                }
                currLevel.getPlayer().drawProjectiles();

                // (4.) draw enemies
                for (Enemy enemy : currLevel.getAllEnemies()) {
                    enemy.setCanvas(canvas);
                    enemy.setLoopCount(loopCount);
                    enemy.draw();
                }

                // (5.) draw fruits
                for (Fruit fruit : currLevel.getAllFruits()) {
                    fruit.setCanvas(canvas);
                    fruit.setLoopCount(loopCount);
                    fruit.draw();
                }


            } catch (Exception e) {
                //Log.e(TAG, "redraw: Could not draw images.");
                e.printStackTrace();
            }
            //Log.d(TAG, "redraw: SUCCESS");
        } else {
            startExitGameProcedure();
        }
    }

    /*****************************
     * 2. Update GameObjects here *
     *****************************/
    public void updateGameObjects() {
        Level currLevel = getLevelManager().getCurrentLevelObj();

        /** Uppdate player */
        currLevel.getPlayer().setGoUp(GameView.this.getMultiTouchHandler().isMoving());
        currLevel.getPlayer().update();

        /** Update all enemies */
        for (Enemy enemy : currLevel.getAllEnemies()) {
            enemy.setTargetGameObj(currLevel.getPlayer()); //also not all enemies need this
            enemy.update();
        }

        /** Update all fruits */
        for (Fruit fruit : currLevel.getAllFruits()) {
            fruit.update();
        }

        /** Update bglayers */
        for (Background background : currLevel.getAllBackgroundLayers()) {
            background.update();
        }

        /** Update bullets */
        currLevel.getPlayer().updateProjectiles();

        /** Check for Collisions - if player hits the ground or gets hit by an enemy, game stops!*/
        if(collisionMgr.checkForCollisions()){
            startExitGameProcedure();
        }

        /** Check Shooting */
        if(getMultiTouchHandler().isShooting()){
            currLevel.getPlayer().addProjectiles();
            getMultiTouchHandler().stopShooting();
        }

        /** Check if levelAssignment is true */
        if(getLevelManager().getCurrentLevelObj().areLevelAssignmentsAchieved()){
            // LevelManager.startDialog()... -> that dialog will be the one from levelAchieved()
            // and the LevelManager will then lead to further action

        }
    }

    /*********************************************************
     * 3. Game Over Methods                                  *
     *********************************************************/
    public void startExitGameProcedure() {
        boolean retry = true;
        getThread().setRunning(false);
        Log.d(TAG, "startExitGameProcedure: Trying to exit game."); //but this is logged?
        while (retry) {
            try {
                Log.d(TAG, "startExitGameProcedure: Trying to join threads and showing dialog before.");
                getActivityContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GameOverDialog.show(GameView.this);
                    }
                });
                retry = false;

            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /** Small helper method for startExitGameProcedure(), which really cleans/exits the game WITHOUT any validation!
     * A wrong call will surely cause an exception. */
    public void exitGameNow() {
        //End everything here in future, so we could resume game when entering failure_is_false :)
        try {
            getThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //save highscore before cleaning
        //TODO: Remove in future (bc. this method is also called when exiting game by dialogs although game was not over)
        new SharedPrefStorageMgr(getActivityContext()).saveNewHighscoreEntry(getHighscore().getValue());
        getHighscore().removeAllListeners();

        //Cleanup all enemy objects etc. (so restart of game is possible without old enemy positions, etc.)
        getLevelManager().resetGame(); //reset gameLevelState so user starts from level 0 again.

        getActivityContext().finish(); //todo: does not work (also do it in runOnUI but in success_true() of dialog
    }

   /*********************************************************
     * 4. Getters & Setters and all of that annoying methods *
     *********************************************************/

    public FrameLayout getLayout() {
        return layout;
    }

    public GameViewActivity getActivityContext() {
        return activityContext;
    }

    public void setActivityContext(GameViewActivity activityContext) {
        this.activityContext = activityContext;
    }

    /** this returns the current HighScore */
    public HighScore getHighscore() {
        return getLevelManager().getCurrentLevelObj().getCurrentLevelHighscore();
    }


    public OnMultiTouchHandler getMultiTouchHandler() {
        return multiTouchHandler;
    }

    public void setMultiTouchHandler(OnMultiTouchHandler multiTouchHandler) {
        this.multiTouchHandler = multiTouchHandler;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public GameLoopThread getThread() {
        return thread;
    }

    public void setThread(GameLoopThread thread) {
        this.thread = thread;
    }
}
