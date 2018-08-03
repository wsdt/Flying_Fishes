package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.DrawableObj;
import yourowngame.com.yourowngame.classes.actors.interfaces.IGameObject;


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
     * GETTER/SETTER ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  *
     */

    protected int getRotationDegree() {
        return rotationDegree;
    }

    public void setRotationDegree(int rotationDegree) {
        this.rotationDegree = rotationDegree;
    }

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
