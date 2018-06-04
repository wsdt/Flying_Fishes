package yourowngame.com.yourowngame.classes.actors.fruits.fruitpowers;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitPower;

/** Slows enemies */
public class EnemySpeed extends FruitPower {
    private static final String TAG = "EnemySpeed";
    private ArrayList<Enemy> levelEnemies;

    /** @param durationMilliSeconds:  -> how long is fruit power active.*/
    public EnemySpeed(double amount, long durationMilliSeconds, @NonNull ArrayList<Enemy> levelEnemies) {
        super(amount, durationMilliSeconds);
        this.setLevelEnemies(levelEnemies);
    }

    @Override
    public void execute() {
        Log.d(TAG, "execute: Started method.");

        for(Enemy enemy : getLevelEnemies()) {
            enemy.setSpeedX(enemy.getSpeedX()*this.getAmount());
        }

        //Automatically stop it after amountTime.
        startStopTimer();
    }

    @Override
    public void stop() {
        //back to normal speed
        for(Enemy enemy : getLevelEnemies()) {
            enemy.setSpeedX(enemy.getSpeedX()/this.getAmount());
        }
    }

    //GETTER / SETTER ----------------------------------------------
    public ArrayList<Enemy> getLevelEnemies() {
        return levelEnemies;
    }

    public void setLevelEnemies(ArrayList<Enemy> levelEnemies) {
        this.levelEnemies = levelEnemies;
    }
}
