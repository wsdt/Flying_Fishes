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
    @Enhance(message = "We have to dispose of the List of Worlds and also the LevelLlist in the" +
            "LevelMgr, to avoid allocating too much memory. We need a light access to Metadata (e.g. by" +
            "making the method static) and we should always ONLY instantiate ONE World and ONE Level" +
            "if needed.")
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



    // SETTER / GETTER +++++++++++++++++++++++++++++++++++++++++
    public static ArrayList<World> getWorlds() {
        return worlds;
    }

    public static void setWorlds(ArrayList<World> worlds) {
        WorldManager.worlds = worlds;
    }
}
