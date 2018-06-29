package yourowngame.com.yourowngame.classes.gamedesign;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import yourowngame.com.yourowngame.activities.WorldActivity;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.gamedesign.worlds.World_Earth;

/** SINGLETON PATTERN */
public class WorldManager {
    private static final String TAG = "WorldManager";
    /** Which worlds are in which order. */
    private static ArrayList<World> worlds = new ArrayList<>();

    /** IMPORTANT: Do not give activity by constructor (to avoid saving old activities etc.)*/
    private static WorldManager INSTANCE;
    private WorldManager() {}
    public static WorldManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WorldManager();
        }
        return INSTANCE;
    }

    /** Only worldActivity should have access. */
    private static void createDefaultWorldOrder(@NonNull WorldActivity activity) {
        setWorlds(new ArrayList<World>());
        getWorlds().add(new World_Earth(activity));
    }

    /** Dummy/Redundant method for resetting game. */
    public static void resetGame(@NonNull Level level) {
        level.cleanUpLevelProperties();
    }



    // SETTER / GETTER +++++++++++++++++++++++++++++++++++++++++
    public static ArrayList<World> getWorlds() {
        return worlds;
    }

    public static void setWorlds(ArrayList<World> worlds) {
        WorldManager.worlds = worlds;
    }
}
