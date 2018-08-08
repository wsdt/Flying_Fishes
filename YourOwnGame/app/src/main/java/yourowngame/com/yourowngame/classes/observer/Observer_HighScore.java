package yourowngame.com.yourowngame.classes.observer;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import yourowngame.com.yourowngame.classes.actors.interfaces.IHighscore_RewardableObj;
import yourowngame.com.yourowngame.classes.observer.interfaces.IHighscore_Observer;

/**
 * HighScore.class that provides a simple counter, to count the highscore
 *
 */

public class Observer_HighScore {
    private static final String TAG = "HighScore";

    /** Currently both non-static (not sure if this is good, but so we can have multiple highscore with own listeners at the same time [but maybe non-sense])*/
    private int counter = 0; //IMPORTANT-> Counter and registeredListenersList should be BOTH STATIC or BOTH NON-STATIC! (otherwise complications!)
    private List<IHighscore_Observer> registeredListeners = new ArrayList<>();

    /** increment method for reward (e.g. enemies or fruits are extending form that interface) */
    public <R extends IHighscore_RewardableObj> void increment(R rewardableObj){
        counter += rewardableObj.getReward();
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
        Iterator<IHighscore_Observer> iterator = registeredListeners.iterator();

        while (iterator.hasNext()) {
            iterator.next(); //go to next
            iterator.remove(); //removeListener can be called normally (just for iterating use the iterator to avoid exception)
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
