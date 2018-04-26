package yourowngame.com.yourowngame.classes.storagemgr;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import yourowngame.com.yourowngame.classes.handler.RandomHandler;

import static yourowngame.com.yourowngame.classes.handler.interfaces.ISharedPrefStorageMgr.*;

/** Currently very easy storage mgr, which is only saving points for current user.
 * So no username, no date etc. is saved, because we will have to delete this file in future when replacing it with sql. */
public class SharedPrefStorageMgr {
    private SharedPreferences sharedPreferences;
    private static final String TAG = "SharedPrefStorMgr";

    public SharedPrefStorageMgr(@NonNull Context context) {
        this.setSharedPreferences(context.getSharedPreferences(SP_HIGHSCORE,Context.MODE_PRIVATE));
    }

    public List<Integer> getSortedHighscoreEntries() {
        List highscoreList;
        try {
            highscoreList = new ArrayList(this.getSharedPreferences().getAll().values());
            Collections.sort(highscoreList);
        } catch (ClassCastException e) {
            e.printStackTrace();
            highscoreList = new ArrayList<>();
            Log.e(TAG, "getSortedHighscoreEntries: Could not sort HighscoreList. Returned empty one.");
        }
         return highscoreList;
    }

    public void saveNewHighscoreEntry(int points) {
        //random for no conflicts by saving (bad technique i know, but we delete this class in future so I am lazy)
        this.getSharedPreferences().edit().putInt(SP_ENTRY_PREFIX+"_"+ RandomHandler.getRandomInt(0, 999999999),points).apply();
    }

    public void clearHighscore() {
        this.getSharedPreferences().edit().clear().apply(); //clear all highscore entries
    }

    //GETTER / SETTER --------------------------------
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
}
