package yourowngame.com.yourowngame.classes.gamelevels;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.interfaces.IGameObject;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.manager.SoundMgr;
import yourowngame.com.yourowngame.classes.counters.Highscore;

public abstract class Level {
    private static final String TAG = "Level";
    private Activity activity;

    protected static SoundMgr soundMgr = new SoundMgr(); //static because always only one soundMgr instance
    private int levelNameResId; //Level name (maybe to show to user [e.g. Die dunkle Gruft, usw.] als Strings.xml res id for multilinguality!
    private Player player;
    private ArrayList<Background> allBackgroundLayers = new ArrayList<>(); //Background layers for each level (as Arraylist to avoid NullpointerExceptions, so we just do not allow gaps)
    private ArrayList<Enemy> allEnemies = new ArrayList<>(); //MUST NOT BE STATIC (different levels, different enemies), All enemies on screen (will be spawned again if isGone) for specific level
    private ArrayList<Fruit> allFruits = new ArrayList<>();
    private ArrayList<LevelAssignment> allLevelAssignments = new ArrayList<>();

    @Enhance (message = "maybe it's better to put it back into gameview or gameviewActivity and all levels access it. " +
            "So we just have to reset the highscore on levelchange (what we have to do anyway)." +
            "totally, that is utterly useless & non OO")
    private Highscore levelHighscore = new Highscore(); //add Level-dependent Highscore
    //TODO: other level-dependent members/values

    //Do not make more constructors
    public Level(@NonNull Activity activity) {
        Log.d(TAG, "Level: ###################### STARTING LOADING LEVEL ###############################");
        this.setActivity(activity);
        determineMetaData();
        determinePlayer();
        determineBackgroundLayers();
        determineAllEnemies();
        determineAllFruits();
        determineLevelAssigments();

        /* Initialize all objs, bc. this is not done anymore in constructors. */
        initializeLevelProperties();
        Log.d(TAG, "Level: ###################### ENDED LOADING LEVEL ##################################");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        cleanUpLevelProperties();
    }

    @Enhance (message = "If we want to change player in future (shop etc.) we SHOULD NOT make the player " +
            "level-dependent! So this method will be obsolete in future!")
    protected abstract void determinePlayer();
    protected abstract void determineBackgroundLayers();
    protected abstract void determineAllEnemies();
    protected abstract void determineAllFruits();
    protected abstract void playBackgroundMusic();
    /** Put all wanted LevelAssigment Objs in there. These will be accessed and evaluated in areLevelAssigmentsAchieved().*/
    protected abstract void determineLevelAssigments();
    /** Defines default data (normally this method does not contain any logic* operations). E.g. setting the levelName by getting it from the strings.xml*/
    protected abstract void determineMetaData();
    /**
     * Difficulty range is between 0 - 5 (double)
     * value increases or decreases difficulty in a game
     */

    /** Check whether the assignments are achieved, or not. Every Level implements their assignments itself! */
    public boolean areLevelAssignmentsAchieved() {
        boolean isLevelAchieved = false;
        for (LevelAssignment levelAssignment : this.getAllLevelAssignments()) {
            if (!levelAssignment.isLevelAssignmentAchieved()) {
                return false;
            } else {
                /*if assignment not false put it to true, as long as no false one is found the method will return true
                 So method also returns false, if no levelAssigments were specified.*/
                isLevelAchieved = true;
            }
        }
        return isLevelAchieved;
    }
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

        //no reason to cleanup levelAssignments, because they calculate their result out from extern values (highscore e.g.) which are resetted.

        //Because level-dependent, also reset when level has changed
        getCurrentLevelHighscore().resetCounter();

        Log.d(TAG, "cleanUpLevelProperties: Clean up all level properties.");
    }

    /** Initialize here now, because it might be the same for all levels :) */
    public void initializeLevelProperties() {
        //CleanUp Player
        this.getPlayer().initialize();
        //CleanUp Enemies
        for (Enemy enemy : this.getAllEnemies()) {enemy.initialize();}
        //CleanUp Bglayers
        for (Background background : this.getAllBackgroundLayers()) {background.initialize();}
        //CleanUp all fruits
        for (Fruit fruit : this.getAllFruits()) {fruit.initialize();}

        Log.d(TAG, "cleanUpLevelProperties: Clean up all level properties.");
    }

    /**
     * We really need to sort things out here, to much getter/setter
     */
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

    public ArrayList<LevelAssignment> getAllLevelAssignments() {
        return allLevelAssignments;
    }

    public void setAllLevelAssignments(ArrayList<LevelAssignment> allLevelAssignments) {
        this.allLevelAssignments = allLevelAssignments;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
