package yourowngame.com.yourowngame.gameEngine.surfaces;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.barriers.Barrier;
import yourowngame.com.yourowngame.classes.actors.barriers.BarrierMgr;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitMgr;
import yourowngame.com.yourowngame.classes.actors.projectiles.ProjectileMgr;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.game_modes.DrawableLevel;
import yourowngame.com.yourowngame.classes.observer.Observer_HighScore;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.Level;
import yourowngame.com.yourowngame.classes.manager.WorldMgr;
import yourowngame.com.yourowngame.classes.manager.CollisionMgr;
import yourowngame.com.yourowngame.classes.manager.dialog.GameOverDialog;
import yourowngame.com.yourowngame.classes.manager.dialog.LevelAchievedDialog;
import yourowngame.com.yourowngame.classes.manager.storage.SharedPrefStorageMgr;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;
import yourowngame.com.yourowngame.classes.manager.MultiTouchMgr;
import yourowngame.com.yourowngame.classes.observer.interfaces.IHighscore_Observer;

/**
 * GameView Surface, draw players here and in the end add it to the GameViewActivity
 */

public class GameView extends DrawableSurfaces {
    private static final String TAG = "GameView";
    private DrawableLevel currLevelObj;
    private static MultiTouchMgr multiTouchHandler = new MultiTouchMgr();

    /**
     * Without this method our app will crash, keep it XML needs this constructor
     */
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    /**
     * Constructor for creating in runtime (not used currently, but recommended to keep though)
     */
    public GameView(Context context) {
        super(context);
    }


    /**
     * This method MUST NOT be called in constructor,
     * moved GameView-Creation to XML so btns might be in foreground,
     * but there we can't call canvas.lock (so thread can't be started unless
     * activity:onCreate() is done.
     *
     * @param context: IMPORTANT ONLY ALLOW GameViewActivity here and not the DrawableSurface!
     */
    public void startGame(@NonNull GameViewActivity context, @NonNull DrawableLevel currLevelObj) {
        this.setDrawableSurfaceActivity(context);



        /*Set Projectiles TODO later on, the level should decide by a simple method wethere u can shoot or not!*/
        ProjectileMgr.runDefaultConfiguration(context);

        //Set LvlMgr
        Log.d(TAG, "startGame: Lvl-obj -> "+currLevelObj);
        this.setCurrLevelObj(currLevelObj);

        /* Initialize GameObjects & eq here! After initializing, the GameLoop will start! also cleanup */
        this.getCurrLevelObj().initiate((GameViewActivity) this.getDrawableSurfaceActivity());

        /* React to user input */
        getRootView().setOnTouchListener(getMultiTouchHandler());

        //Draw everything etc.
        this.initializeDrawing();
    }

