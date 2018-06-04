package yourowngame.com.yourowngame.classes.actors.fruits;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.fruits.interfaces.IFruit;
import yourowngame.com.yourowngame.classes.actors.interfaces.IHighscore_RewardableObj;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.gamelevels.Level;

@Enhance(message = {"Maybe replace isCollected/isOutOfBound etc. with Zustandsmuster",
    "Bitmap/Drawable int array consistency!"})


public abstract class Fruit extends GameObject implements IHighscore_RewardableObj, IFruit.DEFAULT_FRUIT_PROPERTIES {

    private List<FruitPower> fruitPowers = new ArrayList<>();
    @Enhance (message = "Needed or for what is this param?")
    private int spawnTime = 0;


    public Fruit(@NonNull Activity activity, @NonNull Level currLevel, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
        determineFruitPowers(currLevel);
    }

    /**Creates random fruit*/
    public Fruit(@NonNull Activity activity, @NonNull Level currLevel) {
        super(activity);
        determineFruitPowers(currLevel);
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
    public abstract void determineFruitPowers(@NonNull Level currLevel);

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
