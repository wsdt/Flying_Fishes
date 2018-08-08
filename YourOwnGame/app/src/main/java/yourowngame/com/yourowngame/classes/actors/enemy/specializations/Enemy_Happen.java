package yourowngame.com.yourowngame.classes.actors.enemy.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;

/**
 * Created  on 12.03.2018.
 */

public class Enemy_Happen extends Enemy {
    private static final String TAG = "RoboEnemy";
    private static Bitmap[] images;

    /**
     * Happen Constants +++++++++++++++++++++++++++++++++++
     */
    private static final int HIGHSCORE_REWARD = 75;
    private static final int[] IMAGE_FRAMES = new int[]{R.drawable.enemy_happen_1};

    public Enemy_Happen(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    /**
     * Creates random enemy
     */
    public Enemy_Happen(@NonNull Activity activity) {
        super(activity); //also call super constr! (initializing)
    }

    /**
     * targetGameObj: In most cases a player obj. So the happen enemy follows the movements of the victimObj.
     */
    @Override
    public void update() {
        super.update();
        // special case: if the player jumps over an happen enemy, he has no chance to kill it. So the happen
        // will track the player to a specific value, and then just moves constant.
        if (this.getPosX() < 300) {
            this.setPosX(this.getPosX() - this.getSpeedX());
        } else {
            if (this.getTargetGameObj().getPosX() < this.getPosX())
                this.setPosX(this.getPosX() - this.getSpeedX());
            else if (this.getTargetGameObj().getPosX() > this.getPosX())
                this.setPosX(this.getPosX() + this.getSpeedX());

            if (this.getTargetGameObj().getPosY() < this.getPosY())
                this.setPosY(this.getPosY() - this.getSpeedY());
            else if (this.getTargetGameObj().getPosY() > this.getPosY())
                this.setPosY(this.getPosY() + this.getSpeedY());
        }
    }

    //in my opinion, a simple bitmap array would match the animation the best!
    //but we surely should do something to slow it down
    @Override
    public void draw() {
        this.setCurrentBitmap(getImages()[((int) this.getLoopCount() / 20 % IMAGE_FRAMES.length)]);
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
                Log.d(TAG, "Happen-Enemy: Successfully initialized!");
                setInitialized(true);
            }
        } catch (ClassCastException | NullPointerException e) {
            Log.d(TAG, "Happen-Enemy: Initialize Failure!");
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
        Enemy_Happen.images = images;
    }

    /**
     * Get reward method for highscore
     */
    @Override
    public int getReward() {
        return HIGHSCORE_REWARD;
    }
}
