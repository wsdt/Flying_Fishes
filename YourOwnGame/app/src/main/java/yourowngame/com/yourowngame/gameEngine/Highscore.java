package yourowngame.com.yourowngame.gameEngine;

import yourowngame.com.yourowngame.classes.actors.Enemy;

/**
 * Created  on 17.03.2018.
 *
 * a Highscore.class that provides a simple counter, to count the highscore
 */

public class Highscore {
    private final String TAG = "Highscore";
    private int counter = 0;

    public void increment(Enemy e){
        switch(e.getName()){
            case "Robotic": this.counter += 50; break;
            case "Bomber":  this.counter += 100; break;
            case "Spawn":   this.counter += 250; break;
            //many more to come...
        }
    }

    public void decrement(){
        this.counter -= 100;    //if the player gets hit, or if he shoots something "good" dont know, but do not set the counter by params.
        if(this.counter < 0){
            this.counter = 0;
        }
    }

    public void resetCounter(){
        this.counter = 0;
    }

    public int value(){
        return counter;
    }
}
