package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import yourowngame.com.yourowngame.classes.DrawableObj;
import yourowngame.com.yourowngame.classes.actors.interfaces.IGameObject;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;


public abstract class GameObject extends DrawableObj implements IGameObject.PROPERTIES.DEFAULT {
    private static final String TAG = "GameObject";
    /**
     * All gameObjects (also Player with default X) have these params (but not Backgrounds e.g. so
     * it wouldn't be suitable for the DrawableObj.
     */
    private double posX, posY, speedX, speedY;
    private int rotationDegree = DEFAULT_ROTATION; //rotation for simulating flying down/up [can be changed at runtime]
    private Bitmap currentBitmap; //must not be static, is the current index for img-array
    private int heightOfBitmap, widthOfBitmap;

    public GameObject(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity);

        this.setPosX(posX);
        this.setPosY(posY);
        this.setSpeedX(speedX);
        this.setSpeedY(speedY);
    }

    /**
     * Mostly used for creating random enemies/fruits etc. (they have to call super();) to initialize them!
     */
    public GameObject(@NonNull Activity activity) {
        super(activity);
    }

    /**
     * Resets position of gameObj to the start position. Used e.g. in cleanUp();
     * Here and not in DrawableObj, bc. Backgrounds cannot move as a whole.
     */
    public abstract void resetPos();


    /**
     * getCraftedDynamicBitmap:
     * <p>
     * Creates a dynamic bitmap from a drawable res
     *
     * @param imgFrame:        index of int-array (set/getImg())
     * @param rotationDegrees: how much should be image tilted or rotated? (in degrees) / if null then image won't be rotated
     * @param widthInPercent:  reduce/enlarge width / if this param OR scaleHeight is null, both values get ignored! Use . as comma ;) --> Values MUST be higher than 0 and should not be higher than 1! (quality)
     * @param heightInPercent: same as scaleWidth.
     */
    public Bitmap getCraftedDynamicBitmap(@NonNull int[] allImgFrames, int imgFrame, @Nullable Integer rotationDegrees, @Nullable Float widthInPercent, @Nullable Float heightInPercent) throws NoDrawableInArrayFound_Exception {

        Log.d(TAG, "getCraftedBitmaps: Trying to craft bitmaps.");
        if (allImgFrames.length <= imgFrame && allImgFrames.length >= 1) {
            Log.e(TAG, "getCraftedDynamicBitmap: IndexOutOfBounds, could not determine correct drawable for animation. Returning drawable at index 0! Provided imgFrame: " + imgFrame);
            imgFrame = 0;
        } else if (allImgFrames.length <= 0) {
            throw new NoDrawableInArrayFound_Exception("getCraftedDynamicBitmap: FATAL EXCEPTION->Integer array (getImg()) has no content! Could not return bitmap.");
        }
        //not else (because despite normal if method should continue)
        Bitmap targetImg = BitmapFactory.decodeResource(this.getActivity().getResources(), allImgFrames[imgFrame]);
        if ((widthInPercent != null && heightInPercent != null)) { //must be before rotationDegrees-If
            if ((widthInPercent != 1 && heightInPercent != 1)) { //so we also don't scale if factor is 1
                targetImg = Bitmap.createScaledBitmap(targetImg, (int) (targetImg.getWidth() * widthInPercent), (int) (targetImg.getHeight() * heightInPercent), true);
            }

        } //not else if!
        if (rotationDegrees != null) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotationDegrees);
            targetImg = Bitmap.createBitmap(targetImg, 0, 0, targetImg.getWidth(), targetImg.getHeight(), matrix, true);
        } //not else if (we want to make several combinations)

        return targetImg;
    }

    protected int getRotationDegree() {
        return rotationDegree;
    }

    public void setRotationDegree(int rotationDegree) {
        this.rotationDegree = rotationDegree;
    }


    /**
     * GETTER/SETTER SHIT ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  *
     */

    public int getHeightOfBitmap() {
        return heightOfBitmap;
    }

    public void setHeightOfBitmap(int heightOfBitmap) {
        this.heightOfBitmap = heightOfBitmap;
    }

    public int getWidthOfBitmap() {
        return widthOfBitmap;
    }

    public void setWidthOfBitmap(int widthOfBitmap) {
        this.widthOfBitmap = widthOfBitmap;
    }

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

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
    }
}
