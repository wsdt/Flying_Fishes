package yourowngame.com.yourowngame.classes.actors.enemy;

import android.app.Activity;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.actors.enemy.specializations.Enemy_Happen;

/**
 * Used for generating enemies etc.
 */

//TODO: Also add updateAll() and drawAll() from LevelObj out!
public class EnemyMgr {
    private static final String TAG = "EnemyMgr";

    private EnemyMgr() {} //no instance allowed

    /**
     * Creates universally random enemies."
     */
    public static <E extends Enemy> ArrayList<E> createRandomEnemies(@NonNull Activity activity, @NonNull Class<E> enemyClass, @IntRange(from = 1) int numberOfEnemies) {
        ArrayList<E> craftedEnemies = new ArrayList<>();
        for (int i = 0; i < numberOfEnemies; i++) {
            E e = createRandomEnemy(activity,enemyClass);
            if (e == null) {
                Log.e(TAG, "createRandomFruits: Could not create fruits! Returned null.");
                continue; //abort when null
            }
            craftedEnemies.add(e);
        }
        return craftedEnemies;
    }

    /** Convenience method so we don't get a list for one enemy */
    public static <E extends Enemy> E createRandomEnemy(@NonNull Activity activity, @NonNull Class<E> enemyClass) {
        try {
            return enemyClass.getConstructor(Activity.class).newInstance(activity); //use default constructor
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
