package yourowngame.com.yourowngame.classes.observer;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.interfaces.IHighscore_RewardableObj;
import yourowngame.com.yourowngame.classes.observer.interfaces.IFruitCounter_Observer;
import yourowngame.com.yourowngame.classes.observer.interfaces.IHighscore_Observer;

/**
 * FruitCounter.class that provides a simple counter, to count the highscore
 *
 */

public class Observer_FruitCounter {
    private static final String TAG = "FruitObserver";

    /** Currently both non-static (not sure if this is good, but so we can have multiple fruitobservers
     * with own listeners at the same time [but maybe non-sense])
     *
     * F = Pinapo.class, Meloon.class etc.
     * Integer = Counted fruits */
    private Map<Class, Integer> fruitCounter = new HashMap<>(); //non-static !
    private List<IFruitCounter_Observer> registeredListeners = new ArrayList<>();

    /** increment method for collected fruit (e.g. pinapo) */
    public void fruitCollected(Fruit fruit){
        if (fruitCounter.containsKey(fruit.getClass())) {
            fruitCounter.put(fruit.getClass(), fruitCounter.get(fruit.getClass()) + 1); //increment by one
        } else {
            fruitCounter.put(fruit.getClass(), 1); //first fruit
        }
        notifyAllListeners();
    }

    public void resetCounter() {
        fruitCounter = new HashMap<>();
        notifyAllListeners();
    }

    public Map getFruitCounter() {
        return fruitCounter;
    }

    // OBSERVER PATTERN ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /** Allow registering */
    public void addListener(@NonNull IFruitCounter_Observer iFruitCounter_observer) {
        registeredListeners.add(iFruitCounter_observer);
    }

    public void removeListener(@NonNull IFruitCounter_Observer iFruitCounter_observer) {
        registeredListeners.remove(iFruitCounter_observer);
    }

    public void removeAllListeners() {
        Iterator<IFruitCounter_Observer> iterator = registeredListeners.iterator();

        while (iterator.hasNext()) {
            iterator.next(); //go to next
            iterator.remove(); //removeListener can be called normally (just for iterating use the iterator to avoid exception)
        }
    }

    /** Notify all registered objects */
    public void notifyAllListeners() {
        for (IFruitCounter_Observer iFruitCounter_observer : registeredListeners) {
            iFruitCounter_observer.onFruitCountChanged();
        }
        Log.d(TAG, "notifyAllListeners: Notified all fruitCounter listeners.");
    }
}
