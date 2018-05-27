package yourowngame.com.yourowngame.classes.actors.enemy.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.enemy.interfaces.IEnemy;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

/**
 * Created  on 12.03.2018.
 */

public class HappenEnemy extends Enemy implements IEnemy.PROPERTIES.HAPPEN {
    private static final String TAG = "RoboEnemy";
    private static Bitmap[] images;

    public HappenEnemy(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, @NonNull int[] img) {
        super(activity, posX, posY, speedX, speedY, img);
    }

    /**
     * Creates random enemy
     */
    public HappenEnemy(@NonNull Activity activity) {
        super(activity); //also call super constr! (initializing)

        this.setPosX(RandomMgr.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + ADDITIONAL_GAME_WIDTH));
        this.setPosY(RandomMgr.getRandomInt(0, GameViewActivity.GAME_HEIGHT + ADDITIONAL_GAME_WIDTH));
        this.setSpeedX(RandomMgr.getRandomFloat(SPEED_X_MIN, SPEED_X_MAX));
        this.setSpeedY(RandomMgr.getRandomFloat(SPEED_Y_MIN, SPEED_Y_MAX));

        /* Set default Image references (not needed in complex constr., bc. there we provide it
        dynamically) */
        this.setImg(IMAGE_FRAMES);
    }

    /**
     * targetGameObj: In most cases a player obj. So the happen enemy follows the movements of the victimObj.
     */
    @Override
    public void update() {
        if (getPosX() <= 0) {
            // Reset if out of screen
            this.resetPos();
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
        this.setCurrentBitmap(getImages()[((int) this.getLoopCount() % this.getImg().length)]);
        this.getCanvas().drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }

    @Override
    public void initialize() {
        try {
            if (!isInitialized()) {
                setImages(new Bitmap[this.getImg().length]);

                for (int imgFrame = 0; imgFrame < this.getImg().length; imgFrame++) {
                    getImages()[imgFrame] = this.getCraftedDynamicBitmap(imgFrame, DEFAULT_ROTATION, null, null);
                }

                this.setCurrentBitmap(getImages()[0]);
                Log.d(TAG, "Happen-Enemy: Successfully initialized!");
                setInitialized(true);
            }
        } catch (NoDrawableInArrayFound_Exception | ClassCastException | NullPointerException e) {
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
        HappenEnemy.images = images;
    }

    /**
     * Get reward method for highscore
     */
    @Override
    public int getReward() {
        return IEnemy.PROPERTIES.HAPPEN.HIGHSCORE_REWARD;
    }
}
