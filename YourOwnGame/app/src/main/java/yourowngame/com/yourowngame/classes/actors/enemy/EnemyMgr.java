package yourowngame.com.yourowngame.classes.actors.enemy;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

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
    public static <E extends Enemy> ArrayList<E> createRandomEnemies(@NonNull Activity activity, @NonNull Class<E> enemyClass, int numberOfEnemies) {
        ArrayList<E> craftedEnemies = new ArrayList<>();
        try {
            for (int i = 0; i < numberOfEnemies; i++) {
                craftedEnemies.add(enemyClass.getConstructor(Activity.class).newInstance(activity)); //use default constructor
            }
            return craftedEnemies;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "createRandomEnemies: Could not create enemies! Returned null.");
        return null;
    }
}
