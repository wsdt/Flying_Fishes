package yourowngame.com.yourowngame.classes.actors.fruits;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.fruits.fruitpowers.TimeSlowMotion;
import yourowngame.com.yourowngame.classes.actors.fruits.interfaces.IFruit;
import yourowngame.com.yourowngame.classes.actors.interfaces.IHighscore_RewardableObj;
import yourowngame.com.yourowngame.classes.annotations.Bug;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;

@Enhance(message = {"Maybe replace isCollected/isOutOfBound etc. with Zustandsmuster",
    "Bitmap/Drawable int array consistency!"})


public abstract class Fruit extends GameObject implements IHighscore_RewardableObj, IFruit.DEFAULT_FRUIT_PROPERTIES {

    private List<FruitPower> fruitPowers = new ArrayList<>();
    private int spawnTime = 0;

    public Fruit(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    /**Creates random fruit*/
    public Fruit(@NonNull Activity activity) {
        super(activity);
    }

    public boolean hasLeftScreen(){
        return this.getPosX() < 0;
    }

    /** Execute when fruit has been collected. */
    public void fruitCollected() {
        //Execute all fruitPowers
        for (FruitPower fruitPower : this.getFruitPowers()) {
            fruitPower.execute();
        }
    }

    /** Set fruit powers. */
    public abstract void determineFruitPowers();

    //GETTER/SETTERS ---------------------------------------------
    public List<FruitPower> getFruitPowers() {
        return fruitPowers;
    }

    public void setFruitPowers(List<FruitPower> fruitPowers) {
        this.fruitPowers = fruitPowers;
    }

    public int getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(int spawnTime) {
        this.spawnTime = spawnTime;
    }
}
