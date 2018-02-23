package yourowngame.com.yourowngame.classes.background.layers;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.BackgroundManager;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.gameEngine.GameView;

/**
 *  First Layer class, which provides clouds in the sky (how wonderful!)
 *
 *
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

    public class Cloud {
        public Bitmap cloudImg;
        public float posX;
        public float posY;

        public Cloud(Bitmap cloudImg, float posX, float posY) {
            this.posX = posX;
            this.posY = posY;
            this.cloudImg = cloudImg;
        }

        //this methods updates the cloud
        public void updateCloud(float speed) {
            for (Cloud c : getCraftedClouds())
                c.posX += (speed); // divided by 10, because int values mostly relatively fast
        }

    }

    //TODO: doDraw() [surfaceLayer] instead of onDraw() | redraw()

    @Override
    public void updateBackground() { //clouds crafted before, so this is not also in this loop!
        //Calculate here position for every cloud seperately
        for (Cloud cloud : this.getCraftedClouds()) {
            if (GameViewActivity.GAME_WIDTH < cloud.posX) {
                //if outside screen spawn on the start (reset x)
                cloud.posY = this.getRandomYforSkyElements(); //also reset y (looks more natural)
                cloud.posX = this.getRandomXforSkyElements();
                Log.d(TAG, "updateBackground: Resetted cloud.");
            }
            cloud.posX -= this.getBackgroundSpeedX();
            Log.d(TAG, "updateBackground: Tried to move cloud: Y: " + cloud.posY + " / X: " + (cloud.posX));
        }
        Log.d(TAG, "updateBackground: Tried to update BackgroundLayer_Clouds.");
        //}
    }


    public ArrayList<Cloud> getCraftedClouds() {
        if (this.craftedClouds == null) {
            this.craftedClouds = new ArrayList<>();
        }
        return craftedClouds;
    }

    public void setCraftedClouds(ArrayList<Cloud> craftedClouds) {
        this.craftedClouds = craftedClouds;
    }

    public void craftClouds(int[] imgs, int numberOfClouds) {
        Log.d(TAG, "craftClouds: Trying to craft clouds.");
        for(int i = 0; i < numberOfClouds; i++) {
            for (int img : imgs) {                                                                                      //guess its cooler if they spawn outside the map?
                this.getCraftedClouds().add(new Cloud(BitmapFactory.decodeResource(this.getGameView().getResources(), img), this.getRandomXforSkyElements(), this.getRandomYforSkyElements()));
            } //clouds now also already set! (no need to call setter itself)
        }
    }

}