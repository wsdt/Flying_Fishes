package yourowngame.com.yourowngame.classes.manager;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.game_modes.DrawableLevel;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.Level;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.World;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.worlds.World_Earth;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;
import yourowngame.com.yourowngame.gameEngine.surfaces.GameView;

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
    private static ArrayList<Class> worlds = new ArrayList<>();

    /** Current world (curr level in DrawableSurfaces as it's the one who owns it). */
    private static World world;
    /** Only use when no access to gameView.obj*/
    private static DrawableLevel level;
    /**
     * IMPORTANT: Do not give activity by constructor (to avoid saving old activities etc.),
     * bc. we use a Singleton (to avoid memory leaks)
     */
    private WorldMgr() {
    } //no instance allowed


    /**
     * Only worldActivity should have access.
     */
    private static void createDefaultWorldOrder() {
        ArrayList<Class> allWorlds = new ArrayList<>();
        allWorlds.add(World_Earth.class);
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
        if (getCurrWorld(activity,false).getAllLevels().size() <= (getCurr_lvl_index() + 1)) {
            // last lvl in world achieved
            if (getWorlds().size() <= (getCurr_world_index() + 1)) {
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


    // SETTER / GETTER +++++++++++++++++++++++++++++++++++++++++
    public static ArrayList<Class> getWorlds() {
        if (worlds == null || worlds.size() <= 0) {
            createDefaultWorldOrder();
        }
        return worlds;
    }

    public static void setWorlds(ArrayList<Class> worlds) {
        WorldMgr.worlds = worlds;
    }

    public static int getCurr_world_index() {
        return curr_world_index;
    }

    public static void setCurr_world_index(int curr_world_index) {
        if (curr_world_index >= WorldMgr.getWorlds().size()) {
            Log.e(TAG, "setCurr_world_index: Have not set curr world index as that world has not been set.");
        } else {
            WorldMgr.curr_world_index = curr_world_index;
        }
    }

    public static int getCurr_lvl_index() {
        return curr_lvl_index;
    }

    public static void setCurr_lvl_index(int curr_lvl_index) {
        //TODO: Add size validation as in currWorldindex setter
        WorldMgr.curr_lvl_index = curr_lvl_index;
    }

    /** @param force: Forces reload of World (necessary when changing the index of currWorld) */
    //TODO: As we currently only have 1 world I didn't implement nextWorld logic (see level)
    public static World getCurrWorld(@NonNull DrawableSurfaceActivity drawableSurfaceActivity, boolean force) {
        if (WorldMgr.world == null || force) {
            try {
                WorldMgr.world = ((Class<World>) getWorlds().get(getCurr_world_index())).getConstructor(DrawableSurfaceActivity.class).newInstance(drawableSurfaceActivity);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return world;
    }

    public static Map.Entry<Class, Point> getCurrLvlEntry(@NonNull DrawableSurfaceActivity drawableSurfaceActivity, boolean force) {
        Map.Entry<Class, Point> c = ((Map.Entry<Class, Point>) WorldMgr.getCurrWorld(drawableSurfaceActivity,force).getAllLevels().entrySet().toArray()[getCurr_lvl_index()]);
        Log.d(TAG, "getCurrLvlEntry: Loaded lvl entry ->"+c.getKey()+" >> "+getCurr_lvl_index());
        return c;
    }

    /** Should be ONLY used when you have NO ACCESS to GameView instance (which also contains the current lvl obj).
     * @param force: force reload = true*/
    public static DrawableLevel getCurrLvl(@NonNull DrawableSurfaceActivity drawableSurfaceActivity, boolean force) {
         if (WorldMgr.level == null || force) {
             try {
                 WorldMgr.level = ((Class<DrawableLevel>) getCurrLvlEntry(drawableSurfaceActivity, force).getKey()).getConstructor(DrawableSurfaceActivity.class).newInstance(drawableSurfaceActivity);
                 Log.d(TAG, "getCurrLvl: Crafted lvl new.");
             } catch (InstantiationException e) {
                 e.printStackTrace();
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             } catch (InvocationTargetException e) {
                 e.printStackTrace();
             } catch (NoSuchMethodException e) {
                 e.printStackTrace();
             }
         }
         return WorldMgr.level;
    }
}
