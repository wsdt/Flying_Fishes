package yourowngame.com.yourowngame.classes.actors.fruits;

import java.util.Timer;
import java.util.TimerTask;

import yourowngame.com.yourowngame.classes.Feature;

/**
 * Analogy to LevelAssigment.class
 * What influence have collected fruits.
 */
public abstract class FruitPower extends Feature {
    /** How long should be Feature activated.
     * E.g. 10 Seconds (= 10*1000), etc. */
    private long durationMilliseconds;

    /** amount (e.g. new levelConstant or multiplier for changing player attributes etc.)
     *
     * DurationMilliSeconds SHOULD ONLY BE LONG (thread startStopTimer())*/
    public FruitPower(double amount, long durationMilliSeconds) {
        super(amount);
        this.setDurationMilliseconds(durationMilliSeconds);
    }

    /**
     * What should happen, when fruit is collected. Hence, this method should be called when
     * a fruit has been collected (maybe listeners?)
     *
     * Needed params e.g. Enemy objs or levelObj should be class members of subclasses.
     */
    public abstract void execute();
    /** Is executed after time is up. */
    public abstract void stop();

    /** IMPORTANT: Has to be called in execute()
     * Otherwise we have to end the fruitpower manually.
     */
    public void startStopTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                stop();

                //Clear up timerTask (loose Thread)
                cancel();
            }
        }, this.getDurationMilliseconds());
    }

    public long getDurationMilliseconds() {
        return durationMilliseconds;
    }

    public void setDurationMilliseconds(long durationMilliseconds) {
        this.durationMilliseconds = durationMilliseconds;
    }
}
