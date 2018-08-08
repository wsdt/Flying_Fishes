package yourowngame.com.yourowngame.classes.gamedesign;


import android.app.Activity;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.manager.SoundMgr;
import yourowngame.com.yourowngame.classes.observer.Observer_FruitCounter;
import yourowngame.com.yourowngame.classes.observer.Observer_HighScore;

public abstract class Level {
    private static final String TAG = "Level";
    private Activity activity;
    protected static SoundMgr soundMgr; //static because always only one soundMgr instance
    private int levelNameResId; //Level name (maybe to show to user [e.g. Die dunkle Gruft, usw.] als Strings.xml res id for multilinguality!
    /** Where on the superior world map is the level located? (x,y) */
    private Point worldMapPosition;
    private Player player;
    private ArrayList<Background> allBackgroundLayers; //Background layers for each level (as Arraylist to avoid NullpointerExceptions, so we just do not allow gaps)
    private ArrayList<Enemy> allEnemies; //MUST NOT BE STATIC (different levels, different enemies), All enemies on screen (will be spawned again if isGone) for specific level
    private ArrayList<Fruit> allFruits;
    private ArrayList<LevelAssignment> allLevelAssignments;

    /** Static to save memory, but this means we have to care about resetting highscore when new lvl starts */
    private static Observer_HighScore levelHighScore = new Observer_HighScore(); //add Level-dependent HighScore
    /** Also static, don't forget to reset. */
    private static Observer_FruitCounter levelFruitCounter = new Observer_FruitCounter();
    //TODO: other level-dependent members/values

    //Do not make more constructors
    public Level(@NonNull Activity activity, @NonNull Point worldMapPosition) {
        Log.d(TAG, "Level: ###################### STARTING LOADING LEVEL ###############################");
        this.setActivity(activity);
        this.setWorldMapPosition(worldMapPosition);

        //TODO: Maybe get rid of this method, but surely make lvlInformation statically accessible.
        this.determineMetaData();


        /* IMPORTANT: DO NOT ADD HERE DETERMINE_OBJ METHODS, WE ONLY APPEND ENEMIES ETC.
        * WHEN NEEDED AND NOT AT LVLOBJ_CREATION. THIS HAS THE ADVANTAGE THAT THE LVL_OBJ
        * AND SUPERIOR WORLD_OBJS REMAIN SMALL UNLESS WE NEED A SPECIFIC LVL OBJECT (WHEN
        * WE PLAY IT). */
        Log.d(TAG, "Level: ###################### ENDED LOADING LEVEL ##################################");
    }

    public static Observer_FruitCounter getLevelFruitCounter() {
        return levelFruitCounter;
    }

    public static void setLevelFruitCounter(Observer_FruitCounter levelFruitCounter) {
        Level.levelFruitCounter = levelFruitCounter;
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
        getLevelHighscore().resetCounter();

        Log.d(TAG, "cleanUpLevelProperties: Clean up all level properties.");
    }

    //GETTER/SETTER +++++++++++++++++++++++++++++++++++++++++++++++++

    public String getLevelName(){
        return getActivity().getResources().getString(getLevelNameResId());
    }

    public ArrayList<Background> getAllBackgroundLayers() {
        if (allBackgroundLayers == null || allBackgroundLayers.size() <= 0) {
            this.determineBackgroundLayers();
            for (Background background : this.getAllBackgroundLayers()) {background.initialize();}
        }
        return allBackgroundLayers;
    }

    public void setAllBackgroundLayers(ArrayList<Background> allBackgroundLayers) {
        this.allBackgroundLayers = allBackgroundLayers;
    }

    public int getLevelNameResId() {
        return levelNameResId;
    }
    public void setLevelNameResId(@StringRes int levelNameResId) {
        this.levelNameResId = levelNameResId;
    }
    public ArrayList<Enemy> getAllEnemies() {
        if (allEnemies == null || allEnemies.size() <= 0) {
            this.determineAllEnemies();
            for (Enemy enemy : this.getAllEnemies()) {enemy.initialize();}
        }
        return allEnemies;
    }
    public void setAllEnemies(ArrayList<Enemy> allEnemies) {
        this.allEnemies = allEnemies;
    }
    public Player getPlayer() {
        if (player == null) {
            this.determinePlayer();
            this.getPlayer().initialize();
        }
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public ArrayList<Fruit> getAllFruits() {
        if (allFruits == null || allFruits.size() <= 0) {
            this.determineAllFruits();
            for (Fruit fruit : this.getAllFruits()) {fruit.initialize();}
        }
        return allFruits;
    }
    public void setAllFruits(ArrayList<Fruit> allFruits) {
        this.allFruits = allFruits;
    }
    public static Observer_HighScore getLevelHighscore() {
        return levelHighScore;
    }
    public static void setLevelHighScore(Observer_HighScore levelHighScore) {
        Level.levelHighScore = levelHighScore;
    }

    public ArrayList<LevelAssignment> getAllLevelAssignments() {
        if (allLevelAssignments == null || allLevelAssignments.size() <= 0) {
            this.determineLevelAssigments();
        }
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

    public Point getWorldMapPosition() {
        return worldMapPosition;
    }

    public void setWorldMapPosition(Point worldMapPosition) {
        this.worldMapPosition = worldMapPosition;
    }


}
