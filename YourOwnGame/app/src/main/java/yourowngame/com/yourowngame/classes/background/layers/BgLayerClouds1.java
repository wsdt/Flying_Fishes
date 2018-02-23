package yourowngame.com.yourowngame.classes.background.layers;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.background.BackgroundManager;
import yourowngame.com.yourowngame.gameEngine.GameView;

/**
 * Move the inner class into their respective package (=MovableObjects)
 */

public class BgLayerClouds1 extends Background {
    private ArrayList<Cloud> craftedClouds;
    private static final String TAG = "BgLayerClouds1";


    /**
     * image from the int array which is visible
     *
     * @param backgroundManager
     * @param img
     * @param name
     * @param backgroundSpeed
     */
    public BgLayerClouds1(@NonNull BackgroundManager backgroundManager, int[] img, String name, float backgroundSpeed) {
        super(backgroundManager, img, name, backgroundSpeed);
        this.craftClouds(img); //also sets simultaneously
    }

    public class Cloud {
        public Bitmap cloudImg;
        public int posX;
        public int posY;
        public Cloud (Bitmap cloudImg, int posX, int posY) {
            this.posX = posX;
            this.posY = posY;
            this.cloudImg = cloudImg;
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
            Log.d(TAG, "updateBackground: Tried to move cloud: Y: "+this.getCraftedClouds().get(0).posY+" / X: "+(this.getCraftedClouds().get(0).posX));
            Log.d(TAG, "updateBackground: Tried to update BgLayerClouds1.");
        //}


/*
        //TODO: Now we have getWidth etc. by Gamview.getViewHeight(), etc.
        this.setX(this.getX() - this.getSpeedX());

        System.out.println("Position of BackgroundX is " + this.getX());

        /**TODO
         * if currentBackground image is over getWidth(), load the next Image
         * @getDisplay() returns the active drawable (in the first case, bglayer1_clouds_1)
         *
         * *
        if(this.getX() < -4000){
            this.setActiveDrawable(this.getActiveDrawable()+1);
            this.setX(100);
        }

        /** If imageCounter equals the size of the Background its int-array (number of images the obj holds), the counter will start at 0 again
         * eq -> the image bglayer1_clouds_1 will appear again. *
        if(this.getLengthOfBackground() == this.getActiveDrawable()){
            this.setActiveDrawable(0);
        }*/
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

    public void craftClouds(int[] imgs) {
        Log.d(TAG, "craftClouds: Trying to craft clouds.");
        for (int img : imgs) {
            this.getCraftedClouds().add(new Cloud(BitmapFactory.decodeResource(this.getGameView().getResources(), img), (int) this.getBackgroundManager().getGameViewWidth(), this.getRandomYforSkyElements())); //set in advance so not steadily changed (y e.g.)
        } //clouds now also already set! (no need to call setter itself)
    }
}
