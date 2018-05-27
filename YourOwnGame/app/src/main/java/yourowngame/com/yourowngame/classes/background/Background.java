package yourowngame.com.yourowngame.classes.background;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.classes.DrawableObj;
import yourowngame.com.yourowngame.classes.background.interfaces.IBackground;

/**
 * Created on 18.02.2018.
 * <p>
 * Class for creating Backgrounds, only data!
 */

public abstract class Background extends DrawableObj implements IBackground {
    private static final String TAG = "Background";

    private String name;

    public Background(@NonNull Activity activity, String name) {
        super(activity);
        Log.d(TAG, "getBackgroundInstance: Created new instance.");
        this.setName(name);
    }


    //GETTER/SETTER ------------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
