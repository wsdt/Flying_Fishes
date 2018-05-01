package yourowngame.com.yourowngame.classes.actors.fruits.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.interfaces.IFruit;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

public class Meloon extends Fruit {
    private final String TAG = "Meloon";
    private Meloon meloon = null;

    private static Bitmap[] images;

    private Meloon(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    public Meloon(){}

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        isGone(); //check if isGone, or collected

        this.setPosX(this.getPosX() - this.getSpeedX());

        Log.d(TAG, "PosX = " + this.getPosX());
    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) {
        canvas.drawBitmap(images[0], (int) getPosX(), (int) getPosY(), null);
    }

    @Deprecated
    public Meloon createMeloon(){
        Meloon meloon = new Meloon((RandomMgr.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 100)),
                RandomMgr.getRandomInt(0, GameViewActivity.GAME_HEIGHT + 100),
                RandomMgr.getRandomFloat(SPEED_X_MIN, SPEED_X_MAX),
                RandomMgr.getRandomFloat(SPEED_Y_MIN, SPEED_Y_MAX),

                new int[] {R.drawable.meloons_32, R.drawable.meloons_32}, (int) DEFAULT_ROTATION, "Meloon");

        meloon.setCurrentBitmap(images[0]);

        return meloon;
    }

    @Override
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        try {
            if (allObjs != null && !isInitialized) {
                if (allObjs[0] instanceof Activity) {
                    Activity activity = (Activity) allObjs[0];
                    setImages(new Bitmap[this.getImg().length]);

                    for (int imgFrame = 0; imgFrame < this.getImg().length; imgFrame++) {
                        getImages()[imgFrame] = this.getCraftedDynamicBitmap(activity, imgFrame, (int) DEFAULT_ROTATION, null, null);
                    }
                } else {
                    Log.d(TAG, "Meloon-Fruit: Initialize Failure!");
                }
                Log.d(TAG, "Meloon-Fruit: Successfully initialized!");
                isInitialized = true;
            }
        } catch (NoDrawableInArrayFound_Exception | ClassCastException | NullPointerException e) {
            Log.d(TAG, "Meloon-Fruit: Initialize Failure!");
            e.printStackTrace();
        }
        return isInitialized;
    }

    @Override
    public boolean cleanup() {
        return true;
    }

    @Override
    public void isGone() {
        if(isCollected) {
            this.setPosX(GameViewActivity.GAME_WIDTH + 100);
        }
    }

    /** Fruit has been collected */
    @Override
    public void collected() {
        //just for now, we need some good advice here
        this.isCollected = true;
    }

    /** Get reward method for highscore */
    @Override
    public int getReward() {
        return IFruit.REWARDS.MELOONS_FRUIT;
    }

    /*******************
     * Getter & Setter *
     *                 *
     *******************/


    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        Meloon.images = images;
    }
}
