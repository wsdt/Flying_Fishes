package yourowngame.com.yourowngame.classes.actors.fruits;

import android.app.Activity;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.Level;

/**
 * Used for generating or/and managing fruits.
 */

//TODO: Also add updateAll() and drawAll() from LevelObj out!
public class FruitMgr {
    private static final String TAG = "FruitMgr";

    private FruitMgr() {
    } //no instance allowed

    /**
     * Creates universally random fruits."
     *
     * @param currLevel:  Used e.g. by some fruitpowers to take effect on the current level.
     * @param fruitClass: Used to determine which fruit to generate.
     */
    public static <F extends Fruit> ArrayList<F> createRandomFruits(@NonNull Activity activity, @NonNull Level currLevel, @NonNull Class<F> fruitClass, @IntRange(from = 1) int numberOfFruits) {
        ArrayList<F> craftedFruits = new ArrayList<>();
        for (int i = 0; i < numberOfFruits; i++) {
            F f = createRandomFruit(activity, currLevel, fruitClass);
            if (f == null) {
                Log.e(TAG, "createRandomFruits: Could not create fruits! Returned null.");
                return null; //abort when null for performance
            }
            craftedFruits.add(f);
        }
        return craftedFruits;
    }

    /**
     * Convenience method so we don't get a list for one fruit
     */
    public static <F extends Fruit> F createRandomFruit(@NonNull Activity activity, @NonNull Level currLevel, @NonNull Class<F> fruitClass) {
        try {
            return fruitClass.getConstructor(Activity.class, Level.class).newInstance(activity, currLevel); //use default constructor
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
