package yourowngame.com.yourowngame.gameEngine;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.interfaces.IHighscore_RewardableObj;
import yourowngame.com.yourowngame.classes.annotations.Delete;
import yourowngame.com.yourowngame.gameEngine.interfaces.IHighscore_Observer;

/**
 * Created  on 17.03.2018.
 * <p>
 * a Highscore.class that provides a simple counter, to count the highscore
 *
 * @increment: add points to the highscore
 */

public class Highscore {
    private static final String TAG = "Highscore";

    /** Currently both non-static (not sure if this is good, but so we can have multiple highscore with own listeners at the same time [but maybe non-sense])*/
    private int counter = 0; //IMPORTANT-> Counter and registeredListenersList should be BOTH STATIC or BOTH NON-STATIC! (otherwise complications!)
    private List<IHighscore_Observer> registeredListeners = new ArrayList<>();

    public Highscore(int counter) {
        this.counter = counter;
    }

    public Highscore() {
    }

    /** increment method for reward (e.g. enemies or fruits are extending form that interface) */
    public <R extends IHighscore_RewardableObj> void increment(R rewardableObj){
        counter += rewardableObj.getReward();
        notifyAllListeners();
    }

    /** increment just once */
    @Delete(description = "Delete method after coins Highscore has it's own class.")
    @Deprecated
    public void increment(){
        counter++;
        notifyAllListeners();
    }

    @Delete(description = "I think we should not decrease the user's highscore (instead just make it harder to get points)")
    @Deprecated
    public void decrement(Enemy e) {
        counter -= 1; //e.getNegativePoints();
        //Set to 0, to avoid negative values
        if (counter < 0) {
            counter = 0;
        }
        notifyAllListeners();
    }

    public void resetCounter() {
        counter = 0;
        notifyAllListeners();
    }

    public int getValue() {
        return counter;
    }

    // OBSERVER PATTERN ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /** Allow registering */
    public void addListener(@NonNull IHighscore_Observer iHighscore_observer) {
        registeredListeners.add(iHighscore_observer);
    }

    public void removeListener(@NonNull IHighscore_Observer iHighscore_observer) {
        registeredListeners.remove(iHighscore_observer);
    }

    public void removeAllListeners() {
        for (IHighscore_Observer iHighscore_observer : registeredListeners) {
            removeListener(iHighscore_observer);
        }
    }

    /** Notify all registered objects */
    public void notifyAllListeners() {
        for (IHighscore_Observer iHighscore_observer : registeredListeners) {
            iHighscore_observer.onHighscoreChanged();
        }
        Log.d(TAG, "notifyAllListeners: Notified all highscore listeners.");
    }
}
