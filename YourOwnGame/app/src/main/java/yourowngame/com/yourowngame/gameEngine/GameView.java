package yourowngame.com.yourowngame.gameEngine;

import android.content.Context;
import android.content.res.Resources;
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
import yourowngame.com.yourowngame.classes.annotations.Bug;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.commercial.AdManager;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
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
        getRootView().setOnTouchListener(getMultiTouchHandler());

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
                startExitGameProcedure();
            }
        });
    }

    //initialize components that match GameObject()
    @Bug (problem = "Highscore on UI works for first level, but in second level it just get's incremented" +
            "hiddenly (you will see it when game is over), but the highscore value in Textview is not refreshed." +
            "Additionally it works also in second level if we go during the game to the mainActivity and then back, " +
            "which causes this method called again(!) and then it WORKS.",
    possibleSolution = "So I think we should find a way to register this listener in the levelObj itself!",
    priority = Bug.Priority.HIGH, byDeveloper = Constants.Developers.WSDT)
    private void initGameObjects() {
        /** Prepare Highscore */
        this.getHighscore().addListener(new IHighscore_Observer() {
            @Override
            public void onHighscoreChanged() {
                Log.d(TAG, "initGameObjects:onHighscoreChanged: Highscore has changed!");

                /*Refresh Highscore lbl*/
                GameView.this.getActivityContext().setNewHighscoreOnUI();

                /*Evaluate whether user achieved level or not.*/
                if (LevelManager.getInstance(GameView.this.getActivityContext()).getCurrentLevelObj().areLevelAssignmentsAchieved()) {
                    LevelManager.getInstance(GameView.this.getActivityContext()).initiateLevelChangeProcess();
                }
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
                Level currLevel = LevelManager.getInstance(GameView.this.getActivityContext()).getCurrentLevelObj();

                // (1.) draw background
                for (Background background : currLevel.getAllBackgroundLayers()) {
                    background.drawBackground(canvas);
                }

                // (2.) draw player
                currLevel.getPlayer().draw(this.getActivityContext(), canvas, loopCount);

                // (3.) draw Projectiles
                currLevel.getPlayer().drawProjectiles(this.getActivityContext(), canvas, loopCount);

                // (4.) draw enemies
                for (Enemy enemy : currLevel.getAllEnemies()) {
                    enemy.draw(this.getActivityContext(), canvas, loopCount);
                }

                // (5.) draw fruits, need to implement a timer, to push a fruit
                for (Fruit fruit : currLevel.getAllFruits()) {
                    fruit.draw(this.getActivityContext(), canvas, loopCount);
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
        Level currLevel = LevelManager.getInstance(this.getActivityContext()).getCurrentLevelObj();

        //Update player
        currLevel.getPlayer().update(null,
                GameView.this.getMultiTouchHandler().isMultiTouched() || GameView.this.getMultiTouchHandler().isMoving(),
                false);

        //Update all enemies
        for (Enemy enemy : currLevel.getAllEnemies()) {
            enemy.update(currLevel.getPlayer(), null, null);
        }

        //Update all fruits
        for (Fruit fruit : currLevel.getAllFruits()) {
            fruit.update(null,null,null);
        }

        //Update bglayers
        for (Background background : currLevel.getAllBackgroundLayers()) {
            background.updateBackground();
        }

        //Update bullets
        currLevel.getPlayer().updateProjectiles();

        /** Check Shooting */
        if(getMultiTouchHandler().isShooting()){
            currLevel.getPlayer().addProjectiles(getActivityContext());
            getMultiTouchHandler().stopShooting();
        }

        /** check Collision with Border */
        if (currLevel.getPlayer().hitsTheGround(this)) {
            startExitGameProcedure();
        }

        /** check Player-to-Enemy collision */
        for (Enemy e : currLevel.getAllEnemies()){
            if(CollisionManager.checkCollision(currLevel.getPlayer(), e)){
                CollisionManager.playPlayerEnemyCollisionSound(this.getActivityContext());
                startExitGameProcedure();
            }
        }

        /** check Projectile-to-Enemy collision */
        for (Enemy e : currLevel.getAllEnemies()){
            for (int i = 0; i < currLevel.getPlayer().getProjectiles().size(); i++){
                if(CollisionManager.checkCollision(e, currLevel.getPlayer().getProjectileAtPosition(i))){
                    //enemy dies, spawns on the other side
                    e.resetWidthAndHeightOfEnemy();
                    //projectile needs to be deleted
                    currLevel.getPlayer().getProjectiles().remove(currLevel.getPlayer().getProjectileAtPosition(i));
                    //play sound when enemy dies
                    CollisionManager.playProjectileEnemyCollisionSound(this.getActivityContext());
                    //increment the players highScore
                    getHighscore().increment(e);

                    Log.d(TAG, "Highscore = " + getHighscore().getValue());
                }
            }
        }

        /** Check player to Fruit Collision
         *
         * i know, we could just check which subclass it is, but again, bad smell
         * */
        for (Fruit fruit : LevelManager.getInstance(this.getActivityContext()).getCurrentLevelObj().getAllFruits()) {
            if (CollisionManager.checkCollision(LevelManager.getInstance(this.getActivityContext()).getCurrentLevelObj().getPlayer(), fruit)) {
                fruit.collected();
                getHighscore().increment(fruit);

                //Eats fruit, collects it, fruit vanishes
                Log.d(TAG, "Player collected a fruit.");
            }
        }
    }

    /*********************************************************
     * 3. Game Over Methods                                  *
     *********************************************************/
    public void startExitGameProcedure() {
        boolean retry = true;
        thread.setRunning(false);
        Log.d(TAG, "startExitGameProcedure: Trying to exit game."); //but this is logged?
        while (retry) {
            try {
                Log.d(TAG, "startExitGameProcedure: Trying to join threads and showing dialog before.");
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
                                        exitGameNow();
                                    }

                                    @Override
                                    public void failure_is_false() {
                                        //E.g. revive button clicked
                                        new AdManager(GameView.this.getActivityContext()).loadRewardedVideoInRewardActivity(
                                                GameView.this.getActivityContext(), new ExecuteIfTrueSuccess_or_ifFalseFailure_afterCompletation() {
                                                    @Override
                                                    public void success_is_true() {
                                                        //TODO: put here revive method/procedure (e.g. put all positions a few seconds back!)
                                                    }

                                                    @Override
                                                    public void failure_is_false() {
                                                        //don't revive so just do nothing, because rewarded ad does this for us (but to restart game do success from outer interface)
                                                        exitGameNow(); //sozusagen doch kein revive
                                                    }
                                                }, null //don't change activity, because user want to be revived!
                                        );
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

    /** Small helper method for startExitGameProcedure(), which really cleans/exits the game WITHOUT any validation!
     * A wrong call will surely cause an exception. */
    private void exitGameNow() {
        //End everything here in future, so we could resume game when entering failure_is_false :)
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //save highscore before cleaning
        new SharedPrefStorageMgr(getActivityContext()).saveNewHighscoreEntry(getHighscore().getValue());
        getHighscore().removeAllListeners();

        //Cleanup all enemy objects etc. (so restart of game is possible without old enemy positions, etc.)
        LevelManager.getInstance(this.getActivityContext()).resetGame(); //reset gameLevelState so user starts from level 0 again.

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

    public Highscore getHighscore() { //Dummy method for making the code more legible
        return LevelManager.getInstance(this.getActivityContext()).getCurrentLevelObj().getLevelHighscore();
    }


    public OnMultiTouchHandler getMultiTouchHandler() {
        return multiTouchHandler;
    }

    public void setMultiTouchHandler(OnMultiTouchHandler multiTouchHandler) {
        this.multiTouchHandler = multiTouchHandler;
    }
}
