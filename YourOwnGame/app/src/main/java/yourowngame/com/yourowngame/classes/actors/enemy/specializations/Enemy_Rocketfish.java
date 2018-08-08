package yourowngame.com.yourowngame.classes.actors.enemy.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.annotations.Bug;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;

/**
 * Created on 12.03.2018.
 */

public class Enemy_Rocketfish extends Enemy {
    private static Bitmap[] images;
    private static final String TAG = "RocketFish";

    /**
     * Rocketfish Constants +++++++++++++++++++++++++++++
     */
    private static final int HIGHSCORE_REWARD = 50;
    @Bug(priority = Bug.Priority.MEDIUM, problem = "params not used/as the method is in base class",
            byDeveloper = Constants.Developers.WSDT)
    private static final float SPEED_X_MIN = 10f;
    private static final float SPEED_X_MAX = 15f; //TODO: could be also level dependent :) this will surely be level dependent :)
    private static final int[] IMAGE_FRAMES = new int[]{R.drawable.enemy_rocketfish_1};

    /**
     * Used in highscore (only getter/setter, because HighScore is the one who should increment itself) [By default 0, so new enemies would not do anything]
     * -- PositivePoints: E.g. when user shoot down an enemy, each specific enemy supplies a different amount of points.
     * -- NegativePoints: E.g. when enemy was not shoot and passed user without colliding, then user get's negative points. TODO hey thats a nice idea!
     * --> These fields will be set by default from every subclass. So we can modify it at any time.
     * <p>
     * --> SHOULD NOT BE STATIC also not in subclasses so we can modify also single enemies!
     */

    public Enemy_Rocketfish(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, double aggressivity) {
        super(activity, posX, posY, speedX, speedY, aggressivity);
    }

    /**
     * Creates random enemy
     */
    public Enemy_Rocketfish(@NonNull Activity activity) {
        super(activity); //also call super constr! (initializing)

        //does not use aggressivity value, so no setting here
    }

    @Override
    public void update() {
        super.update();
        this.setPosX(this.getPosX() - this.getSpeedX());
    }


    @Override
    public void draw() {
        this.setCurrentBitmap(getImages()[((int) this.getLoopCount() / 10 % IMAGE_FRAMES.length)]);
        this.getCanvas().drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }


    @Override
    public void initialize() {
        try {
            if (!isInitialized()) {
                setImages(new Bitmap[IMAGE_FRAMES.length]);

                for (int imgFrame = 0; imgFrame < IMAGE_FRAMES.length; imgFrame++) {
                    getImages()[imgFrame] = getCraftedDynamicBitmap(this.getActivity(), IMAGE_FRAMES[imgFrame], ROTATION_DEFAULT, null, null);
                }
                this.setCurrentBitmap(getImages()[0]);

                Log.d(TAG, "Robo-Enemy: Successfully initialized!");
                setInitialized(true);
            }
        } catch (ClassCastException | NullPointerException e) {
            Log.d(TAG, "Robo-Enemy: Initialize Failure!");
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

    //GETTER/SETTER ---------------------------
    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        Enemy_Rocketfish.images = images;
    }
}
