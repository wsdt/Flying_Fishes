package yourowngame.com.yourowngame.classes.background.layers;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.annotations.Bug;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.layers.interfaces.IBackgroundLayer_Clouds;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

/**
 * First Layer class, which provides clouds in the sky (how wonderful!)
 * Every Layer-class provides an inner class (or an own class, if too big!)
 * which holds the inner-data of the movableObject. The Layer will override
 * the update()-method as well as the drawBackground()-method
 * from the Background.class
 */

public class BackgroundLayer_Clouds extends Background implements IBackgroundLayer_Clouds {
    private ArrayList<Cloud> craftedClouds;
    private static final String TAG = "BackgroundLayer_Clouds";


    /**
     * image from the int array which is visible
     *
     * @param img
     * @param name
     * @param backgroundSpeed
     */
    public BackgroundLayer_Clouds(@NonNull Context context, int[] img, String name, float backgroundSpeed) {
        super(context, img, name, backgroundSpeed);
        this.initialize(); //now at least one
    }

    /**
     * inner class (the movable-Object on the screen)
     */
    public class Cloud {
        protected Bitmap cloudImg;
        protected float posX;
        protected float posY;
        public float randomSpeed;

        public Cloud(Bitmap cloudImg) {
            this.posX = RandomMgr.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 1500);
            this.posY = RandomMgr.getRandomFloat(0, (int) (GameViewActivity.GAME_HEIGHT * CLOUD_RANDOM_Y_PLACEMENT_IN_PERCENTAGE));
            Log.d(TAG, "Width and Height of GVA " +GameViewActivity.GAME_HEIGHT + " " + GameViewActivity.GAME_WIDTH);

            this.cloudImg = cloudImg;
            this.randomSpeed = RandomMgr.getRandomFloat(CLOUD_RANDOM_SPEED_MIN, CLOUD_RANDOM_SPEED_MAX);
        }

        //this methods updates the cloud
        public void updateCloud(float speed) {
            this.posX -= (speed);
            if (this.posX < -100) //-100 is on every screen outside of visible area
                posX = GameViewActivity.GAME_WIDTH + 100;
            Log.d(TAG, "Position of Cloud = " + this.posY);
        }
    }

    /**
     * calls the update() method from the Cloud.class
     * <p>
     * i removed some thing because it seems we're not using the methods for the right way
     * XXX -> update()         should only move the cloud
     * XXX -> drawBackground() should only draw it
     */
    @Override
    public void updateBackground() {
        for (Cloud cloud : this.getCraftedClouds()) {
            //update cloud
            cloud.updateCloud(cloud.randomSpeed); //Where is the speed defined? couldnt find it!
            //just for logging
            //Log.d(TAG, "updateBackground: Tried to move cloud: Y: " + cloud.posY + " / X: " + (cloud.posX));
        }
        //Log.d(TAG, "updateBackground: Tried to update BackgroundLayer_Clouds.");
    }

    /**
     * draws the clouds
     */
    @Override
    public void drawBackground(Canvas canvas) {
        for (Cloud cloud : this.getCraftedClouds()) {
            canvas.drawBitmap(cloud.cloudImg, cloud.posX, cloud.posY, null);
            //Log.d(TAG, "drawBackground: Tried to draw cloud: "+cloud);
        }
        //Log.d(TAG, "drawBackground: Tried to draw BgCloudLayer clouds.");
    }

    /**
     * Returns the craftedClouds
     */
    public ArrayList<Cloud> getCraftedClouds() {
        if (this.craftedClouds == null) {
            this.craftedClouds = new ArrayList<>();
        }
        return craftedClouds;
    }

    /**
     * Crafts clouds
     *
     * @param imgs           image(s) of the clouds
     * @param numberOfClouds figure of clouds
     */
    @Bug(byDeveloper = "SOLUTION",
    message = "Metrics of Display are vanished!",
    possibleSolution = "Game Metrics are not working anymore, they need to be set at every level!")
    private void craftClouds(int[] imgs, int numberOfClouds) {
        Log.d(TAG, "craftClouds: Trying to craft clouds.");
        for (int i = 0; i < numberOfClouds; i++) {

            this.getCraftedClouds().add(new Cloud(BitmapFactory.decodeResource(this.getContext().getResources(), imgs[RandomMgr.getRandomInt(0, imgs.length - 1)])));
            Log.d(TAG, "GameView Height = ");
            Log.d(TAG, "craftClouds: Added cloud no. "+i);
        }
    }

    /** allObjs == NULL/no param to provide */
    @Override
    @SafeVarargs
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        //TODO: Maybe make amount clouds configurable by constr or similar?
        this.craftClouds(this.getImg(), AMOUNT_CLOUDS); //also sets simultaneously
        return true;
    }

    @Override
    public boolean cleanup() {
        return true;
    }

}