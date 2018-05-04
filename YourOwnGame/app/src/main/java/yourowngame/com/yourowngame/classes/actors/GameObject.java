package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.gamelevels.interfaces.IDrawAble;
import yourowngame.com.yourowngame.classes.gamelevels.interfaces.IUpdateAble;
import yourowngame.com.yourowngame.gameEngine.interfaces.Initializer;


public abstract class GameObject implements Initializer, IUpdateAble, IDrawAble {
    private static final String TAG = "GameObject";
    private Context context;
    private double posX, posY, speedX, speedY;
    private int rotationDegree; //rotation for simulating flying down/up
    private String name;
    private int[] img; //must not be static (overwriding)
    private Bitmap currentBitmap; //just a reference for other classes, so they know which bitmap is active (for collision calculation etc.)
    private int heightOfBitmap, widthOfBitmap;

    protected boolean isInitialized = false; //should be only set to true in initialize() --> no getter setter because only class itself should have access


    public GameObject(@NonNull Context context, double posX, double posY, double speedX, double speedY, @NonNull int[] img, int rotationDegree, @Nullable String name) {
        this.setContext(context);

        this.setPosX(posX);
        this.setPosY(posY);
        this.setSpeedX(speedX);
        this.setSpeedY(speedY);
        this.setRotationDegree(rotationDegree);
        this.setName(name);
        this.setImg(img);

        this.initialize();
    }

    /** Mostly used for creating random enemies/fruits etc. (they have to call super();) to initialize them!*/
    public GameObject(@NonNull Context context){
        this.setContext(context);
        this.initialize();
    }


    /**
     * getCraftedDynamicBitmap:
     *
     * Creates a dynamic bitmap from a drawable res
     *
     * @param imgFrame: index of int-array (set/getImg())
     * @param rotationDegrees: how much should be image tilted or rotated? (in degrees) / if null then image won't be rotated
     * @param widthInPercent: reduce/enlarge width / if this param OR scaleHeight is null, both values get ignored! Use . as comma ;) --> Values MUST be higher than 0 and should not be higher than 1! (quality)
     * @param heightInPercent: same as scaleWidth. */
    public Bitmap getCraftedDynamicBitmap(int imgFrame, @Nullable Integer rotationDegrees, @Nullable Float widthInPercent, @Nullable Float heightInPercent) throws NoDrawableInArrayFound_Exception {

        Log.d(TAG, "getCraftedBitmaps: Trying to craft bitmaps.");
        if (this.getImg().length <= imgFrame && this.getImg().length >= 1) {
               Log.e(TAG, "getCraftedDynamicBitmap: IndexOutOfBounds, could not determine correct drawable for animation. Returning drawable at index 0! Provided imgFrame: "+imgFrame);
               imgFrame = 0;
        } else if (this.getImg().length <= 0) { throw new NoDrawableInArrayFound_Exception("getCraftedDynamicBitmap: FATAL EXCEPTION->Integer array (getImg()) has no content! Could not return bitmap."); }
        //not else (because despite normal if method should continue)
        Bitmap targetImg = BitmapFactory.decodeResource(this.getContext().getResources(), this.getImg()[imgFrame]);
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



    /**GETTER/SETTER SHIT ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  * */

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

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
