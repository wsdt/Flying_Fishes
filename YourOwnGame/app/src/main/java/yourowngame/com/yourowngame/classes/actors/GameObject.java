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
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;


public abstract class GameObject {
    private static final String TAG = "GameObject";
    private double posX, posY, speedX, speedY;
    private float rotationDegree; //rotation for simulating flying down/up
    private String name;
    private int[] img;
    //private ArrayList<Bitmap> imgCrafted; //[no setter/getter!] used for performance enhancement


    public GameObject(double posX, double posY, double speedX, double speedY, int[] img, float rotationDegree, @Nullable String name) {
        //super(activity);
        this.setPosX(posX);
        this.setPosY(posY);
        this.setSpeedX(speedX);
        this.setSpeedY(speedY);
        this.setRotationDegree(rotationDegree);
        this.setName(name);
        this.setImg(img);

        //no default declaration for speed necessary, because no constructor for GameObject without speedX/Y available
    }
    //Default constructor
    public GameObject(){}

    /**
     * method which its only use is to update the objects x and y axis!
     *
     * @param goUp check if object goes up
     * @param goForward check if object goes down
     */
    public abstract void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward);

    /**
     * @param obj check
     * @return returns whether a collision happened or not!
     */
    public abstract boolean collision(View view, GameObject obj);

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

    /** getCraftedDynamicBitmap:
     *
     * Creates a dynamic bitmap from a drawable res
     *
     * @param imgFrame: index of int-array (set/getImg())
     * @param rotationDegrees: how much should be image tilted or rotated? (in degrees) / if null then image won't be rotated
     * @param widthInPercent: reduce/enlarge width / if this param OR scaleHeight is null, both values get ignored! Use . as comma ;) --> Values MUST be higher than 0 and should not be higher than 1! (quality)
     * @param heightInPercent: same as scaleWidth. */
    public Bitmap getCraftedDynamicBitmap(@NonNull Context context, int imgFrame, @Nullable Float rotationDegrees, @Nullable Float widthInPercent, @Nullable Float heightInPercent) throws NoDrawableInArrayFound_Exception {
        Log.d(TAG, "getCraftedBitmaps: Trying to craft bitmaps.");
        if (this.getImg().length <= imgFrame && this.getImg().length >= 1) {
               Log.e(TAG, "getCraftedDynamicBitmap: IndexOutOfBounds, could not determine correct drawable for animation. Returning drawable at index 0!");
               imgFrame = 0;
        } else if (this.getImg().length <= 0) { throw new NoDrawableInArrayFound_Exception("getCraftedDynamicBitmap: FATAL EXCEPTION->Integer array (getImg()) has no content! Could not return bitmap."); }
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

    /**
     * Creates a simple Bitmap from a Drawable res
     *
     * @param context context
     * @param img image to be drawn
     * @return
     */
    public Bitmap getCraftedBitmap(@NonNull Context context, int img){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), img);

        return bitmap;
    }

    public float getRotationDegree() {
        return rotationDegree;
    }

    public void setRotationDegree(float rotationDegree) {
        this.rotationDegree = rotationDegree;
    }
}