    /********************************
     * 1. Draw Objects here         *
     ********************************/
    @Enhance(byDeveloper = "Solution49", message = "why passing the loopcount and the canvas in an extra method? " +
            "we could just add params to the draw method by draw(int amount, canvas canvas), surely every" +
            "draw method will need to have those params")
    @Override
    public void drawAll(@NonNull Canvas canvas, long loopCount) { //Create separate method, so we could add some things here
        Log.d(TAG, "redraw: Trying to invalidate/redraw GameView.");
        if (canvas != null) { //NOT REALLY!!! (system calls)
            //in loop, BUT we don't do anything if already set
            canvas.drawColor(0, PorterDuff.Mode.CLEAR); //remove previous bitmaps etc. (it does not work to set here only bg color!, because of mode)

            try {
                // (1.) draw background
                for (Background background : this.getCurrLevelObj().getBgLayers()) {
                    background.setCanvas(canvas);
                    background.draw();
                }

                // (2.) draw player
                this.getCurrLevelObj().getPlayer().setCanvas(canvas);
                this.getCurrLevelObj().getPlayer().setLoopCount(loopCount);
                this.getCurrLevelObj().getPlayer().draw();

                // (3.) draw Projectiles
                ProjectileMgr.drawProjectiles(canvas, loopCount);

                // (4.) draw enemies
                for (Enemy enemy : this.getCurrLevelObj().getEnemies()) {
                    enemy.setCanvas(canvas);
                    enemy.setLoopCount(loopCount);
                    enemy.draw();
                }

                // (5.) draw fruits
                for (Fruit fruit : this.getCurrLevelObj().getFruits()) {
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
            startExitProcedure();
        }
    }

    /*****************************
     * 2. Update GameObjects here *
     *****************************/
    @Override
    public void updateAll() {
        /* Uppdate player */
        this.getCurrLevelObj().getPlayer().setGoUp(getMultiTouchHandler().isMoving());
        this.getCurrLevelObj().getPlayer().update();

        /* Update all enemies */
        for (Enemy enemy : this.getCurrLevelObj().getEnemies()) {
            enemy.setTargetGameObj(this.getCurrLevelObj().getPlayer()); //also not all enemies need this
            enemy.update();
        }

        /* Update all fruits */
        for (Fruit fruit : this.getCurrLevelObj().getFruits()) {
            fruit.update();
        }

        /* Update bglayers */
        for (Background background : this.getCurrLevelObj().getBgLayers()) {
            background.update();
        }


        /* Update bullets */
        ProjectileMgr.updateProjectiles();

        /* Check for Collisions - if player hits the ground or gets hit by an enemy or hits barrier, game stops!*/
        if (this.getCurrLevelObj().getCollisionMgr().checkForCollisions()) {
            startExitProcedure();
        }

        /* Check Shooting */
        if (getMultiTouchHandler().isShooting()) {
            ProjectileMgr.shoot(this.getCurrLevelObj().getPlayer(), false); //false = enable frequency capping
            //getMultiTouchHandler().stopShooting();
        }
    }

    /*********************************************************
     * 3. Game Over Methods                                  *
     ********************************************************
     *
     * ExitProcedure e.g. not needed in WorldView. So not abstract. */
    @Override
    public void startExitProcedure() {
        boolean retry = true;
        getThread().setRunning(false);
        Log.d(TAG, "startExitProcedure: Trying to exit game."); //but this is logged?
        while (retry) {
            try {
                Log.d(TAG, "startExitProcedure: Trying to join threads and showing dialog before.");
                this.getDrawableSurfaceActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GameOverDialog.show(GameView.this, currLevelObj);
                    }
                });
                retry = false;

            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Small helper method for startExitProcedure(), which really cleans/exits the game WITHOUT any validation!
     * A wrong call will surely cause an exception.
     */
    @Override
    public void exitNow() {
        //End everything here in future, so we could resume game when entering failure_is_false :)
        try {
            getThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //save highscore before cleaning
        //TODO: Remove in future (bc. this method is also called when exiting game by dialogs although game was not over)
        new SharedPrefStorageMgr(getDrawableSurfaceActivity()).saveNewHighscoreEntry(getHighscore().getValue());
        getHighscore().removeAllListeners();

        //Cleanup all enemy objects etc. (so restart of game is possible without old enemy positions, etc.)
        WorldMgr.resetGame(this.getCurrLevelObj()); //reset gameLevelState so user starts from level 0 again.

        getDrawableSurfaceActivity().finish(); //todo: does not work (also do it in runOnUI but in success_true() of dialog
    }

    /* ********************************************************
     * 4. Getters & Setters
     *********************************************************/

    /*** this returns the current HighScore */
    public Observer_HighScore getHighscore() {
        return Level.getLevelHighscore();
    }


    public static MultiTouchMgr getMultiTouchHandler() {
        return multiTouchHandler;
    }

    public static void setMultiTouchHandler(MultiTouchMgr multiTouchHandler) {
        GameView.multiTouchHandler = multiTouchHandler;
    }

    /** Current levelObj which player can play now.*/
    public DrawableLevel getCurrLevelObj() {
        return currLevelObj;
    }

    public void setCurrLevelObj(DrawableLevel currLevelObj) {
        this.currLevelObj = currLevelObj;
    }
}
