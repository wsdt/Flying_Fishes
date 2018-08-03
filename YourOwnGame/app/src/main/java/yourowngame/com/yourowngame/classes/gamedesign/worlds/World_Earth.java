package yourowngame.com.yourowngame.classes.gamedesign.worlds;

import android.app.Activity;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.layers.BL_FlyingElements;
import yourowngame.com.yourowngame.classes.background.layers.BL_SingleColor;
import yourowngame.com.yourowngame.classes.gamedesign.Level;
import yourowngame.com.yourowngame.classes.gamedesign.World;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_DarkDescent;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_EndlessDawn;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_FruityIsland;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_NightRider;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_SummerSky;
import yourowngame.com.yourowngame.classes.manager.WorldMgr;

public class World_Earth extends World {
    private static final String TAG = "World_Earth";


    /** Initializing constructor. */
    public World_Earth(@NonNull Activity activity) {
        super(activity);

    }

    @Override
    protected void determineAllLevels() {
        ArrayList<Level> levelList = new ArrayList<>();

        levelList.add(new Level_SummerSky(this.getActivity(),new Point(50,50)));
        levelList.add(new Level_NightRider(this.getActivity(), new Point(400,50)));
        levelList.add(new Level_EndlessDawn(this.getActivity(), new Point(1200,300)));
        levelList.add(new Level_DarkDescent(this.getActivity(), new Point(1200,500)));
        levelList.add(new Level_FruityIsland(this.getActivity(), new Point(1500, 800)));
        this.setAllLevels(levelList);
    }

    @Override
    protected void determineBackgroundLayers() {
        ArrayList<Background> backgrounds = new ArrayList<>(); //override old

        backgrounds.add(new BL_SingleColor(this.getActivity(), R.color.colorSkyBlue));
        backgrounds.add(new BL_FlyingElements(this.getActivity(), new int[]{R.drawable.enemy_rocketfish_01}, 3));
        Log.d(TAG, "Added Background Objects " + backgrounds.size() + " to World_Earth");
        this.setAllBackgroundLayers(backgrounds);

    }

    @Override
    protected void determineMetaData() {
        this.setWorldNameResId(R.string.world_worldName_earth);
    }
}
