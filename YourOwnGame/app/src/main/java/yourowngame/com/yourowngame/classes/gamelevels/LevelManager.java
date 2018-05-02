package yourowngame.com.yourowngame.classes.gamelevels;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.background.BackgroundManager;
import yourowngame.com.yourowngame.classes.gamelevels.levels.Level_HarmlessSky;
import yourowngame.com.yourowngame.classes.gamelevels.levels.Level_LostPlace;
import yourowngame.com.yourowngame.classes.gamelevels.levels.Level_UnknownLand;

/**
 * Pattern: SINGLETON
 * Why using SparseArray over Hashmap or ArrayList?
 * Sparsearray needs less memory than Hashmap and also has the ability to set elements into the list with gaps (arraylist can't do that).
 * Only drawback of sparseArray: The get() method is mostly slower, because of the searching algorithm behind it and as well it isn't
 * mapped like the Hashmap. But if we search for a level with .get(1) [NEVER USE GET, ALWAYS USE .valueAt() = FASTER] and save it to a temp. Level-Obj this drawback is not that dramatic.
 * --> BUT be careful when iterating over SparseArray, if we assign from 1 upwards or have gaps, then we could get a null object, because there is NO foreach.
 */
public class LevelManager {
    private static int CURRENT_LEVEL = 0; //Global level variable so everybody knows which level now is (should be only adapted by LevelManager, so NO SETTER)
    private static final String TAG = "LevelManager";
    private static LevelManager INSTANCE;
    private static ArrayList<Level> levelList; //By changing this, we can have flexible level orders and also are able to iterate over levels (after this level the next one comes etc.)
    private static BackgroundManager backgroundManager; //levels might need the BackgroundManager or/and it's gameView

    private LevelManager(@NonNull BackgroundManager backgroundManager) {
        Log.d(TAG, "LevelMgr: Creating new instance of LevelMgr.");
        LevelManager.setBackgroundManager(backgroundManager);
        createDefaultLevelList(); //for now, just use the default level order, which is chosen by us
        INSTANCE = this;
    }

    public static LevelManager getInstance(@NonNull BackgroundManager backgroundManager) {
        return (INSTANCE != null) ? INSTANCE : new LevelManager(backgroundManager);
    }

    //Heart of levelMgr: static so more comfortable to call [do not forget the drawback of SparseArrays when calling this method! (although I used valueAt())]
    public Level getCurrentLevelObj() {
        if (getLevelList().size() > getCurrentLevel()) {
            return getLevelList().get(getCurrentLevel());
        }
        Log.w(TAG, "getCurrentLevelObj: Level is null! Currentlevel does not exist->" + getCurrentLevel());
        return null;
    }

    /**
     * This method is called when user achieved a level and is allowed to play the next one.
     * The new level will be returned.
     */
    public int levelAchieved() {
        Log.d(TAG, "levelAchieved: User achieved current level. Entered next level. Ensure that level exists (sparseArray) and change components (enemies, bg etc.)");
        return ++CURRENT_LEVEL; //pre-inkrement to return new current level
    }

    public void createDefaultLevelList() { //used for restarting game (add levels chronologically) --> faster than sparseArray
        setLevelList(new ArrayList<Level>()); //for restarting to avoid nullpointer and resetting levellist (here so we force this method to be called)
        getLevelList().add(new Level_HarmlessSky());
        getLevelList().add(new Level_LostPlace());
        /*getLevelList().put(new Level_HauntedForest());
        getLevelList().put(new Level_UnknownLand());
        getLevelList().put(new Level_DarkDescent());*/
        Log.d(TAG, "createDefaultLevelList: Have set the default level list.");
    }

    //GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static int getCurrentLevel() { //No setter, because level should be managed by LevelManager
        return CURRENT_LEVEL;
    }

    public ArrayList<Level> getLevelList() {
        if (levelList == null) {
            createDefaultLevelList();
        }
        return levelList;
    }

    /**
     * @param levelList: SparseArray makes it possible to set levels at the desired index, also when there might be gaps
     *                   (similar to Hashmaps, but here are SparseArrays more efficient)
     */
    public static void setLevelList(ArrayList<Level> levelList) {
        LevelManager.levelList = levelList;
    }

    public static BackgroundManager getBackgroundManager() {
        return backgroundManager;
    }

    public static void setBackgroundManager(BackgroundManager backgroundManager) {
        LevelManager.backgroundManager = backgroundManager;
    }
}
