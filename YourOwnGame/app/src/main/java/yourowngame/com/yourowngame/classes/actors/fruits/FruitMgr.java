package yourowngame.com.yourowngame.classes.actors.fruits;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Meloon;
import yourowngame.com.yourowngame.classes.annotations.Bug;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

/** Used for generating or/and managing fruits.*/

//TODO: Also add updateAll() and drawAll() from LevelObj out!
public class FruitMgr {
    private static final String TAG = "FruitMgr";

    /**
     * Creates universally random fruits."
     */
    public static <F extends Fruit> ArrayList<F> createRandomFruits(@NonNull Context context, @NonNull Class<F> fruitClass, int numberOfFruits) {
        ArrayList<F> craftedFruits = new ArrayList<>();
        try {
            for (int i = 0; i < numberOfFruits; i++) {

                craftedFruits.add(fruitClass.getConstructor(Context.class).newInstance(context)); //use default constructor
            }
            return craftedFruits;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "createRandomFruits: Could not create fruits! Returned null.");
        return null;
    }
}
