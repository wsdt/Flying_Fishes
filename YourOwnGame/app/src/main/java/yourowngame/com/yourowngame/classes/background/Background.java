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

public abstract class Background {
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
    public abstract void updateBackground(int backgroundSpeed);

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
