package yourowngame.com.yourowngame.classes.actors.fruits.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.fruitpowers.FruitPower_EnemySpeed;
import yourowngame.com.yourowngame.classes.actors.fruits.interfaces.IFruit;
import yourowngame.com.yourowngame.classes.gamedesign.Level;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;


public class Fruit_Avoci extends Fruit implements IFruit.AVOCI_FRUIT_PROPERTIES {
    public static final String TAG = "Avoci";
    private static Bitmap[] images;

    public Fruit_Avoci(@NonNull Activity activity, @NonNull Level currLevel, double posX, double posY, double speedX, double speedY) {
        super(activity, currLevel, posX, posY, speedX, speedY);
        //fruit powers are determined in super constr
    }

    /**
     * Create random fruit
     */
    public Fruit_Avoci(@NonNull Activity activity, @NonNull Level currLevel) {
        super(activity, currLevel);

        this.setPosX(RandomMgr.getRandomInt(DrawableSurfaces.getDrawWidth(), DrawableSurfaces.getDrawWidth() + (int) OFF_TIME));
        this.setPosY(RandomMgr.getRandomInt(0, DrawableSurfaces.getDrawHeight()));
        this.setSpeedX(SPEED_X);
        this.setSpeedY(SPEED_Y);

        //Fruit powers are determined in super constr.
    }

    @Override
    public void determineFruitPowers(@NonNull Level currLevel) {
        this.getFruitPowers().add(new FruitPower_EnemySpeed(0.5,10000, currLevel.getAllEnemies()));
    }

    @Override
    public void removeFruitPowers(@NonNull Level currLevel) {
        this.getFruitPowers().remove(0);
    }


    /*************************************** UPDATE / DRAW *************************************************/
    @Override
    public void update() {
        this.setPosX(this.getPosX() - this.getSpeedX()); //just move them from right to left
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
                    getImages()[imgFrame] = getCraftedDynamicBitmap(this.getActivity(),IMAGE_FRAMES[imgFrame], DEFAULT_ROTATION, null, null);
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

    @Override
    public boolean cleanup() {
        resetPos();
        return true;
    }

    @Override
    public void resetPos() {
        this.setPosX(RandomMgr.getRandomFloat(DrawableSurfaces.getDrawWidth(), DrawableSurfaces.getDrawWidth() + IFruit.AVOCI_FRUIT_PROPERTIES.OFF_TIME));
        this.setPosY(RandomMgr.getRandomFloat(IFruit.DEFAULT_FRUIT_PROPERTIES.Y_UPLIFT, DrawableSurfaces.getDrawHeight() - IFruit.DEFAULT_FRUIT_PROPERTIES.Y_UPLIFT));
    }

    /**
     * Get reward method for highscore
     */
    @Override
    public int getReward() {
        return IFruit.AVOCI_FRUIT_PROPERTIES.HIGHSCORE_REWARD;
    }

    /******************Getter & Setter ******************/
    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        Fruit_Avoci.images = images;
    }
}
