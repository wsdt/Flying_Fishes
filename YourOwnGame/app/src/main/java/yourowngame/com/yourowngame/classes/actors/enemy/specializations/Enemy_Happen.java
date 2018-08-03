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

public class Enemy_Happen extends Enemy implements IEnemy.PROPERTIES.HAPPEN {
    private static final String TAG = "RoboEnemy";
    private static Bitmap[] images;

    public Enemy_Happen(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    /**
     * Creates random enemy
     */
    public Enemy_Happen(@NonNull Activity activity) {
        super(activity); //also call super constr! (initializing)

        this.setPosX(RandomMgr.getRandomInt(DrawableSurfaces.getDrawWidth(), DrawableSurfaces.getDrawWidth() + ADDITIONAL_GAME_WIDTH));
        this.setPosY(RandomMgr.getRandomInt(0, DrawableSurfaces.getDrawHeight() + ADDITIONAL_GAME_WIDTH));
        this.setSpeedX(RandomMgr.getRandomFloat(SPEED_X_MIN, SPEED_X_MAX));
        this.setSpeedY(RandomMgr.getRandomFloat(SPEED_Y_MIN, SPEED_Y_MAX));
    }

    /**
     * targetGameObj: In most cases a player obj. So the happen enemy follows the movements of the victimObj.
     */
    @Override
    public void update() {
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
        this.setCurrentBitmap(getImages()[((int) this.getLoopCount()/20 % IMAGE_FRAMES.length)]);
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
        return IEnemy.PROPERTIES.HAPPEN.HIGHSCORE_REWARD;
    }
}
