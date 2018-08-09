package yourowngame.com.yourowngame.classes.actors.fruits.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.fruitpowers.FruitPower_EnemySpeed;
import yourowngame.com.yourowngame.classes.mode_adventure.Level;


public class Fruit_Avoci extends Fruit {
    public static final String TAG = "Avoci";
    private static Bitmap[] images;

    /**
     * Avoci Constants +++++++++++++++++++++++
     */
    private static final int HIGHSCORE_REWARD = 50;
    private static final int[] IMAGE_FRAMES = new int[]{R.drawable.avoci};

    public Fruit_Avoci(@NonNull Activity activity, @NonNull Level currLevel, double posX, double posY, double speedX, double speedY) {
        super(activity, currLevel, posX, posY, speedX, speedY);
        //fruit powers are determined in super constr
    }

    /**
     * Create random fruit
     */
    public Fruit_Avoci(@NonNull Activity activity, @NonNull Level currLevel) {
        super(activity, currLevel);
        //Fruit powers are determined in super constr.
    }

    @Override
    public void determineFruitPowers(@NonNull Level currLevel) {
        this.getFruitPowers().add(new FruitPower_EnemySpeed(0.5, 10000, currLevel.getAllEnemies()));
    }

    @Override
    public void removeFruitPowers(@NonNull Level currLevel) {
        this.getFruitPowers().remove(0);
    }


    /*************************************** UPDATE / DRAW *************************************************/
    @Override
    public void update() {
        super.update();
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
        Fruit_Avoci.images = images;
    }
}
