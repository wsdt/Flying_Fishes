package yourowngame.com.yourowngame.classes;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;

/**
 * Combines drawable, updateable and initializer interface into this class to provide
 * generic abstract methods incl. class variables.
 * <p>
 * E.g. used for Background and GameObject
 */
public abstract class DrawableObj {
    private Activity activity;
    private Canvas canvas;
    private long loopCount;
    private boolean isInitialized = false; //should be only set to true in initialize() --> no getter setter because only class itself should have access
    /**
     * String resource id (multilingual) for setting a to user visible name of the drawable obj.
     * Can be not set, so name is not obligatory.
     */
    private int strResName;

    public DrawableObj(@NonNull Activity activity) {
        this.setActivity(activity);
    }

    /**
     * Method is called to draw elem to canvas.
     * This method CAN actually throw the NoDrawableInArrayFound_Exception!
     */
    public abstract void draw() throws NoDrawableInArrayFound_Exception;

    /**
     * Method is called to mutate position, etc. of object.
     */
    public abstract void update();

    /**
     * Used to allocate all necessary objects and some other heavyweight operations,
     * which only need to be done once.
     * <p>
     * To make it easier for us I used a generic with unlimited params. Unfortunately
     * if we implement this interface Java changes the signature to OBJ[] which would
     * force us to provide obj with the same type. But we want to send different objects
     * and also how much we want (I know, this is against the principle of datatypes,
     * but here it might be really useful). So if we implement a interface, just ensure
     * that you change OBJ[] allObjs back to OBJ... allObjs :)
     * <p>
     * Also important for this method: Keep errors as locally as possible and always keep
     * track of provided objects (because of generics and indexoutofbounds etc.) so I recommend
     * to provide parameters in a comment above the method: e.g. OBJ[0]: Activity
     * <p>
     * Although it might make sense in some cases, this method should/must NOT be static
     * in any case!
     * <p>
     * The method should look like this:
     * [at]Override [at]SafeVarargs
     * public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
     * try {
     * if (allObjs != null) {
     * //TODO: use here your objects and do everything
     * } else {return false;}
     * } catch (ClassCastException | NullPointerException e) {
     * Log.e(TAG, "initialize: Could not initialize Backgroundlayer for clouds.");
     * e.printStackTrace();
     * return false;
     * }
     * return true;
     * }
     */
    public abstract void initialize();

    /**
     * Removes all objects from ram (is called e.g. when game is exited or
     * an class with it's instances isn't needed anymore.
     *
     * @return hasSucceeded: returns false if error happened or true if everything went good.
     */
    public abstract boolean cleanup();


    //GETTER/SETTER ----------------------------------------------------------------
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public long getLoopCount() {
        return loopCount;
    }

    public void setLoopCount(long loopCount) {
        this.loopCount = loopCount;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    public int getStrResName() {
        return strResName;
    }

    public void setStrResName(int strResName) {
        this.strResName = strResName;
    }
}
