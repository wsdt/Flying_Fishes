package yourowngame.com.yourowngame.classes.background;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.classes.DrawableObj;

/**
 * Class for creating Backgrounds, only data!
 */

public abstract class Background extends DrawableObj {
    private static final String TAG = "Background";

    public Background(@NonNull Activity activity) {
        super(activity);
    }
}
