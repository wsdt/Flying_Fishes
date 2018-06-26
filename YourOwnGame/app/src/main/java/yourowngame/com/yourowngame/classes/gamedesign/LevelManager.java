package yourowngame.com.yourowngame.classes.gamedesign;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_BonusLevel01;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_DarkDescent;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_EndlessDawn;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_SummerSky;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_NightRider;

/**
 * Pattern: SINGLETON
 *
 * DO NOT MAKE ANY METHODS HERE STATIC (we have a Singleton, so no problem)! ALL PARAMS (IF POSSIBLE) SHOULD BE STATIC.
 * */
public class LevelManager {
    private static final String TAG = "LevelManager";

    private Activity activity;
    private static int CURRENT_LEVEL = 0; //Global level variable so everybody knows which level now is (should be only adapted by LevelManager, so NO SETTER)
    private static ArrayList<Level> levelList; //By changing this, we can have flexible level orders and also are able to iterate over levels (after this level the next one comes etc.)

    /** Don't make this as Singleton or similar, bc. old activities get saved into instance
     * which is then obsolete. Additionally, this class nests an Activity-Obj., which should
     * never be static (instabil).*/
    public LevelManager(@NonNull Activity activity) {
        Log.d(TAG, "LevelMgr: Creating new instance of LevelMgr.");
        this.setActivity(activity);

        /* Do not create default levelList here (more performant to do it for each instance, when we
        need it explicitely */
    }

    //Heart of levelMgr
    public Level getCurrentLevelObj() {
        if (getLevelList().size() > getCurrentLevel()) {
            return getLevelList().get(getCurrentLevel());
        }
        Log.w(TAG, "getCurrentLevelObj: Level is null! Currentlevel does not exist->" + getCurrentLevel());
        return null;
    }

    private void createDefaultLevelList() { //used for restarting game (add levels chronologically) --> faster than sparseArray
        setLevelList(new ArrayList<Level>()); //for restarting to avoid nullpointer and resetting levellist (here so we force this method to be called)
        getLevelList().add(new Level_SummerSky(this.getActivity()));
        getLevelList().add(new Level_NightRider(this.getActivity()));
        getLevelList().add(new Level_EndlessDawn(this.getActivity()));
        getLevelList().add(new Level_DarkDescent(this.getActivity()));
        getLevelList().add(new Level_BonusLevel01(this.getActivity()));
        Log.d(TAG, "createDefaultLevelList: Have set the default level list.");
    }

    //GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public int getCurrentLevel() { //No setter, because level should be managed by LevelManager
        return CURRENT_LEVEL;
    }

    public void setCurrentLevel(int newLevel) {
        Log.d(TAG, "setCurrentLevel: Setting level");

        //Check if level++ exists,
        if ((newLevel) >= getLevelList().size()) { //is lvl indexoutofbonds?
            Log.w(TAG, "setCurrentLevel: Can't go into level. Returning already set level.");
        } else {
            CURRENT_LEVEL = newLevel;
        }
    }

    /** Used for restarting game so user starts again with lvl 1.*/
    public void resetGame() {
        getCurrentLevelObj().cleanUpLevelProperties();
        CURRENT_LEVEL = 0;
    }

    public ArrayList<Level> getLevelList() {
        if (levelList == null) {
            createDefaultLevelList();
        }
        return levelList;
    }

    public void setLevelList(ArrayList<Level> levelList) {
        LevelManager.levelList = levelList;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
