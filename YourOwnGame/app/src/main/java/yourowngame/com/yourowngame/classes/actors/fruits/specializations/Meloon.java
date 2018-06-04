package yourowngame.com.yourowngame.classes.actors.fruits.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.fruitpowers.PlayerSpeed;
import yourowngame.com.yourowngame.classes.actors.fruits.interfaces.IFruit;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

public class Meloon extends Fruit implements IFruit.MELOON_FRUIT_PROPERTIES {
    private static final String TAG = "Meloon";

    private static Bitmap[] images;

    public Meloon(@NonNull Activity activity, @NonNull Level currLevel, double posX, double posY, double speedX, double speedY) {
        super(activity, currLevel, posX, posY, speedX, speedY);
        //powers assigned in super constr
    }

    /**
     * Creates random fruit
     */
    public Meloon(@NonNull Activity activity, @NonNull Level currLevel) {
        super(activity, currLevel); //also call super constr! (initializing) and determining powers

        this.setPosX(RandomMgr.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + (int) OFF_TIME));
        this.setPosY(RandomMgr.getRandomInt(0, GameViewActivity.GAME_HEIGHT));
        this.setSpeedX(SPEED_X);
        this.setSpeedY(SPEED_Y);
    }

    @Override
    public void determineFruitPowers(@NonNull Level currLevel) {
        this.getFruitPowers().add(new PlayerSpeed(4,10,currLevel.getPlayer()));
    }


    /*************************************** UPDATE / DRAW *************************************************/
    @Override
    public void update() {
        this.setPosX(this.getPosX() - this.getSpeedX());
    }

    @Override
    public void draw() {
        this.setCurrentBitmap(getImages()[((int) this.getLoopCount() % IMAGE_FRAMES.length)]);
        this.getCanvas().drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }

    /*************************************** UPDATE / DRAW *************************************************/


    @Override
    public void initialize() {
        try {
            if (!isInitialized()) {
                setImages(new Bitmap[IMAGE_FRAMES.length]);

                for (int imgFrame = 0; imgFrame <IMAGE_FRAMES.length; imgFrame++) {
                    getImages()[imgFrame] = this.getCraftedDynamicBitmap(IMAGE_FRAMES, imgFrame, DEFAULT_ROTATION, null, null);
                }
                this.setCurrentBitmap(getImages()[0]);

                Log.d(TAG, "Meloon-Fruit: Successfully initialized!");
                setInitialized(true);
            }
        } catch (NoDrawableInArrayFound_Exception | ClassCastException | NullPointerException e) {
            Log.d(TAG, "Meloon-Fruit: Initialize Failure!");
            e.printStackTrace();
        }
    }


    /**
     * reset positions, return true
     */
    @Override
    public boolean cleanup() {
        resetPos();
        return true;
    }

    /**
     * Fruits need to differ in here
     */
    @Override
    public void resetPos() {
        this.setPosX(RandomMgr.getRandomFloat(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + IFruit.MELOON_FRUIT_PROPERTIES.OFF_TIME));
        this.setPosY(RandomMgr.getRandomFloat(IFruit.DEFAULT_FRUIT_PROPERTIES.Y_UPLIFT, GameViewActivity.GAME_HEIGHT - IFruit.DEFAULT_FRUIT_PROPERTIES.Y_UPLIFT));
    }

    /**
     * Get reward method for highscore
     */
    @Override
    public int getReward() {
        return IFruit.MELOON_FRUIT_PROPERTIES.HIGHSCORE_REWARD;
    }

    /******************Getter & Setter ******************/
    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        Meloon.images = images;
    }


}
