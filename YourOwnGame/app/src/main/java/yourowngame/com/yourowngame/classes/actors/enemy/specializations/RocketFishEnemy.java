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

/**
 * Created on 12.03.2018.
 */

public class RocketFishEnemy extends Enemy implements IEnemy.ROCKETFISH_ENEMY_PROPERTIES {
    private static Bitmap[] images;
    private static final String TAG = "RocketFish";

    /**
     * Used in highscore (only getter/setter, because Highscore is the one who should increment itself) [By default 0, so new enemies would not do anything]
     * -- PositivePoints: E.g. when user shoot down an enemy, each specific enemy supplies a different amount of points.
     * -- NegativePoints: E.g. when enemy was not shoot and passed user without colliding, then user get's negative points. TODO hey thats a nice idea!
     * --> These fields will be set by default from every subclass. So we can modify it at any time.
     * <p>
     * --> SHOULD NOT BE STATIC also not in subclasses so we can modify also single enemies!
     */

    public RocketFishEnemy(@NonNull Context context, double posX, double posY, double speedX, double speedY, @NonNull int[] img, int rotationDegree, @Nullable String name) {
        super(context, posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    /**
     * Creates random enemy
     */
    public RocketFishEnemy(@NonNull Context context) {
        super(context); //also call super constr! (initializing)

        this.setPosX(RandomMgr.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + ADDITIONAL_GAME_WIDTH));
        this.setPosY(RandomMgr.getRandomInt(0, GameViewActivity.GAME_HEIGHT));
        this.setSpeedX(RandomMgr.getRandomFloat(IEnemy.ROCKETFISH_ENEMY_PROPERTIES.SPEED_X_MIN, IEnemy.ROCKETFISH_ENEMY_PROPERTIES.SPEED_X_MAX));
        this.setSpeedY(RandomMgr.getRandomFloat(IEnemy.ROCKETFISH_ENEMY_PROPERTIES.SPEED_Y_MIN, IEnemy.ROCKETFISH_ENEMY_PROPERTIES.SPEED_Y_MAX));
        this.setRotationDegree(DEFAULT_ROTATION);
        this.setName("Bomber");

        this.setCurrentBitmap(getImages()[0]);
    }

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        this.setPosX(this.getPosX() - this.getSpeedX());
        resetIfOutOfBounds();
    }


    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (int i = 0; i < getImages().length; i++) {
            canvas.drawBitmap(getImages()[i], (int) this.getPosX(), (int) this.getPosY(), null);
        }
    }


    @Override
    @SafeVarargs
    @Enhance(message = {"I get crazy, we have a really bad design here/everywhere with thousands of different Image getters/setters etc. " +
            "Additionally we are not consistent because player has another directive.",
            "Additionally we should consider putting the initialize() method of all enemies into the abstract base class because they will all look the same!"})
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        //we really need to change the initialize, Object params, instanceOf..
        this.setImg(IMAGE_FRAMES); //current design (bad!)

        try {
            if (!isInitialized) {
                setImages(new Bitmap[this.getImg().length]);

                for (int imgFrame = 0; imgFrame < this.getImg().length; imgFrame++) {
                    getImages()[imgFrame] = this.getCraftedDynamicBitmap(imgFrame, DEFAULT_ROTATION, null, null);
                }
                Log.d(TAG, "Robo-Enemy: Successfully initialized!");
                isInitialized = true;
            }
        } catch (NoDrawableInArrayFound_Exception | ClassCastException | NullPointerException e) {
            Log.d(TAG, "Robo-Enemy: Initialize Failure!");
            e.printStackTrace();
        }
        return isInitialized;
    }

    @Override
    public boolean cleanup() {
        resetWidthAndHeightOfEnemy(); //just reset y/x
        return true;
    }

    /**
     * Get reward method for highscore
     */
    @Override
    public int getReward() {
        return IEnemy.ROCKETFISH_ENEMY_PROPERTIES.HIGHSCORE_REWARD;
    }

    //GETTER/SETTER ---------------------------
    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        RocketFishEnemy.images = images;
    }
}
