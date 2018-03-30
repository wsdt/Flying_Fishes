package yourowngame.com.yourowngame.gameEngine;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.Reward;

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
    private int counter = 0;

    /** increment method for enemy */
    public void increment(Enemy e){
        counter += e.getPositivePoints();
    }

    /** increment method for reward */
    public void increment(Reward r){
        counter += r.getReward();
    }

    /** enemys leaves the screen without getting killed, so the player's highscore decreases*/
    public void decrement(Enemy e) {
        counter -= e.getNegativePoints();
        //Set to 0, to avoid negative values
        if (this.counter < 0) {
            this.counter = 0;
        }
    }

    public void resetCounter() {
        this.counter = 0;
    }

    public int value() {
        return counter;
    }
}
