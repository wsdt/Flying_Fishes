package yourowngame.com.yourowngame.classes.gamelevels;


import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.manager.SoundMgr;

public abstract class Level { //which level an object is (1, 5, etc.) should be decided by LevelManager [so more flexible to changes]
    private static final String TAG = "Level";
    protected static SoundMgr soundMgr = new SoundMgr(); //static because always only one soundMgr instance
    private String levelName; //Level name (maybe to show to user [e.g. Die dunkle Gruft, usw.]
    private Player player;
    private ArrayList<Background> allBackgroundLayers = new ArrayList<>(); //Background layers for each level (as Arraylist to avoid NullpointerExceptions, so we just do not allow gaps)
    private ArrayList<Enemy> allEnemies = new ArrayList<>(); //MUST NOT BE STATIC (different levels, different enemies), All enemies on screen (will be spawned again if isGone) for specific level
    private ArrayList<Fruit> allFruits = new ArrayList<>();
    //TODO: other level-dependent members/values

    public Level() {
        Log.d(TAG, "Level: ###################### STARTING LOADING LEVEL ###############################");
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

    /** Level realizations should have a fallback/default background
     * This method should calculate or simply just create the allBackgroundLayers-SparseArray
     * and call setAllBackgroundLayers() with it.
     *
     * TODO: Ok good idea! I think the easiest fallback-procedure would be just adding fallback layers/enemies etc.
     * TODO: into default level object/members (e.g. instead of new ArrayList<>() --> new ArrayList<>({all Objs})*/
    protected abstract void determinePlayer();
    protected abstract void determineBackgroundLayers();
    protected abstract void determineAllEnemies();
    protected abstract void determineAllFruits();
    protected abstract void playBackgroundMusic();
    public abstract void cleanUpLevelProperties();

    /** Place in this method all to validating params like highscore etc. and return true if
     * conditions are met. So GameView knows it can increase the level.*/
    public abstract boolean areLevelAssignmentsAchieved();

    //GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public ArrayList<Background> getAllBackgroundLayers() {
        return allBackgroundLayers;
    }

    public void setAllBackgroundLayers(ArrayList<Background> allBackgroundLayers) {
        this.allBackgroundLayers = allBackgroundLayers;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
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
}
