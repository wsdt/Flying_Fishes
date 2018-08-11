package yourowngame.com.yourowngame.classes.game_modes.mode_adventure.worlds;

import android.graphics.Point;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.layers.BL_FlyingElements;
import yourowngame.com.yourowngame.classes.background.layers.BL_SingleColor;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.Level;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.World;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.levels.Level_DarkDescent;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.levels.Level_EndlessDawn;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.levels.Level_FruityIsland;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.levels.Level_LucifersGameRoom;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.levels.Level_NightRider;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.levels.Level_SummerSky;

public class World_Earth extends World {
    private static final String TAG = "World_Earth";


    /** Initializing constructor. */
    public World_Earth(@NonNull DrawableSurfaceActivity activity) {
        super(activity);

    }

    @Override
    protected void determineAllLevels() {
        LinkedHashMap<Class,Point> levelList = new LinkedHashMap<>();

        levelList.put(Level_SummerSky.class, new Point(50,50));
        levelList.put(Level_NightRider.class, new Point(400,50));
        levelList.put(Level_EndlessDawn.class, new Point(1200,300));
        levelList.put(Level_DarkDescent.class, new Point(1200,500));
        levelList.put(Level_FruityIsland.class, new Point(1500, 800));
        levelList.put(Level_LucifersGameRoom.class, new Point(1000,850));
        this.setAllLevels(levelList);
    }

    @Override
    protected void determineBackgroundLayers() {
        ArrayList<Background> backgrounds = new ArrayList<>(); //override old

        backgrounds.add(new BL_SingleColor(this.getActivity(), R.color.colorSkyBlue));
        backgrounds.add(new BL_FlyingElements(this.getActivity(), new int[]{R.drawable.enemy_rocketfish_1}, 3));
        this.setAllBackgroundLayers(backgrounds);

    }

    @Override
    protected void determineMetaData() {
        this.setWorldNameResId(R.string.world_worldName_earth);
    }
}
