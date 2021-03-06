package yourowngame.com.yourowngame.classes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.WorkerThread;
import android.util.Log;

import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;

/**
 * Combines drawable, updateable and initializer interface into this class to provide
 * generic abstract methods incl. class variables.
 * <p>
 * E.g. used for Background and GameObject
 */
public abstract class DrawableObj {
    private static final String TAG = "DrawableObj";

    private Activity activity;
    private Canvas canvas;
    //TODO: Make loopcount static as ALL subclasses will have the same value
    private long loopCount;
    @Enhance (message = "Make static and adapt initialize() procedures. --> Better performance bc. only foreach specialization " +
            "executed once, instead for each instance as now!" +
            "BUT if we make this static, then we have to put it manually in all lowest subclasses bc. java resolves" +
            "static references otherwise for all. So if we initialize player our app thinks enemies are also initialized.",
        priority = Enhance.Priority.HIGH, byDeveloper = Constants.Developers.WSDT)
    //TODO: Evaluate isInitialized dynamically whether bitmapp array is filled or NOT!!!!!!!!!!!!
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
    @MainThread
    public abstract void draw();

    /**
     * Method is called to mutate position, etc. of object.
     */
    @WorkerThread
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
    //TODO: @CallSuper --> do work here which is the same for all subclasses (e.g. isInitialized)
    public abstract void initialize();

    /**
     * Removes all objects from ram (is called e.g. when game is exited or
     * an class with it's instances isn't needed anymore.
     *
     * @return hasSucceeded: returns false if error happened or true if everything went good.
     */
    public abstract boolean cleanup();


    /**
     * getCraftedDynamicBitmap:
     * <p>
     * Creates a dynamic bitmap from a drawable res
     *
     * @param resDrawable:        Drawable to craft (resource int)
     * @param rotationDegrees: how much should be image tilted or rotated? (in degrees) / if null then image won't be rotated
     * @param width:  reduce/enlarge width / if this param OR scaleHeight is null, both values get ignored! Use . as comma ;) --> Values MUST be higher than 0
     * @param height: same as scaleWidth.
     */
    public static Bitmap getCraftedDynamicBitmap(@NonNull Activity activity, @DrawableRes int resDrawable, @Nullable Integer rotationDegrees, @Nullable Integer width, @Nullable Integer height) {

        Log.d(TAG, "getCraftedBitmaps: Trying to craft bitmap.");

        //not else (because despite normal if method should continue)
        Bitmap targetImg = BitmapFactory.decodeResource(activity.getResources(), resDrawable);
        if ((width != null && height != null) && (width > 0 && height > 0)) { //must be before rotationDegrees-If
            //also don't scale if 0!
            targetImg = Bitmap.createScaledBitmap(targetImg, width, height, true);
        } //not else if!
        if (rotationDegrees != null) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotationDegrees);
            targetImg = Bitmap.createBitmap(targetImg, 0, 0, targetImg.getWidth(), targetImg.getHeight(), matrix, true);
        } //not else if (we want to make several combinations)

        return targetImg;
    }

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

    public void setStrResName(@StringRes int strResName) {
        this.strResName = strResName;
    }
}
