package yourowngame.com.yourowngame.classes.gamelevels;


import android.util.SparseArray;

import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.gameEngine.GameView;

public abstract class Level { //which level an object is (1, 5, etc.) should be decided by LevelManager [so more flexible to changes]
    private static final String TAG = "Level";
    private String levelName; //Level name (maybe to show to user [e.g. Die dunkle Gruft, usw.]
    private ArrayList<Background> backgroundLayers = new ArrayList<>(); //Background layers for each level (as Arraylist to avoid NullpointerExceptions, so we just do not allow gaps)
    //TODO: other level-dependent members/values

    public Level() {determineBackgroundLayers();}

    /** Level realizations should have a fallback/default background
     * This method should calculate or simply just create the backgroundLayers-SparseArray
     * and call setBackgroundLayers() with it. */
    protected abstract void determineBackgroundLayers();

    //GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public ArrayList<Background> getBackgroundLayers() {
        return backgroundLayers;
    }

    public void setBackgroundLayers(ArrayList<Background> backgroundLayers) {
        this.backgroundLayers = backgroundLayers;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
