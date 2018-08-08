package yourowngame.com.yourowngame.classes.actors.fruits.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.fruitpowers.FruitPower_PlayerSpeed;
import yourowngame.com.yourowngame.classes.gamedesign.Level;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

public class Fruit_Meloon extends Fruit {
    private static final String TAG = "Meloon";
    private static Bitmap[] images;

    /**
     * Meloon constants +++++++++++++++++++++++++++
     */
    private static final int HIGHSCORE_REWARD = 100;
    private static final int[] IMAGE_FRAMES = new int[]{R.drawable.fruit_meloon};

    public Fruit_Meloon(@NonNull Activity activity, @NonNull Level currLevel, double posX, double posY, double speedX, double speedY) {
        super(activity, currLevel, posX, posY, speedX, speedY);
        //powers assigned in super constr
    }

    /**
     * Creates random fruit
     */
    public Fruit_Meloon(@NonNull Activity activity, @NonNull Level currLevel) {
        super(activity, currLevel); //also call super constr! (initializing) and determining powers
    }

    @Override
    public void determineFruitPowers(@NonNull Level currLevel) {
        this.getFruitPowers().add(new FruitPower_PlayerSpeed(2, 10000, currLevel.getPlayer()));
    }

    @Override
    public void removeFruitPowers(@NonNull Level currLevel) {
        this.getFruitPowers().remove(0);
    }


    /*************************************** UPDATE / DRAW *************************************************/
    @Override
    public void update() {
        super.update();
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

                for (int imgFrame = 0; imgFrame < IMAGE_FRAMES.length; imgFrame++) {
                    getImages()[imgFrame] = getCraftedDynamicBitmap(this.getActivity(), IMAGE_FRAMES[imgFrame], ROTATION_DEFAULT, null, null);
                }
                this.setCurrentBitmap(getImages()[0]);

                Log.d(TAG, "Meloon-Fruit: Successfully initialized!");
                setInitialized(true);
            }
        } catch (ClassCastException | NullPointerException e) {
            Log.d(TAG, "Meloon-Fruit: Initialize Failure!");
            e.printStackTrace();
        }
    }

    /**
     * Get reward method for highscore
     */
    @Override
    public int getReward() {
        return HIGHSCORE_REWARD;
    }

    /******************Getter & Setter ******************/
    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        Fruit_Meloon.images = images;
    }


}
