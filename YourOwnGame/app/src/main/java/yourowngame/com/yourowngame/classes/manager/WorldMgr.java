package yourowngame.com.yourowngame.classes.manager;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.game_modes.DrawableLevel;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.Level;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.World;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.worlds.World_Earth;

/**
 * SINGLETON PATTERN
 */
public class WorldMgr {
    private static final String TAG = "WorldManager";
    private static int curr_world_index = 0;
    /**
     * Index only within world specific arrayList (NOT FOR ALL Levels cross-world)
     */
    private static int curr_lvl_index = 0;

    //TODO: Maybe replace arraylists of world/levels with Lvl1.class lists and initialize them on start! (but need to keep one as a member)

    /**
     * Which worlds are in which order.
     */
    private static ArrayList<World> worlds = new ArrayList<>();

    /**
     * IMPORTANT: Do not give activity by constructor (to avoid saving old activities etc.),
     * bc. we use a Singleton (to avoid memory leaks)
     */
    private WorldMgr() {
    } //no instance allowed


    /**
     * Only worldActivity should have access.
     */
    private static void createDefaultWorldOrder(@NonNull DrawableSurfaceActivity activity) {
        ArrayList<World> allWorlds = new ArrayList<>();
        allWorlds.add(new World_Earth(activity));
        setWorlds(allWorlds);
    }

    /**
     * Dummy/Redundant method for resetting game.
     */
    public static void resetGame(@NonNull DrawableLevel level) {
        level.cleanUp();
    }

    /**
     * Returns next lvl (if in the same world just next index, otherwise
     * next world the first index. If last lvl this method returns the first level! (good?)
     * <p>
     * Method might return null in error case!
     * <p>
     * World.class has a constraint, that each specialization needs at least one LvlObj otherwise
     * we get a WrongConfigured-Exception. So, we don't evaluate of the levelList.size().
     */
    public static boolean setNextLvl(@NonNull GameViewActivity activity) {
        if (getCurrWorldObj(activity).getAllLevels().size() <= (getCurr_lvl_index() + 1)) {
            // last lvl in world achieved
            if (getWorlds(activity).size() <= (getCurr_world_index() + 1)) {
                // last lvl in last world achieved --> No lvl available (just return false)
                return false;
            } else {
                // not last world, but last lvl in prior world
                setCurr_world_index(getCurr_world_index() + 1);
                setCurr_lvl_index(0);
            }
        } else {
            // not last lvl in world, just return incremented lvlIndex
            setCurr_lvl_index(getCurr_lvl_index() + 1);
        }
        return true; //next lvl available
    }

    public static Level getCurrLvlObj(@NonNull DrawableSurfaceActivity activity) {
        return getCurrWorldObj(activity).getAllLevels().get(WorldMgr.getCurr_lvl_index());
    }

    public static World getCurrWorldObj(@NonNull DrawableSurfaceActivity activity) {
        return WorldMgr.getWorlds(activity).get(WorldMgr.getCurr_world_index());
    }


    // SETTER / GETTER +++++++++++++++++++++++++++++++++++++++++
    public static ArrayList<World> getWorlds(@NonNull DrawableSurfaceActivity worldActivity) {
        if (worlds == null || worlds.size() <= 0) {
            createDefaultWorldOrder(worldActivity);
        }
        return worlds;
    }

    public static void setWorlds(ArrayList<World> worlds) {
        WorldMgr.worlds = worlds;
    }

    public static int getCurr_world_index() {
        return curr_world_index;
    }

    public static void setCurr_world_index(int curr_world_index) {
        WorldMgr.curr_world_index = curr_world_index;
    }

    public static int getCurr_lvl_index() {
        return curr_lvl_index;
    }

    public static void setCurr_lvl_index(int curr_lvl_index) {
        WorldMgr.curr_lvl_index = curr_lvl_index;
    }
}
