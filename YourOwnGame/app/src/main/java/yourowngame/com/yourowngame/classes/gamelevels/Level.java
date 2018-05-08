package yourowngame.com.yourowngame.classes.gamelevels;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.manager.SoundMgr;
import yourowngame.com.yourowngame.classes.counters.Highscore;

public abstract class Level {
    private static final String TAG = "Level";
    private Context context;

    protected static SoundMgr soundMgr = new SoundMgr(); //static because always only one soundMgr instance
    private int levelNameResId; //Level name (maybe to show to user [e.g. Die dunkle Gruft, usw.] als Strings.xml res id for multilinguality!
    private Player player;
    private ArrayList<Background> allBackgroundLayers = new ArrayList<>(); //Background layers for each level (as Arraylist to avoid NullpointerExceptions, so we just do not allow gaps)
    private ArrayList<Enemy> allEnemies = new ArrayList<>(); //MUST NOT BE STATIC (different levels, different enemies), All enemies on screen (will be spawned again if isGone) for specific level
    private ArrayList<Fruit> allFruits = new ArrayList<>();
    private Highscore levelHighscore = new Highscore(); //add Level-dependent Highscore
    //TODO: other level-dependent members/values

    public Level(@NonNull Context context) {
        Log.d(TAG, "Level: ###################### STARTING LOADING LEVEL ###############################");
        this.setContext(context);
        determineMetaData();
        determinePlayer();
        determineBackgroundLayers();
        determineAllEnemies();
        determineAllFruits();
        Log.d(TAG, "Level: ###################### ENDED LOADING LEVEL ##################################");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        cleanUpLevelProperties();
    }

    protected abstract void determinePlayer();
    protected abstract void determineBackgroundLayers();
    protected abstract void determineAllEnemies();
    protected abstract void determineAllFruits();
    protected abstract void playBackgroundMusic();
    /** Defines default data (normally this method does not contain any logic* operations). E.g. setting the levelName by getting it from the strings.xml*/
    protected abstract void determineMetaData();

    /** Cleaning up here now, because it might be the same for all levels :) */
    public void cleanUpLevelProperties() {
        //CleanUp Player
        this.getPlayer().cleanup();
        //CleanUp Enemies
        for (Enemy enemy : this.getAllEnemies()) {enemy.cleanup();}
        //CleanUp Bglayers
        for (Background background : this.getAllBackgroundLayers()) {background.cleanup();}
        //CleanUp all fruits
        for (Fruit fruit : this.getAllFruits()) {fruit.cleanup();}

        //Because level-dependent, also reset when level has changed
        getCurrentLevelHighscore().resetCounter();

        Log.d(TAG, "cleanUpLevelProperties: Clean up all level properties.");
    }

    /** Check whether the assignments are achieved, or not. Every Level implements their assignments itself! */
    public abstract boolean areLevelAssignmentsAchieved();

    //GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public ArrayList<Background> getAllBackgroundLayers() {
        return allBackgroundLayers;
    }

    public void setAllBackgroundLayers(ArrayList<Background> allBackgroundLayers) {
        this.allBackgroundLayers = allBackgroundLayers;
    }
    public int getLevelNameResId() {
        return levelNameResId;
    }
    public void setLevelNameResId(int levelNameResId) {
        this.levelNameResId = levelNameResId;
    }
    public ArrayList<Enemy> getAllEnemies() {
        return allEnemies;
    }
    public void setAllEnemies(ArrayList<Enemy> allEnemies) {
        this.allEnemies = allEnemies;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public ArrayList<Fruit> getAllFruits() {
        return allFruits;
    }
    public void setAllFruits(ArrayList<Fruit> allFruits) {
        this.allFruits = allFruits;
    }
    public Highscore getCurrentLevelHighscore() {
        return levelHighscore;
    }
    public void setLevelHighscore(Highscore levelHighscore) {
        this.levelHighscore = levelHighscore;
    }
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
}
