package yourowngame.com.yourowngame.classes.background;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Random;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;
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


    /** TODO
     * This method updates the X-Values of the background & will make it move
     * After the first image is completely out of sight, the next one will appear (but of course, the fuck it doenst appear smoothly)
     *
     */
    public abstract void updateBackground(); //if we want to change the speed just use the setter!

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


    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random){
        this.random = random;
    }
}
