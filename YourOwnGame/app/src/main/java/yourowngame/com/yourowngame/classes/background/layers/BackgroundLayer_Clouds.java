package yourowngame.com.yourowngame.classes.background.layers;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.BackgroundManager;
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
        this.craftClouds(img, 30); //also sets simultaneously
    }

    public class Cloud {
        public Bitmap cloudImg;
        public int posX;
        public int posY;

        public Cloud(Bitmap cloudImg, int posX, int posY) {
            this.posX = posX;
            this.posY = posY;
            this.cloudImg = cloudImg;
        }

        //this methods updates the cloud
        public void updateCloud(int speed) {
            for (Cloud c : getCraftedClouds())
                c.posX += speed;
        }

    }

    //TODO: doDraw() [surfaceLayer] instead of onDraw() | redraw()

    @Override
    public void updateBackground() { //clouds crafted before, so this is not also in this loop!
        //TODO: Calculate here position for every cloud seperately
        //currently only one cloud! (todo: make foreach etc.)
        //currently we assume that at least and maximum one cloud is given!
        //Canvas currentCanvas = this.getBackgroundManager().getGameView().getCurrentCanvas();
        //if (currentCanvas != null) {
            /*this.getCraftedClouds().get(0).posX -= this.getBackgroundSpeedX();
            currentCanvas.drawBitmap(this.getCraftedClouds().get(0).cloudImg,
                    (float) this.getCraftedClouds().get(0).posX,
                    (float) this.getCraftedClouds().get(0).posY, null);*/
            /*currentCanvas.drawBitmap(this.getCraftedClouds().get(0).cloudImg,
                    (float) 100,
                    (float) 100, null);*/
        this.getCraftedClouds().get(0).posX -= this.getBackgroundSpeedX();
        Log.d(TAG, "updateBackground: Tried to move cloud: Y: " + this.getCraftedClouds().get(0).posY + " / X: " + (this.getCraftedClouds().get(0).posX));
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