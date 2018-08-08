package yourowngame.com.yourowngame.classes.actors.enemy.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

/**
 * Created  on 12.03.2018.
 */

public class Enemy_Boba extends Enemy {
    private static final String TAG = "BobaEnemy";
    private static Bitmap[] images;

    /**
     * Boba Constants ++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    private static final int HIGHSCORE_REWARD = 100;
    private static final int[] IMAGE_FRAMES = new int[]{R.drawable.enemy_boba_1};

    /**
     * READ -> if you use this constructor, the current img will not be set as the currentBitmap!
     */
    public Enemy_Boba(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    /**
     * Generates random enemy
     */
    public Enemy_Boba(@NonNull Activity activity) {
        super(activity); //also call super constr! (initializing)
    }


    @Override
    public void update() {
        super.update();
            double speed = this.getSpeedX();
            if (this.getTargetGameObj().getPosY() > (this.getPosY() - 50) && this.getTargetGameObj().getPosY() < (this.getPosY() + 50)) {
                speed *= 2; //double speed if user is at same/similar height
            }
            this.setPosX(this.getPosX() - speed);
    }

    @Override
    public void draw() {
        this.setCurrentBitmap(getImages()[((int) this.getLoopCount() / 5 % IMAGE_FRAMES.length)]);
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
        } catch (NullPointerException e) {
            Log.d(TAG, "Robo-Enemy: Initialize Failure!");
            e.printStackTrace();
        }
    }


    /**
     * GETTER / SETTER
     */

    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        Enemy_Boba.images = images;
    }


    /**
     * Get reward method for highscore
     */
    @Override
    public int getReward() {
        return HIGHSCORE_REWARD;
    }
}
