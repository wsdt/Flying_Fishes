package yourowngame.com.yourowngame.classes.background;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Log;

import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.gameEngine.GameView;

/**
 * Created on 18.02.2018.
 *
 * Class for creating Backgrounds, only data!
 *
 * TODO exception handling
 */

public class Background {
    private static final String TAG = "Background";
    private GameView gameView;
    private int[] img;
    private String name;
    private int speedX = 0;
    private double x;
    private double y;
    private int activeDrawable = 0; /** image from the int array which is visible*/

    public Background(GameView gameView, int[] img, String name, int backgroundSpeed) {
        Log.d(TAG, "getBackgroundInstance: Created new instance.");
        this.setImg(img);
        this.setName(name);
        this.setBackgroundSpeed(backgroundSpeed);
    }

    public Bitmap getCraftedBitmap(Context activityContext) {
        //maybe later more things (like animations and so on)
        return BitmapFactory.decodeResource(activityContext.getResources(),
                this.getImg()[this.getActiveDrawable()]);
    }


    /** TODO
     * This method updates the X-Values of the background & will make it move
     * After the first image is completely out of sight, the next one will appear (but of course, the fuck it doenst appear smoothly)
     *
     */
    public void updateBackground(@Nullable Integer backgroundSpeed){
        //if I understood it right we can replace imageCounter here with activeDrawable

        //configure speed [now in constructor]
        //this.setBackgroundSpeed((backgroundSpeed==null) ? Constants.Background.defaultBgSpeed : backgroundSpeed); //if given use it, otherwise default value

        //sets the activeDrawable to position 0 (int array) --> already 0 at instantiation
        //this.setActiveDrawable(this.getDisplay(this.getActiveDrawable()));

        //TODO: Now we have getWidth etc. by Gamview.getViewHeight(), etc.
        this.setX(this.getX() - this.getSpeedX());

        System.out.println("Position of BackgroundX is " + this.getX());

        /**TODO
         * if currentBackground image is over getWidth(), load the next Image
         * @getDisplay() returns the active drawable (in the first case, bglayer1_clouds_1)
         *
         * */
        /**TODO */
        if(this.getX() < -4000){
            this.setActiveDrawable(this.getActiveDrawable()+1);
            this.setX(100); /** <- thats the shithole, here we should draw the next image, x should be again at 0, but just draw the next image */
        }

        /** If imageCounter equals the size of the Background its int-array (number of images the obj holds), the counter will start at 0 again
         * eq -> the image bglayer1_clouds_1 will appear again. */
        if(this.getLengthOfBackground() == this.getActiveDrawable()){
            this.setActiveDrawable(0);
        }
    }

    //returns how many drawables for this background are defined
    public int getLengthOfBackground(){
        return getImg().length;
    }

    public void setBackgroundSpeed(int speedX){
        this.speedX = speedX;
    }

    public double getX() {
        return x;
    }

    public void setX(double x){
        this.x=x;
    }

    public void setActiveDrawable(int pos){
        activeDrawable = pos;
    }

    public int getActiveDrawable(){
        return activeDrawable;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public int getSpeedX() {
        return speedX;
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

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
}
