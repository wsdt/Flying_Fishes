package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;

/**
 * Superclass for other GameObjects
 * <p>
 * Some obj's do not move, so speedX/Y is at standard 0.
 *
 * * * * PLEASE RED * * *
 *
 * Imagination of player.class
 *
 * The player will spawn at a defined position
 * he first of all will only move up (if screen touched)
 * or fall dawn, if not.
 * if he hits the ground, game over
 * if he collets a bonus, he will get more freedom to move around
 * if he hits a barrier/enemy, he could lose life points or die immediately
 *
 * guess we're save if we keep the implementation at this level,
 * if development increaes, we can easy add other movements!
 *
 * würdest was anderst machen?
 *
 */

public abstract class GameObject /*extends Mapper*/ {
    private static final String TAG = "GameObject";
    private double posX, posY, speedX, speedY;
    private String name;
    private int[] img;
    //private ArrayList<Bitmap> imgCrafted; //[no setter/getter!] used for performance enhancement

    //add, add, add

    public GameObject(double posX, double posY, double speedX, double speedY, int[] img, @Nullable String name) {
        //super(activity);
        this.setPosX(posX);
        this.setPosY(posY);
        this.setSpeedX(speedX);
        this.setSpeedY(speedY);
        this.setName(name);
        this.setImg(img);

        //no default declaration for speed necessary, because no constructor for GameObject without speedX/Y available
    }


    /**
     * TODO update()
     *
     * guess we got a design-problem here. Method works just fine, but only for Player.class
     * All other GameObjects() got nothing to do with this method.
     *
     * Enemys will move towards the player due to the posX/Y values of the player
     * Rewards will stay static, waiting to be collected (or maybe fly around the surface)
     * Barriers will always be static, waiting for collision
     * ...
     *
     * might implement a interface Updateable with method update()?
     *
     * I would place the logic of the current update method within the body method of the players class.
     * because the player is the only one to receive this (logically!)
     *
     */

    public void update(@Nullable Boolean goUp, @Nullable Boolean goForward) {
        /* ######################################## HOW TO USE ###############################################
         * Uses speedY and speedX of object (goUp==true --> goUp | goUp==false --> goDown | goForward==true --> goForward | goForward==false --> goBack)
         * (goUp == null --> IGNORING | goForward == null --> IGNORING)*/


        if (goForward == null && goUp == null) {
            Log.i(TAG, "update: Called update-method without a valid Boolean param!");
        } else {
            // Update Y
            // replaced x/y game starts at 0|0 which is the top left corner of the view
            // if player "jumps" the y value increases (cause y grows towards the bottom of the view)
            if (goUp != null) {
                if (goUp) {
                    this.setPosY(this.getPosY() - this.getSpeedY() * Constants.Actors.GameObject.MOVE_UP_MULTIPLIER);
                } else {
                    this.setPosY(this.getPosY() + this.getSpeedY());
                } //if false go down
            } else {
                Log.i(TAG, "updateY: Ignoring goUp. Because parameter null.");
            }

            //Update X
            if (goForward != null) {
                if (goForward) {
                    this.setPosX(this.getPosX() + this.getSpeedX()); //if true go forward
                } else {
                  // should not go back, only if bonus of getting forward is no longer active
                  //  this.setPosX(this.getPosX() - this.getSpeedX());
                } //if false go back
            } else {
                Log.i(TAG, "updateX: Ignoring goForward. Because parameter null.");
            }
        }
    }

    /* only merges bitmaps
    public Bitmap getAnimatedImg(Context context) {
        if (this.animatedImg == null) {
            ArrayList<Bitmap> bitmaps = new ArrayList<>();
            for (int drawableFrame : this.getImg()) {
                bitmaps.add(BitmapFactory.decodeResource(context.getResources(), drawableFrame));
            }
            this.animatedImg = bitmaps.get(Math.abs((new AtomicInteger()).get() % bitmaps.size()));
        }
        return this.animatedImg; //return previously factored bitmap [no calculating necessary]
    }*/

    //GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public double getPosX() {
        return posX;
    }

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

    public int[] getImg() {
        return img;
    }

    public void setImg(int[] img) {
        this.img = img;
    }

    /** getCraftedBitmap:
     * @param imgFrame: index of int-array (set/getImg())
     * @param rotationDegrees: how much should be image tilted or rotated? (in degrees) / if null then image won't be rotated
     * @param widthInPercent: reduce/enlarge width / if this param OR scaleHeight is null, both values get ignored! Use . as comma ;) --> Values MUST be higher than 0 and should not be higher than 1! (quality)
     * @param heightInPercent: same as scaleWidth. */
    public Bitmap getCraftedBitmap(@NonNull Context context, int imgFrame, @Nullable Float rotationDegrees, @Nullable Float widthInPercent, @Nullable Float heightInPercent) throws NoDrawableInArrayFound_Exception {
        Log.d(TAG, "getCraftedBitmaps: Trying to craft bitmaps.");
        if (this.getImg().length <= imgFrame && this.getImg().length >= 1) {
               Log.e(TAG, "getCraftedBitmap: IndexOutOfBounds, could not determine correct drawable for animation. Returning drawable at index 0!");
               imgFrame = 0;
        } else if (this.getImg().length <= 0) { throw new NoDrawableInArrayFound_Exception("getCraftedBitmap: FATAL EXCEPTION->Integer array (getImg()) has no content! Could not return bitmap."); }
        //not else (because despite normal if method should continue)
        Bitmap targetImg = BitmapFactory.decodeResource(context.getResources(), this.getImg()[imgFrame]);
        if (widthInPercent != null && heightInPercent != null) { //must be before rotationDegrees-If
            targetImg = Bitmap.createScaledBitmap(targetImg, (int) (targetImg.getWidth()*widthInPercent), (int) (targetImg.getHeight()*heightInPercent), true);
        } //not else if!
        if (rotationDegrees != null) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotationDegrees);
            targetImg = Bitmap.createBitmap(targetImg, 0, 0, targetImg.getWidth(), targetImg.getHeight(), matrix, true);
        } //not else if (we want to make several combinations)
        return targetImg;
    }

}
