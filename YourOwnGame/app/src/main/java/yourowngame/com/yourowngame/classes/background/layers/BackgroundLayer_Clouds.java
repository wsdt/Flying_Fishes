package yourowngame.com.yourowngame.classes.background.layers;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.BackgroundManager;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;

/**
 * First Layer class, which provides clouds in the sky (how wonderful!)
 * Every Layer-class provides an inner class (or an own class, if too big!)
 * which holds the inner-data of the movableObject. The Layer will override
 * the update()-method as well as the drawBackground()-method
 * from the Background.class
 */

public class BackgroundLayer_Clouds extends Background {
    private ArrayList<Cloud> craftedClouds;
    private static final String TAG = "BackgroundLayer_Clouds";


    /**
     * image from the int array which is visible
     *
     * @param backgroundManager
     * @param img
     * @param name
     * @param backgroundSpeed
     */
    public BackgroundLayer_Clouds(@NonNull BackgroundManager backgroundManager, int[] img, String name, float backgroundSpeed) {
        super(backgroundManager, img, name, backgroundSpeed);
        this.craftClouds(img, Constants.Background.layer1_clouds.anzahlClouds); //also sets simultaneously
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
            this.posX = RandomHandler.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 1500);
            this.posY = RandomHandler.getRandomFloat(0, (int) (GameViewActivity.GAME_HEIGHT * Constants.Background.layer1_clouds.randomYplacementInPercentageCloud));
            ;
            this.cloudImg = cloudImg;
            this.randomSpeed = RandomHandler.getRandomFloat(Constants.Background.layer1_clouds.randomCloudSpeedMin, Constants.Background.layer1_clouds.randomCloudSpeedMax);
        }

        //this methods updates the cloud
        public void updateCloud(float speed) {
            this.posX -= (speed);
            if (this.posX < -100) //-100 is on every screen outside of visible area
                posX = GameViewActivity.GAME_WIDTH + 100;
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
            Log.d(TAG, "updateBackground: Tried to move cloud: Y: " + cloud.posY + " / X: " + (cloud.posX));
        }
        Log.d(TAG, "updateBackground: Tried to update BackgroundLayer_Clouds.");
    }

    /**
     * draws the clouds
     */
    @Override
    public void drawBackground(Canvas canvas) {
        for (Cloud cloud : this.getCraftedClouds())
            canvas.drawBitmap(cloud.cloudImg, cloud.posX, cloud.posY, null);
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
    public void craftClouds(int[] imgs, int numberOfClouds) {
        Log.d(TAG, "craftClouds: Trying to craft clouds.");
        for (int i = 0; i < numberOfClouds; i++) {
            //position of X & Y now set in the Constructor (for easier reading)
            this.getCraftedClouds().add(new Cloud(BitmapFactory.decodeResource(this.getGameView().getResources(), imgs[RandomHandler.getRandomInt(0,imgs.length-1)])));
        }
    }

}