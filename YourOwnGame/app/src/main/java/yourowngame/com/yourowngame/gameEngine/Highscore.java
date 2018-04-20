package yourowngame.com.yourowngame.gameEngine;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.Reward;
import yourowngame.com.yourowngame.gameEngine.interfaces.IHighscore_Observer;

/**
 * Created  on 17.03.2018.
 * <p>
 * a Highscore.class that provides a simple counter, to count the highscore
 *
 * So basically the highscore class only provides 2 methods
 *
 * @increment: add points to the highscore
 * @decrement: remove points from the highscore
 *
 * That class looks wonderful!
 *
 */

public class Highscore {
    private static final String TAG = "Highscore";

    /** Currently both non-static (not sure if this is good, but so we can have multiple highscore with own listeners at the same time [but maybe non-sense])*/
    private int counter = 0; //IMPORTANT-> Counter and registeredListenersList should be BOTH STATIC or BOTH NON-STATIC! (otherwise complications!)
    private List<IHighscore_Observer> registeredListeners = new ArrayList<>();


    /** increment method for enemy */
    public void increment(Enemy e){
        counter += e.getPositivePoints();
        notifyAllListeners();
    }

    /** increment method for reward */
    public void increment(Reward r){
        counter += r.getReward();
        notifyAllListeners();
    }

    /** increment just once */
    public void increment(){
        counter++;
        notifyAllListeners();
    }

    /** enemys leaves the screen without getting killed, so the player's highscore decreases*/
    public void decrement(Enemy e) {
        counter -= e.getNegativePoints();
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

    /** Notify all registered objects */
    public void notifyAllListeners() {
        for (IHighscore_Observer iHighscore_observer : registeredListeners) {
            iHighscore_observer.onHighscoreChanged();
        }
        Log.d(TAG, "notifyAllListeners: Notified all highscore listeners.");
    }
}
