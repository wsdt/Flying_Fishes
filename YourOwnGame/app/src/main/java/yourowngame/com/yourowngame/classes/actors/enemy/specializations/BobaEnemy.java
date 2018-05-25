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
    public BobaEnemy(@NonNull Context context, double posX, double posY, double speedX, double speedY, @NonNull int[] img, int rotationDegree, @Nullable String name) {
        super(context, posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    /**
     * Generates random enemy
     */
    public BobaEnemy(@NonNull Context context) {
        super(context); //also call super constr! (initializing)

        this.setPosX(RandomMgr.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + ADDITIONAL_GAME_WIDTH));
        this.setPosY(RandomMgr.getRandomInt(0, GameViewActivity.GAME_HEIGHT));
        this.setSpeedX(RandomMgr.getRandomFloat(SPEED_X_MIN, SPEED_X_MAX));
        this.setSpeedY(RandomMgr.getRandomFloat(SPEED_Y_MIN, SPEED_Y_MAX));
        this.setRotationDegree(DEFAULT_ROTATION);
        this.setName("Spawn");

        this.setCurrentBitmap(getImages()[0]);
    }


    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        if(getPosX() <= 0){
            // Reset if out of screen
            this.resetPos();
        } else {
            this.setPosX(this.getPosX() - this.getSpeedX());
        }
    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        this.setCurrentBitmap(getImages()[((int) loopCount % this.getImg().length)]);
        canvas.drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
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
        } catch (NoDrawableInArrayFound_Exception | NullPointerException e) {
            Log.d(TAG, "Robo-Enemy: Initialize Failure!");
            e.printStackTrace();
        }
        return isInitialized;
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
