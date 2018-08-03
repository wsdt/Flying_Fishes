package yourowngame.com.yourowngame.classes.actors.enemy.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.enemy.interfaces.IEnemy;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

/**
 * Created  on 12.03.2018.
 */

public class Enemy_Boba extends Enemy implements IEnemy.PROPERTIES.BOBA {
    private static final String TAG = "BobaEnemy";
    private static Bitmap[] images;

    /**READ -> if you use this constructor, the current img will not be set as the currentBitmap! */
    public Enemy_Boba(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    /**
     * Generates random enemy
     */
    public Enemy_Boba(@NonNull Activity activity) {
        super(activity); //also call super constr! (initializing)

        this.setPosX(RandomMgr.getRandomInt(DrawableSurfaces.getDrawWidth(), DrawableSurfaces.getDrawWidth() + ADDITIONAL_GAME_WIDTH));
        this.setPosY(RandomMgr.getRandomInt(0, DrawableSurfaces.getDrawHeight()));
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
        this.setCurrentBitmap(getImages()[((int) this.getLoopCount()/5 % IMAGE_FRAMES.length)]);
        this.getCanvas().drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }


    @Override
    public void initialize() {
        try {
            if (!isInitialized()) {
                setImages(new Bitmap[IMAGE_FRAMES.length]);

                for (int imgFrame = 0; imgFrame < IMAGE_FRAMES.length; imgFrame++) {
                    getImages()[imgFrame] = getCraftedDynamicBitmap(this.getActivity(), IMAGE_FRAMES[imgFrame], DEFAULT_ROTATION, null, null);
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
        return IEnemy.PROPERTIES.BOBA.HIGHSCORE_REWARD;
    }
}
