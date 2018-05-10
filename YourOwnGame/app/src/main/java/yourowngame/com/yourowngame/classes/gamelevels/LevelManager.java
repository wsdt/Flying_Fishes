package yourowngame.com.yourowngame.classes.gamelevels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.annotations.Bug;
import yourowngame.com.yourowngame.classes.gamelevels.levels.Level_DarkDescent;
import yourowngame.com.yourowngame.classes.gamelevels.levels.Level_EndlessDawn;
import yourowngame.com.yourowngame.classes.gamelevels.levels.Level_SummerSky;
import yourowngame.com.yourowngame.classes.gamelevels.levels.Level_NightRider;
import yourowngame.com.yourowngame.classes.manager.dialog.DialogMgr;

/**
 * Pattern: SINGLETON
 *
 * DO NOT MAKE ANY METHODS HERE STATIC (we have a Singleton, so no problem)! ALL PARAMS (IF POSSIBLE) SHOULD BE STATIC.
 * */
public class LevelManager {
    private Context context;

    private static int CURRENT_LEVEL = 0; //Global level variable so everybody knows which level now is (should be only adapted by LevelManager, so NO SETTER)
    private static final String TAG = "LevelManager";
    private static LevelManager INSTANCE;
    private static ArrayList<Level> levelList; //By changing this, we can have flexible level orders and also are able to iterate over levels (after this level the next one comes etc.)

    private LevelManager(@NonNull Context context) {
        Log.d(TAG, "LevelMgr: Creating new instance of LevelMgr.");
        this.setContext(context);

        createDefaultLevelList(); //for now, just use the default level order, which is chosen by us
        INSTANCE = this;
    }

    public static LevelManager getInstance(@NonNull Context context) {
        return (INSTANCE != null) ? INSTANCE : new LevelManager(context);
    }

    //Heart of levelMgr
    public Level getCurrentLevelObj() {
        if (getLevelList().size() > getCurrentLevel()) {
            return getLevelList().get(getCurrentLevel());
        }
        Log.w(TAG, "getCurrentLevelObj: Level is null! Currentlevel does not exist->" + getCurrentLevel());
        return null;
    }

    @Bug (problem = "First play works, but if we restart the game, the user achieves the level immediately on next validation whether" +
            "level assignments are achieved. So e.g. level 2 after restart also for only 50 points possible!")
    public void initiateLevelAchievedProcess(@NonNull DialogMgr dialogMgr) {
        Log.d(TAG, "initiateLevelAchievedProcess: Trying to change level.");


        //show dialog
        dialogMgr.showDialog(dialogMgr.createDialog_LevelAchieved());


        /** According to our current philosophy we do not make fluent level changes so following lines
         * are not necessary. Just show dialog and do the things we want to. */
        /*this.getCurrentLevelObj().cleanUpLevelProperties(); //remove everything from display

        //After this call the levelObj reference has been changed! So clean up the game field before (remove all enemies etc.)
        return levelAchieved();*/
    }

    private void createDefaultLevelList() { //used for restarting game (add levels chronologically) --> faster than sparseArray
        setLevelList(new ArrayList<Level>()); //for restarting to avoid nullpointer and resetting levellist (here so we force this method to be called)
        getLevelList().add(new Level_SummerSky(this.getContext()));
        getLevelList().add(new Level_NightRider(this.getContext()));
        getLevelList().add(new Level_EndlessDawn(this.getContext()));
        getLevelList().add(new Level_DarkDescent(this.getContext()));
        Log.d(TAG, "createDefaultLevelList: Have set the default level list.");
    }

    //GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public int getCurrentLevel() { //No setter, because level should be managed by LevelManager
        return CURRENT_LEVEL;
    }

    public int setCurrentLevel(int newLevel) {
        Log.d(TAG, "setCurrentLevel: Setting level");

        //Check if level++ exists,
        if ((newLevel) >= getLevelList().size()) { //is lvl indexoutofbonds?
            Log.w(TAG, "setCurrentLevel: Can't go into level. Returning already set level.");
            return CURRENT_LEVEL;
        } else {
            CURRENT_LEVEL = newLevel;
            return newLevel; //pre-inkrement to return new current level
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
