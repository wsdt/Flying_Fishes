package yourowngame.com.yourowngame.classes.actors.fruits.fruitpowers;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitPower;

/** Slows enemies */
public class EnemySpeed extends FruitPower {
    private ArrayList<Enemy> levelEnemies;

    /** @param durationMilliSeconds:  -> how long is fruit power active.*/
    public EnemySpeed(double amount, long durationMilliSeconds, @NonNull ArrayList<Enemy> levelEnemies) {
        super(amount, durationMilliSeconds);
        this.setLevelEnemies(levelEnemies);
    }

    @Override
    public void execute() {
        Enemy.setModifierOfList(getLevelEnemies(),this.getAmount());

        //Automatically stop it after amountTime.
        startStopTimer();
    }

    @Override
    public void stop() {
        //back to normal speed
        Enemy.setModifierOfList(getLevelEnemies(),1); //1=default value [Attention! If other fruits or lvls changed this value it gets overwritten]
    }

    //GETTER / SETTER ----------------------------------------------
    public ArrayList<Enemy> getLevelEnemies() {
        return levelEnemies;
    }

    public void setLevelEnemies(ArrayList<Enemy> levelEnemies) {
        this.levelEnemies = levelEnemies;
    }
}
