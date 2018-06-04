package yourowngame.com.yourowngame.classes.actors.fruits.fruitpowers;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitPower;
import yourowngame.com.yourowngame.classes.annotations.Bug;

/** Removes some (= amount) enemies for a given time. */
public class FruitPower_RemoveEnemies extends FruitPower {
    private static final String TAG = "RemoveEnemies";
    private ArrayList<Enemy> removedEnemies = new ArrayList<>(); //should be initialized
    private ArrayList<Enemy> levelEnemies;

    /** @param amount: How many enemies to remove. Here exceptionally a int instead of double so
     * we can cast it without any problems later. */
    public FruitPower_RemoveEnemies(int amount, long durationMilliSeconds, @NonNull ArrayList<Enemy> levelEnemies) {
        super(amount, durationMilliSeconds);
        this.setLevelEnemies(levelEnemies);
    }

    @Override
    public void execute() {
        Log.d(TAG, "execute: Started method.");

        /* Iterate over levelEnemies acc. to desired amount [= how many enemies should be removed]
        * If desired Amount of rvm enemies is bigger than level size, then just remove all enemies except 1.*/
        int countLvlEnemies = this.getLevelEnemies().size();
        int enemiesToRemove; //all numbers
        if (countLvlEnemies <= 1) {
            //Keep at least 1 enemy, so we do not remove any!
            enemiesToRemove = 0;
        } else if (this.getAmount() > countLvlEnemies) {
            enemiesToRemove = countLvlEnemies-1;
        } else {
            enemiesToRemove = (int) this.getAmount();
        }
        Log.d(TAG, "execute: Removing enemies -> "+enemiesToRemove);

        int i = 0;
        for (Iterator<Enemy> enemyIterator = this.getLevelEnemies().iterator(); (enemyIterator.hasNext() && (i++) < enemiesToRemove);) {
            /* save enemy for adding it later again and always get first one (bc. 0 should here always exist) */
            this.getRemovedEnemies().add(this.getLevelEnemies().get(0));
            this.getLevelEnemies().remove(0);
            Log.d(TAG, "execute: Removed enemy.");
        }

        //Show removed enemies after duration
        startStopTimer();
    }

    @Override
    public void stop() {
        for (Enemy removedEnemy : this.getRemovedEnemies()) {
            removedEnemy.resetPos(); //reset pos for avoiding spawning in the middle of the screen and clashing with player
            this.getLevelEnemies().add(removedEnemy);
        }
    }

    //GETTER/SETTER ----------------------------------------------------
    public ArrayList<Enemy> getRemovedEnemies() {
        return removedEnemies;
    }

    public void setRemovedEnemies(ArrayList<Enemy> removedEnemies) {
        this.removedEnemies = removedEnemies;
    }

    public ArrayList<Enemy> getLevelEnemies() {
        return levelEnemies;
    }

    public void setLevelEnemies(ArrayList<Enemy> levelEnemies) {
        this.levelEnemies = levelEnemies;
    }
}
