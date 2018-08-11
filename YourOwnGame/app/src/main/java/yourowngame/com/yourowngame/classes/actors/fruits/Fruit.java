package yourowngame.com.yourowngame.classes.actors.fruits;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.interfaces.IHighscore_RewardableObj;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.game_modes.DrawableLevel;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.Level;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

@Enhance(message = {"Maybe replace isCollected/isOutOfBound etc. with Zustandsmuster",
        "Bitmap/Drawable int array consistency!"})


public abstract class Fruit extends GameObject implements IHighscore_RewardableObj {
    private static final String TAG = "Fruit";
    private List<FruitPower> fruitPowers = new ArrayList<>();
    /**
     * Needed to control in future the spawning of fruits (They should occur more randomly or even
     * only a certain amount of times within one level as they are used as currency later.
     */
    private int spawnTime = 0;

    /**
     * Default fruit constants +++++++++++++++++++++++++++++++++++++++
     */
    protected static final float SPEED_X = 10f;
    protected static final float SPEED_Y = 10f;

    public Fruit(@NonNull Activity activity, @NonNull DrawableLevel currLevel, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
        determineFruitPowers(currLevel);
    }

    /**
     * Creates random fruit
     */
    public Fruit(@NonNull Activity activity, @NonNull DrawableLevel currLevel) {
        super(activity);
        determineFruitPowers(currLevel);
    }

    /**
     * Fruits need to differ in here
     */
    @Override
    public void resetPos() {
        this.setPosX(RandomMgr.getRandomFloat(DrawableSurfaces.getDrawWidth()+this.getWidthOfBitmap(), (DrawableSurfaces.getDrawWidth()+this.getWidthOfBitmap())*2)); //+width of bitmap to spawn outside of screen and not simultaneously (*2)
        this.setPosY(RandomMgr.getRandomFloat(this.getHeightOfBitmap(), DrawableSurfaces.getDrawHeight() - this.getHeightOfBitmap()));
    }

    @Override
    public void resetSpeed() {
        this.setSpeedX(SPEED_X);
        this.setSpeedY(SPEED_Y);
    }

    /**
     * Execute when fruit has been collected.
     */
    public void fruitCollected() {
        Log.d(TAG, "fruitCollected: Executing powers -> " + this.getFruitPowers().size());

        //Execute all fruitPowers
        for (FruitPower fruitPower : this.getFruitPowers()) {
            fruitPower.execute();
        }
    }

    /**
     * Set fruit powers.
     */
    public abstract void determineFruitPowers(@NonNull DrawableLevel currLevel);

    /**
     * Remove fruit powers, for bonus levels!
     */
    public abstract void removeFruitPowers(@NonNull DrawableLevel currLevel);

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
