package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import yourowngame.com.yourowngame.R;

/**
 *  Superclass for other GameObjects
 *
 *  Some obj's do not move, so speedX/Y is at standard 0.
 *
 */

public abstract class GameObject /*extends Mapper*/ {
    private static final String TAG = "GameObject";
    private double posX, posY, speedX, speedY;
    private String name;
    private int img;

    //add, add, add

    public GameObject(double posX, double posY, double speedX, double speedY, int img, @Nullable String name){
        //super(activity);
        this.setPosX(posX);
        this.setPosY(posY);
        this.setSpeedX(speedX);
        this.setSpeedY(speedY);
        this.setName(name);
        this.setImg(img);

        //no default declaration for speed necessary, because no constructor for GameObject without speedX/Y available
    }

    /* @update:
     *
     * had some troubles with this method, but in the end i got an arrow to the knee.
     *
     * nah thought that cohesion might be a problem, because update() method is kind a player.class-like.
     * But all Objects should move in their own way, only the player.class relies on user interaction
     *
     * but all fine!
     *
     */

    //not abstract anymore because now we have the possibility of all possible updateCombinations and do not have to distinguish between player, enemy etc. (if we want to change it, we can override it manually)
    public void update(@Nullable Boolean goUp, @Nullable Boolean goForward) {
        /* ######################################## HOW TO USE ###############################################
         * Uses speedY and speedX of object (goUp==true --> goUp | goUp==false --> goDown | goForward==true --> goForward | goForward==false --> goBack)
         * (goUp == null --> IGNORING | goForward == null --> IGNORING)*/



        if (goForward == null && goUp == null) {
            Log.i(TAG, "update: Called update-method without a valid Boolean param!");
        } else {
            //Update Y
            if (goUp != null) {
                if (goUp) {
                    this.setPosY(this.getPosY() + this.getSpeedY()); //if true go up
                } else {
                    this.setPosY(this.getPosY() - this.getSpeedY());
                } //if false go down
            } else {
                Log.i(TAG, "updateY: Ignoring goUp. Because parameter null.");
            }

            //Update X
            if (goForward != null) {
                if (goForward) {
                    this.setPosX(this.getPosX() + this.getSpeedX()); //if true go forward
                } else {
                    this.setPosX(this.getPosX() - this.getSpeedX());
                } //if false go back
            } else {
                Log.i(TAG, "updateX: Ignoring goForward. Because parameter null.");
            }
        }
    }

    //GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public double getPosX() { return posX; }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

}
