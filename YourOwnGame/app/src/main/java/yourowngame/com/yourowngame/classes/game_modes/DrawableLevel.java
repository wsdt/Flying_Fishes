package yourowngame.com.yourowngame.classes.game_modes;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.DrawableObj;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.actors.projectiles.ProjectileMgr;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.manager.CollisionMgr;
import yourowngame.com.yourowngame.classes.manager.SoundMgr;
import yourowngame.com.yourowngame.classes.observer.Observer_FruitCounter;
import yourowngame.com.yourowngame.classes.observer.Observer_HighScore;
import yourowngame.com.yourowngame.classes.observer.interfaces.IHighscore_Observer;

/** All levels (regardless of mode) must extends from this base class to be drawn on GameView. */
public abstract class DrawableLevel {
    private static final String TAG = "DrawableLevel";

    // IMPORTANT: DO NOT PUT CONTEXT AS CLASS MEMBER //TODO to avoid memory leaks
    private DrawableSurfaceActivity drawableSurfaceActivity;
    protected static SoundMgr soundMgr; //static because always only one soundMgr instance
    private Player player;
    private ArrayList<Background> bgLayers; //Background layers for each level (as Arraylist to avoid NullpointerExceptions, so we just do not allow gaps)
    private ArrayList<Enemy> enemies; //MUST NOT BE STATIC (different levels, different enemies), All enemies on screen (will be spawned again if isGone) for specific level
    private ArrayList<Fruit> fruits;
    private CollisionMgr collisionMgr;

    /** Static to save memory, but this means we have to care about resetting highscore when new lvl starts */
    private static Observer_HighScore levelHighScore = new Observer_HighScore(); //add Level-dependent HighScore
    /** Also static, don't forget to reset. */
    private static Observer_FruitCounter levelFruitCounter = new Observer_FruitCounter();

    public DrawableLevel(@NonNull DrawableSurfaceActivity drawableSurfaceActivity) {
        this.setDrawableSurfaceActivity(drawableSurfaceActivity);
    }

    /** @param gameViewActivity: Only call this method with GameViewActivity!*/
    @CallSuper
    public void initiate(@NonNull final GameViewActivity gameViewActivity) {
        /* Cleanup Level Properties */
        this.cleanUp();

        /* Create CollisionManager*/
        setCollisionMgr(new CollisionMgr(this, this.getDrawableSurfaceActivity(), getLevelHighscore()));

        getLevelHighscore().addListener(new IHighscore_Observer() {
            @Override
            public void onHighscoreChanged() {
                Log.d(TAG, "initiate:onHighscoreChanged: HighScore has changed!");

                /*Refresh HighScore lbl
                 * IMPORTANT to be sure that only GameViewActivity is assigned to GameView. */
                if (!(DrawableLevel.this.getDrawableSurfaceActivity() instanceof GameViewActivity)) {
                    DrawableLevel.this.setDrawableSurfaceActivity(gameViewActivity);
                    Log.e(TAG, "initiate:onHighscoreChanged: Memory leak -> Level has WorldViewActivity and not GameViewActivity.");
                }
                ((GameViewActivity) DrawableLevel.this.getDrawableSurfaceActivity()).setNewHighscoreOnUI();
            }
        });


        // DIRTY HACK :(
        /* To prevent memory leaks change all contexts/drawablesurfaceactivities of current level
        * from WorldViewActivity to GameViewActivity. TODO: Remove this in future and find a smoother
        * solution (no context class-members would be perfect) */
        /*ArrayList<DrawableObj> doList = new ArrayList<>();
        doList.addAll(getBgLayers());
        doList.addAll(getEnemies());
        doList.addAll(getFruits());
        doList.add(getPlayer());

        //set gameViewactivity instead
        for (DrawableObj d : doList) {
            d.setActivity(gameViewActivity);
        }*/
    }

    @CallSuper
    public void cleanUp() {
        //CleanUp Player
        this.getPlayer().cleanup();
        //CleanUp Enemies
        for (Enemy enemy : this.getEnemies()) {enemy.cleanup();}
        //CleanUp Bglayers
        for (Background background : this.getBgLayers()) {background.cleanup();}
        //CleanUp all fruits
        for (Fruit fruit : this.getFruits()) {fruit.cleanup();}

        getLevelHighscore().resetCounter();
        getLevelFruitCounter().resetCounter();

        /* Clean Up Projectiles*/
        ProjectileMgr.cleanUp();

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        cleanUp();
    }

    //GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Background> getBgLayers() {
        return bgLayers;
    }

    public void setBgLayers(ArrayList<Background> bgLayers) {
        this.bgLayers = bgLayers;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Fruit> getFruits() {
        return fruits;
    }

    public void setFruits(ArrayList<Fruit> fruits) {
        this.fruits = fruits;
    }

    public static Observer_HighScore getLevelHighscore() {
        return levelHighScore;
    }
    public static void setLevelHighScore(Observer_HighScore levelHighScore) {
        DrawableLevel.levelHighScore = levelHighScore;
    }

    public static Observer_FruitCounter getLevelFruitCounter() {
        return levelFruitCounter;
    }

    public static void setLevelFruitCounter(Observer_FruitCounter levelFruitCounter) {
        DrawableLevel.levelFruitCounter = levelFruitCounter;
    }

    public CollisionMgr getCollisionMgr() {
        return collisionMgr;
    }

    public void setCollisionMgr(CollisionMgr collisionMgr) {
        this.collisionMgr = collisionMgr;
    }

    public DrawableSurfaceActivity getDrawableSurfaceActivity() {
        return drawableSurfaceActivity;
    }

    public void setDrawableSurfaceActivity(DrawableSurfaceActivity drawableSurfaceActivity) {
        this.drawableSurfaceActivity = drawableSurfaceActivity;
    }
}
