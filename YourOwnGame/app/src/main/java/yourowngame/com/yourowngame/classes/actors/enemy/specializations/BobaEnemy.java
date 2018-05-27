package yourowngame.com.yourowngame.classes.actors.enemy.specializations;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.enemy.interfaces.IEnemy;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

import static yourowngame.com.yourowngame.classes.actors.enemy.interfaces.IEnemy.PROPERTIES.BOBA.HIGHSCORE_REWARD;

/**
 * Created  on 12.03.2018.
 */

public class BobaEnemy extends Enemy implements IEnemy.PROPERTIES.BOBA {
    private static final String TAG = "BobaEnemy";
    private static Bitmap[] images;

    /**READ -> if you use this constructor, the current img will not be set as the currentBitmap! */
    public BobaEnemy(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    /**
     * Generates random enemy
     */
    public BobaEnemy(@NonNull Activity activity) {
        super(activity); //also call super constr! (initializing)

        this.setPosX(RandomMgr.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + ADDITIONAL_GAME_WIDTH));
        this.setPosY(RandomMgr.getRandomInt(0, GameViewActivity.GAME_HEIGHT));
        this.setSpeedX(RandomMgr.getRandomFloat(SPEED_X_MIN, SPEED_X_MAX));
        this.setSpeedY(RandomMgr.getRandomFloat(SPEED_Y_MIN, SPEED_Y_MAX));
    }


    @Override
    public void update() {
        if(getPosX() <= 0){
            // Reset if out of screen
            this.resetPos();
        } else {
            this.setPosX(this.getPosX() - this.getSpeedX());
        }
    }

    @Override
    public void draw() {
        this.setCurrentBitmap(getImages()[((int) this.getLoopCount() % IMAGE_FRAMES.length)]);
        this.getCanvas().drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }


    @Override
    public void initialize() {
        try {
            if (!isInitialized()) {
                setImages(new Bitmap[IMAGE_FRAMES.length]);

                for (int imgFrame = 0; imgFrame < IMAGE_FRAMES.length; imgFrame++) {
                    getImages()[imgFrame] = this.getCraftedDynamicBitmap(IMAGE_FRAMES, imgFrame, DEFAULT_ROTATION, null, null);
                }
                this.setCurrentBitmap(getImages()[0]);

                Log.d(TAG, "Robo-Enemy: Successfully initialized!");
                setInitialized(true);
            }
        } catch (NoDrawableInArrayFound_Exception | NullPointerException e) {
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
        BobaEnemy.images = images;
    }


    /**
     * Get reward method for highscore
     */
    @Override
    public int getReward() {
        return IEnemy.PROPERTIES.BOBA.HIGHSCORE_REWARD;
    }
}
