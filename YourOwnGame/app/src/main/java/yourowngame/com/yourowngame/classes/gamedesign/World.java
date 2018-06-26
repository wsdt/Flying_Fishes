package yourowngame.com.yourowngame.classes.gamedesign;

import android.app.Activity;
import android.graphics.Point;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.background.Background;

/** Contains several levels which then are displayed in a LevelHierarchy.
 * No cleanUp() and no initialize() necessary bc. we this class has no
 * influence on gameLogic! */
public abstract class World {
    private static final String TAG = "World";

    private Activity activity;
    /** World name (maybe to show to user [e.g. Die dunkle Gruft, usw.] als Strings.xml res id for multilinguality!*/
    private int worldNameResId;
    /** Which icon is used to represent the level on the map? These icons are placed according to
     * set pointObj in alllevels-Map. By default a default one is used, but you can change this Icon
     * in each World individually, by just using the Setter in the inheritated/default constructor. */
    private int levelRepresentativeResId = R.drawable.avoci; //TODO: Use a real icon
    /** Background layers for each world. These layers are determined by each subclass in the determineBgLayers(). */
    private ArrayList<Background> allBackgroundLayers = new ArrayList<>();
    /** All levels of this world, with a PointObj (not for identification, but for positioning on the
     * levelScreen). Levels are arranged according on which position they are in the map itself. */
    private HashMap<Point, Level> allLevels = new HashMap<>();


    /** Initializing constructor. */
    public World(@NonNull Activity activity) {
        // Must be the first assignment
        this.setActivity(activity);

        this.determineMetaData();
        this.determineAllLevels();
        this.determineBackgroundLayers();
    }

    /** Setting all levels which should be displayed on the custom LevelHierarchy. */
    @Enhance (message = "Maybe we can get rid of this function, by only using the static getMetaData()-methods" +
            "of the levelObjs itself, and then by clicking on one we initialize ONE level.")
    protected abstract void determineAllLevels();

    /** Here backgroundlayers of game itself could be used (or just the a animated bg picture or similar)
     * This has no effect on levels, it's just the background of the levelHierarchy*/
    protected abstract void determineBackgroundLayers();

    /** Defines default data (normally this method does not contain any logic* operations). E.g. setting the levelName by getting it from the strings.xml*/
    @Enhance(message = "Make static, so we can access it without loading the whole level (e.g. for levelHierarchies etc.)")
    protected abstract void determineMetaData();

    // GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getWorldNameResId() {
        return worldNameResId;
    }

    public void setWorldNameResId(int worldNameResId) {
        this.worldNameResId = worldNameResId;
    }

    public ArrayList<Background> getAllBackgroundLayers() {
        return allBackgroundLayers;
    }

    public void setAllBackgroundLayers(ArrayList<Background> allBackgroundLayers) {
        this.allBackgroundLayers = allBackgroundLayers;
    }

    public HashMap<Point, Level> getAllLevels() {
        return allLevels;
    }

    public void setAllLevels(HashMap<Point, Level> allLevels) {
        this.allLevels = allLevels;
    }

    public int getLevelRepresentativeResId() {
        return levelRepresentativeResId;
    }

    public void setLevelRepresentativeResId(int levelRepresentativeResId) {
        this.levelRepresentativeResId = levelRepresentativeResId;
    }
}
