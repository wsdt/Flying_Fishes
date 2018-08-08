package yourowngame.com.yourowngame.classes.background;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.classes.DrawableObj;

/**
 * Created on 18.02.2018.
 * <p>
 * Class for creating Backgrounds, only data!
 */

public abstract class Background extends DrawableObj {
    private static final String TAG = "Background";

    public Background(@NonNull Activity activity) {
        super(activity);
        Log.d(TAG, "getBackgroundInstance: Created new instance.");
    }
}
