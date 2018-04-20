package yourowngame.com.yourowngame.gameEngine.interfaces;

import android.support.annotation.Nullable;

public interface Initializer {
    /** Used in initiliaze() and cleanup() [to set uniform values] --> Objects just use NULL */
    int PRIMITIVES_ILLEGAL_VALUE = (-1);

    /** Used to allocate all necessary objects and some other heavyweight operations,
     * which only need to be done once.
     *
     * To make it easier for us I used a generic with unlimited params. Unfortunately
     * if we implement this interface Java changes the signature to OBJ[] which would
     * force us to provide obj with the same type. But we want to send different objects
     * and also how much we want (I know, this is against the principle of datatypes,
     * but here it might be really useful). So if we implement a interface, just ensure
     * that you change OBJ[] allObjs back to OBJ... allObjs :)
     *
     * Also important for this method: Keep errors as locally as possible and always keep
     * track of provided objects (because of generics and indexoutofbounds etc.) so I recommend
     * to provide parameters in a comment above the method: e.g. OBJ[0]: Activity
     *
     * Although it might make sense in some cases, this method should/must NOT be static
     * in any case!
     *
     * The method should look like this:
     * [at]Override [at]SafeVarargs
        public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
            try {
                 if (allObjs != null) {
                    //TODO: use here your objects and do everything
                 } else {return false;}
            } catch (ClassCastException | NullPointerException e) {
                Log.e(TAG, "initialize: Could not initialize Backgroundlayer for clouds.");
                e.printStackTrace();
                return false;
            }
        return true;
        }
     *
     * @param allObjs: Used to provide necessary objects for operations.
     * @return hasSucceeded: returns false if error happened or true if everything went good. */
    <OBJ> boolean  initialize(@Nullable OBJ... allObjs);

    /** Removes all objects from ram (is called e.g. when game is exited or
     * an class with it's instances isn't needed anymore.
     *
     * @return hasSucceeded: returns false if error happened or true if everything went good.*/
    boolean cleanup();
}
