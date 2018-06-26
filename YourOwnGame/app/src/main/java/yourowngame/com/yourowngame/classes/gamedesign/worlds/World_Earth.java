package yourowngame.com.yourowngame.classes.gamedesign.worlds;

import android.app.Activity;
import android.graphics.Point;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.background.layers.BL_SingleColor;
import yourowngame.com.yourowngame.classes.gamedesign.World;
import yourowngame.com.yourowngame.classes.gamedesign.levels.Level_SummerSky;

public class World_Earth extends World {
    /** Initializing constructor. */
    public World_Earth(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    protected void determineAllLevels() {
        this.getAllLevels().put(new Point(50,50),new Level_SummerSky(this.getActivity()));
        this.getAllLevels().put(new Point(100,50),new Level_SummerSky(this.getActivity()));
        this.getAllLevels().put(new Point(300,50),new Level_SummerSky(this.getActivity()));
        this.getAllLevels().put(new Point(250,150),new Level_SummerSky(this.getActivity()));
    }

    @Override
    protected void determineBackgroundLayers() {
        this.getAllBackgroundLayers().add(new BL_SingleColor(this.getActivity(),R.color.colorSkyBlue));
    }

    @Override
    protected void determineMetaData() {
        this.setWorldNameResId(R.string.world_worldName_earth);
    }
}
