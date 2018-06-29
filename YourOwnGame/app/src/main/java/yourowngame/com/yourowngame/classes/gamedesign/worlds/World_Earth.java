package yourowngame.com.yourowngame.classes.gamedesign.worlds;

import android.app.Activity;
import android.graphics.Point;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.layers.BL_SingleColor;
import yourowngame.com.yourowngame.classes.gamedesign.Level;
import yourowngame.com.yourowngame.classes.gamedesign.World;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_DarkDescent;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_EndlessDawn;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_NightRider;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_SummerSky;

public class World_Earth extends World {
    /** Initializing constructor. */
    public World_Earth(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    protected void determineAllLevels() {
        HashMap<Point, Level> levelMap = new HashMap<>();
        levelMap.put(new Point(50,50),new Level_SummerSky(this.getActivity()));
        levelMap.put(new Point(400,50),new Level_NightRider(this.getActivity()));
        levelMap.put(new Point(1200,300),new Level_EndlessDawn(this.getActivity()));
        levelMap.put(new Point(1200,500),new Level_DarkDescent(this.getActivity()));
        this.setAllLevels(levelMap);
    }

    @Override
    protected void determineBackgroundLayers() {
        ArrayList<Background> backgrounds = new ArrayList<>();
        backgrounds.add(new BL_SingleColor(this.getActivity(),R.color.colorSkyBlue));
        this.setAllBackgroundLayers(backgrounds);
    }

    @Override
    protected void determineMetaData() {
        this.setWorldNameResId(R.string.world_worldName_earth);
    }
}
