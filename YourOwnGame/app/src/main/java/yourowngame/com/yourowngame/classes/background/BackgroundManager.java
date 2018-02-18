package yourowngame.com.yourowngame.classes.background;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.configuration.Constants;

/**
 * Created on 18.02.2018.
 *
 * Provides a BackgroundManager
 *
 * Singleton, always just one BackgroundManager
 *
 * TODO: exception handling, improving performance,
 *
 */

public class BackgroundManager {

    private static final String Background_TAG = "Background";
    private Background backgroundLevelOne;  /** background for Level 1 */
    private Background backgroundLevelTwo;  /** background for Level 2*/
    private Background currentBackground;   /** the current background to be shown */
    private int imageCounter = 0;

    //Singleton
    private static BackgroundManager INSTANCE = new BackgroundManager();
    public static BackgroundManager getInstance(){
        return INSTANCE;
    }

    //private access, due to singleton
    //create backgrounds here
    private BackgroundManager(){
        backgroundLevelOne = new Background(new int[] {R.drawable.clouds_1, R.drawable.clouds_2, R.drawable.clouds_3}, "Heaven");
      //backgroundLevelTwo ...
    }

    // by creation, 1 = level 1, 2 = level 2 etc.
    public void setBackgroundLevel(int i){
        switch(i){
            case 1: currentBackground = backgroundLevelOne; break;
            case 2: //not implemented yet, more backgrounds comming later!
        }
    }

    /** TODO
     * This method updates the X-Values of the background & will make it move
     * After the first image is completely out of sight, the next one will appear (but of course, the fuck it doenst appear smoothly)
     *
     */
    public void updateBackground(){
        //configure speed
        currentBackground.setBackgroundSpeed(10);

        //sets the activeDrawable to position 0 (int array)
        currentBackground.setActiveDrawable(currentBackground.getDisplay(imageCounter));

        //starts at 0, X - speed = decrease, if under 4000 (but why 4000? sure, should be at the getWidth() fuck, BUT I HAVE NO FUCKING GETWIDTH !!!
        currentBackground.setX(currentBackground.getX() - currentBackground.getSpeedX());

        System.out.println("Position of BackgroundX is " + currentBackground.getX());

        /**TODO
         * if currentBackground image is over getWidth(), load the next Image
         * @getDisplay() returns the active drawable (in the first case, clouds_1)
         *
         * */
                                    //and again, needs to be fucking getWidth
        if(currentBackground.getX() < -4000){
            currentBackground.setActiveDrawable(currentBackground.getDisplay(imageCounter++));
            currentBackground.setX(100); /** <- thats the shithole, here we should draw the next image, x should be again at 0, but just draw the next image */
        }

        /** If imageCounter equals the size of the Background its int-array (number of images the obj holds), the counter will start at 0 again
         * eq -> the image clouds_1 will appear again. */
        if(currentBackground.getLengthOfBackground() == imageCounter){
            imageCounter = 0;
        }
    }

    public Background getCurrentBackground(){
        return currentBackground;
    }

    public double getCurrentBackgroundX(){
        return currentBackground.getX();
    }

    public double getCurrentBackgroundY(){
        return currentBackground.getY();
    }

}
