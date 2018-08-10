package yourowngame.com.yourowngame.classes.game_modes.mode_adventure;

import android.graphics.Point;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.annotation.StringRes;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.exceptions.WrongConfigured_Exception;

/**
 * Contains several levels which then are displayed in a LevelHierarchy.
 * No cleanUp() and no initialize() necessary bc. we this class has no
 * influence on gameLogic!
 */
public abstract class World {
    private static final String TAG = "World";

    private DrawableSurfaceActivity activity;
    /**
     * World name (maybe to show to user [e.g. Die dunkle Gruft, usw.] als Strings.xml res id for multilinguality!
     */
    private int worldNameResId;
    /**
     * Which icon is used to represent the level on the map? These icons are placed according to
     * set pointObj in alllevels-Map. By default a default one is used, but you can change this Icon
     * in each World individually, by just using the Setter in the inheritated/default constructor.
     */
    private int levelRepresentativeResId = R.drawable.world_1_lvl_representant;
    /**
     * Background layers for each world. These layers are determined by each subclass in the determineBgLayers().
     */
    private ArrayList<Background> allBackgroundLayers;
    /**
     * All levels of this world, with a PointObj (not for identification, but for positioning on the
     * levelScreen). Levels are arranged according on which position they are in the map itself.
     */
    private ArrayList<Level> allLevels;


    /**
     * Initializing constructor.
     */
    public World(@NonNull DrawableSurfaceActivity activity) {
        // Must be the first assignment
        this.setActivity(activity);
        this.determineMetaData();
        this.determineAllLevels();
        this.determineBackgroundLayers();

        /* Evaluate constraints (e.g. at minimum one lvl defined.
         * Check should be last method! */
        try {
            this.developerConfigurationCheck();
        } catch (WrongConfigured_Exception e) {
            Log.e(TAG, "World: World has been configured badly!");
            e.printStackTrace();
        }
    }

    private void developerConfigurationCheck() throws WrongConfigured_Exception {
        // CONSTRAINT 1 - At minimum one lvl is configured.
        if (this.getAllLevels() == null || this.getAllLevels().size() <= 0) {
            throw new WrongConfigured_Exception("World has no levels configured! You have to define at least one lvl for each worldObj!");
        }
    }

    /**
     * Setting all levels which should be displayed on the custom LevelHierarchy.
     */
    @Enhance(message = "Maybe we can get rid of this function, by only using the static getMetaData()-methods" +
            "of the levelObjs itself, and then by clicking on one we initialize ONE level.")
    protected abstract void determineAllLevels();

    /**
     * Here backgroundlayers of game itself could be used (or just the a animated bg picture or similar)
     * This has no effect on levels, it's just the background of the levelHierarchy
     */
    protected abstract void determineBackgroundLayers();

    /**
     * Defines default data (normally this method does not contain any logic* operations). E.g. setting the levelName by getting it from the strings.xml
     */
    @Enhance(message = "Make static, so we can access it without loading the whole level (e.g. for levelHierarchies etc.)")
    protected abstract void determineMetaData();

    // GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public DrawableSurfaceActivity getActivity() {
        return activity;
    }

    public void setActivity(@NonNull DrawableSurfaceActivity activity) {
        this.activity = activity;
    }

    public int getWorldNameResId() {
        return worldNameResId;
    }

    public void setWorldNameResId(@StringRes int worldNameResId) {
        this.worldNameResId = worldNameResId;
    }

    public ArrayList<Background> getAllBackgroundLayers() {
        if (allBackgroundLayers == null) {
            allBackgroundLayers = new ArrayList<>();
        }
        return allBackgroundLayers;
    }

    public void setAllBackgroundLayers(@NonNull @Size(min = 1) ArrayList<Background> allBackgroundLayers) {
        this.allBackgroundLayers = allBackgroundLayers;
    }

    public ArrayList<Level> getAllLevels() {
        if (allLevels == null) {
            allLevels = new ArrayList<>();
        }
        return allLevels;
    }

    public void setAllLevels(@NonNull @Size(min = 1) ArrayList<Level> allLevels) {
        this.allLevels = allLevels;
    }

    public int getLevelRepresentativeResId() {
        return levelRepresentativeResId;
    }

    public void setLevelRepresentativeResId(@DrawableRes int levelRepresentativeResId) {
        this.levelRepresentativeResId = levelRepresentativeResId;
    }
}
