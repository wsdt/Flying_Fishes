package yourowngame.com.yourowngame.classes.background;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;

import java.util.Random;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.gameEngine.GameView;

/**
 * Created on 18.02.2018.
 *
 * Class for creating Backgrounds, only data!
 *
 * TODO exception handling
 */

public abstract class Background {
    private static final String TAG = "Background";
    private int[] img;
    private String name;
    private float backgroundSpeedX = 0;
    private BackgroundManager backgroundManager; //contains also GameView!!
    private Random random;

    public Background(@NonNull BackgroundManager backgroundManager, int[] img, String name, float backgroundSpeed) {
        Log.d(TAG, "getBackgroundInstance: Created new instance.");
        this.setImg(img);
        this.setName(name);
        this.setBackgroundSpeed(backgroundSpeed);
        this.setBackgroundManager(backgroundManager);
        this.setRandom(new Random()); //for random height/y of clouds etc.

    }



    /*public Bitmap getCraftedDynamicBitmap(Context activityContext) {
        //maybe later more things (like animations and so on)
        //return BitmapFactory.decodeResource(activityContext.getResources(),
          //      this.getImg()[this.getActiveDrawable()]);
    }*/


    /** TODO
     * This method updates the X-Values of the background & will make it move
     * After the first image is completely out of sight, the next one will appear (but of course, the fuck it doenst appear smoothly)
     *
     */
    public abstract void updateBackground(); //if we want to change the speed just use the setter!

    //returns how many drawables for this background are defined
    public int getLengthOfBackground(){
        return getImg().length;
    }

    public void setBackgroundSpeed(float backgroundSpeedX){
        this.backgroundSpeedX = backgroundSpeedX;
    }

    public float getBackgroundSpeedX() {
        return backgroundSpeedX;
    }

    public int getDisplay(int pos){
        return getImg()[pos];
    }

    public int[] getImg() {
        return img;
    }

    public void setImg(int[] img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**Dummy method */
    public GameView getGameView() {
        return this.getBackgroundManager().getGameView();
    }

    /** image from the int array which is visible*/
    public BackgroundManager getBackgroundManager() {
        return backgroundManager;
    }

    public void setBackgroundManager(BackgroundManager backgroundManager) {
        this.backgroundManager = backgroundManager;
    }

    //Returns a random Y Value for the clouds ( located between 0 and 15% of the GameHeight)
    public int getRandomYforSkyElements() {
        double randomY;
            randomY = Math.random() * GameViewActivity.GAME_HEIGHT * Constants.Background.layer1_clouds.randomYplacementInPercentageCloud; //so clouds will be existing from 0 to 15% of top

        return (int) randomY;
    }

    //Returns a random X Value for the clouds ( located between -50 and 0)
    public int getRandomXforSkyElements() {
        int i = ((new Random()).nextInt(1001)-1000);
        Log.d(TAG, "Lets just check if X is between -50 and 0 or not:" + i);
        return i;
    }


    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random){
        this.random = random;
    }
}
