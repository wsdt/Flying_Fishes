package yourowngame.com.yourowngame.gameEngine;

import yourowngame.com.yourowngame.classes.actors.BomberEnemy;
import yourowngame.com.yourowngame.classes.actors.Enemy;
import yourowngame.com.yourowngame.classes.actors.RoboticEnemy;
import yourowngame.com.yourowngame.classes.actors.SpawnEnemy;

/**
 * Created  on 17.03.2018.
 * <p>
 * a Highscore.class that provides a simple counter, to count the highscore
 */

public class Highscore {
    private static final String TAG = "Highscore";
    private int counter = 0;

    /** I am really NOT satisfied with this method. Tried it with reflection etc.
     * but it seems here quite difficult to be object-oriented AND using the struct pattern.
     *
     * todo: Why? If we add new enemies we always have to keep in mind that we have to update it here as well.
     *
     * Problem: We need to get the implicitely casted instance out from e. But that seems really not trivial.
     *
     * Casting NOT redundant, because otherwise we would receive default values of superclass Enemy.*/
    public void increment(Enemy e) {
        if (e instanceof RoboticEnemy) {
            this.counter += ((RoboticEnemy) e).getPositivePoints();
        } else if (e instanceof BomberEnemy) {
            this.counter += ((BomberEnemy) e).getPositivePoints();
        } else if (e instanceof SpawnEnemy) {
            this.counter += ((SpawnEnemy) e).getPositivePoints();
        } else {
            this.counter += e.getPositivePoints();
        }
    }

    /** Same as @method increment()
     *
     * if the player gets hit, or if he shoots something "good" dont know, but do not set the counter by params.*/
    public void decrement(Enemy e) {
        if (e instanceof RoboticEnemy) {
            this.counter -= ((RoboticEnemy) e).getNegativePoints();
        } else if (e instanceof BomberEnemy) {
            this.counter -= ((BomberEnemy) e).getNegativePoints();
        } else if (e instanceof SpawnEnemy) {
            this.counter -= ((SpawnEnemy) e).getNegativePoints();
        } else {
            this.counter -= e.getNegativePoints();
        }

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
