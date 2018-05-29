package yourowngame.com.yourowngame.classes.actors.fruits.fruitpowers;

import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.FruitPower;
import yourowngame.com.yourowngame.classes.gamelevels.Level;

/** Slows time */
public class TimeSlowMotion extends FruitPower {
    private Level activeOnLevel;

    /** @param durationMilliSeconds: (= amount) -> how long is fruit power active.*/
    public TimeSlowMotion(long durationMilliSeconds, @NonNull Level currLevel) {
        super(durationMilliSeconds);
        this.setActiveOnLevel(currLevel);
    }

    @Override
    public void execute() {
        Enemy.setModifierOfList(this.getActiveOnLevel().getAllEnemies(),this.getAmount());

        //Automatically stop it after amountTime.
        startStopTimer();
    }

    @Override
    public void stop() {
        //back to normal speed
        Enemy.setModifierOfList(this.getActiveOnLevel().getAllEnemies(),1);
    }

    //GETTER / SETTER ----------------------------------------------
    public Level getActiveOnLevel() {
        return activeOnLevel;
    }

    public void setActiveOnLevel(Level activeOnLevel) {
        this.activeOnLevel = activeOnLevel;
    }
}
