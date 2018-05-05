package yourowngame.com.yourowngame.classes.actors.fruits.specializations;

import android.app.Activity;
import android.content.Context;
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
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

public class Meloon extends Fruit implements IFruit.MELOON_FRUIT_PROPERTIES {
    private final String TAG = "Meloon";
    private Meloon meloon = null;

    private static Bitmap[] images;

    public Meloon(@NonNull Context context, double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(context, posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    /**Creates random fruit*/
    public Meloon(@NonNull Context context) {
        super(context); //also call super constr! (initializing)

        this.setPosX(RandomMgr.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + ADDITIONAL_GAME_WIDTH));
        this.setPosY(RandomMgr.getRandomInt(0, GameViewActivity.GAME_HEIGHT));
        this.setSpeedX(SPEED_X);
        this.setSpeedY(SPEED_Y);
        this.setRotationDegree(DEFAULT_ROTATION);
        this.setName("Meloon");

        this.setCurrentBitmap(Meloon.getImages()[0]);
    }


    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        isGone(); //check if isGone, or collected
        this.setPosX(this.getPosX() - this.getSpeedX());
    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) {
        canvas.drawBitmap(images[0], (int) getPosX(), (int) getPosY(), null);
        Log.d(TAG,"POS X MELOON  ==  " + this.getPosX());
    }

    @Override
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        this.setImg(IMAGE_FRAMES);

        try {
            if (!isInitialized) {
                    setImages(new Bitmap[this.getImg().length]);

                    for (int imgFrame = 0; imgFrame < this.getImg().length; imgFrame++) {
                        getImages()[imgFrame] = this.getCraftedDynamicBitmap(imgFrame, (int) DEFAULT_ROTATION, null, null);
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
        isGone(); //currently just do like the meloon is away
        return true;
    }

    @Override
    public void isGone() {
        if(this.getPosX() < 0 || this.isCollected()) {
            this.setPosX(GameViewActivity.GAME_WIDTH + IFruit.DEFAULT_FRUIT_PROPERTIES.OFF_TIME);
            this.setPosY(RandomMgr.getRandomFloat(100, GameViewActivity.GAME_HEIGHT-100));
            this.setCollected(false);
        }
    }

    /** Fruit has been collected */
    @Override
    public void collected() {
        //just for now, we need some good advice here
        this.setCollected(true);
    }

    /** Get reward method for highscore */
    @Override
    public int getReward() {
        return IFruit.MELOON_FRUIT_PROPERTIES.HIGHSCORE_REWARD;
    }

    /******************Getter & Setter ******************/
    public static Bitmap[] getImages() {
        return images;
    }
    public static void setImages(Bitmap[] images) {
        Meloon.images = images;
    }
}
