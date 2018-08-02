package yourowngame.com.yourowngame.classes.actors.enemy.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.enemy.interfaces.IEnemy;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

/**
 * Created on 12.03.2018.
 */

public class Enemy_Rocketfish extends Enemy implements IEnemy.PROPERTIES.ROCKETFISH {
    private static Bitmap[] images;
    private static final String TAG = "RocketFish";

    /**
     * Used in highscore (only getter/setter, because HighScore is the one who should increment itself) [By default 0, so new enemies would not do anything]
     * -- PositivePoints: E.g. when user shoot down an enemy, each specific enemy supplies a different amount of points.
     * -- NegativePoints: E.g. when enemy was not shoot and passed user without colliding, then user get's negative points. TODO hey thats a nice idea!
     * --> These fields will be set by default from every subclass. So we can modify it at any time.
     * <p>
     * --> SHOULD NOT BE STATIC also not in subclasses so we can modify also single enemies!
     */

    public Enemy_Rocketfish(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    /**
     * Creates random enemy
     */
    public Enemy_Rocketfish(@NonNull Activity activity) {
        super(activity); //also call super constr! (initializing)

        this.setPosX(RandomMgr.getRandomInt(DrawableSurfaces.getDrawWidth(), DrawableSurfaces.getDrawWidth() + ADDITIONAL_GAME_WIDTH));
        this.setPosY(RandomMgr.getRandomInt(0, DrawableSurfaces.getDrawHeight()));
        this.setSpeedX(RandomMgr.getRandomFloat(IEnemy.PROPERTIES.ROCKETFISH.SPEED_X_MIN, IEnemy.PROPERTIES.ROCKETFISH.SPEED_X_MAX));
        this.setSpeedY(RandomMgr.getRandomFloat(IEnemy.PROPERTIES.ROCKETFISH.SPEED_Y_MIN, IEnemy.PROPERTIES.ROCKETFISH.SPEED_Y_MAX));
    }

    @Override
    public void update() {
        if (getPosX() <= 0) {
            // Reset if out of screen
            this.resetPos();
        } else {
            this.setPosX(this.getPosX() - this.getSpeedX());
        }
    }


    @Override
    public void draw() {
        this.setCurrentBitmap(getImages()[((int) this.getLoopCount()/10 % IMAGE_FRAMES.length)]);
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
        } catch (NoDrawableInArrayFound_Exception | ClassCastException | NullPointerException e) {
            Log.d(TAG, "Robo-Enemy: Initialize Failure!");
            e.printStackTrace();
        }
    }


    /**
     * Get reward method for highscore
     */
    @Override
    public int getReward() {
        return IEnemy.PROPERTIES.ROCKETFISH.HIGHSCORE_REWARD;
    }

    //GETTER/SETTER ---------------------------
    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        Enemy_Rocketfish.images = images;
    }
}
