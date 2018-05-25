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
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

/**
 * Created  on 12.03.2018.
 *
 */

public class HappenEnemy extends Enemy implements IEnemy.PROPERTIES.HAPPEN {
    private static final String TAG = "RoboEnemy";
    private static Bitmap[] images;

    public HappenEnemy(@NonNull Context context, double posX, double posY, double speedX, double speedY, @NonNull int[] img, int rotationDegree, @Nullable String name) {
        super(context, posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    /** Creates random enemy */
    public HappenEnemy(@NonNull Context context){
        super(context); //also call super constr! (initializing)

        this.setPosX(RandomMgr.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + ADDITIONAL_GAME_WIDTH));
        this.setPosY(RandomMgr.getRandomInt(0, GameViewActivity.GAME_HEIGHT + ADDITIONAL_GAME_WIDTH));
        this.setSpeedX(RandomMgr.getRandomFloat(SPEED_X_MIN, SPEED_X_MAX));
        this.setSpeedY(RandomMgr.getRandomFloat(SPEED_Y_MIN, SPEED_Y_MAX));
        this.setRotationDegree(DEFAULT_ROTATION);
        this.setName("Robotic");

        this.setCurrentBitmap(getImages()[0]);
    }

    /** @param victim: In most cases a player obj. So the happen enemy follows the movements of the victimObj. */
    @Override
    public void update(GameObject victim, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        if(getPosX() <= 0){
            // Reset if out of screen
            this.resetPos();
        } else {
            if (victim.getPosX() < this.getPosX())
                this.setPosX(this.getPosX() - this.getSpeedX());
            else if (victim.getPosX() > this.getPosX())
                this.setPosX(this.getPosX() + this.getSpeedX());

            if (victim.getPosY() < this.getPosY())
                this.setPosY(this.getPosY() - this.getSpeedY());
            else if (victim.getPosY() > this.getPosY())
                this.setPosY(this.getPosY() + this.getSpeedY());
        }

    }

    //in my opinion, a simple bitmap array would match the animation the best!
    //but we surely should do something to slow it down
    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        this.setCurrentBitmap(getImages()[((int) loopCount % this.getImg().length)]);
        canvas.drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }

    @Override
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        this.setImg(IMAGE_FRAMES); //current design (bad!)

        try {
            if (!isInitialized) {
                    setImages(new Bitmap[this.getImg().length]);

                    for (int imgFrame = 0; imgFrame < this.getImg().length; imgFrame++) {
                        getImages()[imgFrame] = this.getCraftedDynamicBitmap(imgFrame, DEFAULT_ROTATION, null, null);
                    }
                Log.d(TAG, "Happen-Enemy: Successfully initialized!");
                isInitialized = true;
            }
        } catch (NoDrawableInArrayFound_Exception | ClassCastException | NullPointerException e) {
            Log.d(TAG, "Happen-Enemy: Initialize Failure!");
            e.printStackTrace();
        }
        return isInitialized;
    }

    /** GETTER / SETTER */
    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        HappenEnemy.images = images;
    }

    /** Get reward method for highscore */
    @Override
    public int getReward() {
        return IEnemy.PROPERTIES.HAPPEN.HIGHSCORE_REWARD;
    }
}
