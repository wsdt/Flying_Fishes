package yourowngame.com.yourowngame.classes.gamelevels;


import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.actors.Enemy;
import yourowngame.com.yourowngame.classes.background.Background;

public abstract class Level { //which level an object is (1, 5, etc.) should be decided by LevelManager [so more flexible to changes]
    private static final String TAG = "Level";
    private String levelName; //Level name (maybe to show to user [e.g. Die dunkle Gruft, usw.]
    private ArrayList<Background> allBackgroundLayers = new ArrayList<>(); //Background layers for each level (as Arraylist to avoid NullpointerExceptions, so we just do not allow gaps)
    private ArrayList<Enemy> allEnemies = new ArrayList<>(); //MUST NOT BE STATIC (different levels, different enemies), All enemies on screen (will be spawned again if gone) for specific level
    //TODO: other level-dependent members/values

    public Level() {determineBackgroundLayers();determineAllEnemies();}

    /** Level realizations should have a fallback/default background
     * This method should calculate or simply just create the allBackgroundLayers-SparseArray
     * and call setAllBackgroundLayers() with it. */
    protected abstract void determineBackgroundLayers();
    protected abstract void determineAllEnemies();

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
}
