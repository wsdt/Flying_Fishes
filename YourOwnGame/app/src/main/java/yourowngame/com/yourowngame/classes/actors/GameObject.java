package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.DrawableObj;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;


public abstract class GameObject extends DrawableObj {
    private static final String TAG = "GameObject";
    /**
     * All gameObjects (also Player with default X) have these params (but not Backgrounds e.g. so
     * it wouldn't be suitable for the DrawableObj.
     */
    private double posX, posY, speedX, speedY;
    private int rotationDegree = ROTATION_DEFAULT; //rotation for simulating flying down/up [can be changed at runtime] --> 0 as default
    private Bitmap currentBitmap; //must not be static, is the current index for img-array
    private int heightOfBitmap, widthOfBitmap;

    /* GameObj constants ++++++++++++++++++++++++++++++++++++++++++++*/
    /**
     * Rotation of player flying up (simulating by tilting image)
     */
    protected static final int ROTATION_UP = 5;
    /**
     * Rotation of player flying down (simulating by tilting img)
     */
    protected static final int ROTATION_DOWN = -5;
    /**
     * Default rotation
     */
    protected static final int ROTATION_DEFAULT = 0;

    public GameObject(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity);

        this.setPosX(posX);
        this.setPosY(posY);
        this.setSpeedX(speedX);
        this.setSpeedY(speedY);

        //No need to reset pos or speed etc. as we are setting it here.
    }

    /**
     * Mostly used for creating random enemies/fruits etc. (they have to call super();) to initialize them!
     */
    public GameObject(@NonNull Activity activity) {
        super(activity);
        //Set/Reset default speed and position
        this.resetPos();
        this.resetSpeed();
    }

    @Override @CallSuper
    public void update() {
        // Reset pos/speed etc. when not visible anymore
        if (this.isNotVisible()) {
            this.resetPos(); //only resetPos as enemies etc. would get exponentionally fast
        }
    }

    /**
     * Is GameObj. visible or has it left the screen via Y or X?
     */
    public boolean isNotVisible() {
        return ((this.getPosX() <= (0-this.getWidthOfBitmap())) || /* has left on left side */
                (this.getPosX() >= (DrawableSurfaces.getDrawWidth()+this.getWidthOfBitmap())) || /* has left on right side */
                (this.getPosY() <= (0-this.getHeightOfBitmap())) || /* has left on bottom side */
                (this.getPosY() >= (DrawableSurfaces.getDrawHeight()+this.getHeightOfBitmap()))) /* has left on top side*/;
    }

    /**
     * Resets position of gameObj to the start position. Used e.g. in cleanUp();
     * Here and not in DrawableObj, bc. Backgrounds cannot move as a whole.
     */
    public abstract void resetPos();

    /**
     * Resets speed of gameObj
     */
    public abstract void resetSpeed();

    @Override
    @CallSuper //Call super to always do resetPos();
    public boolean cleanup() {
        this.resetPos();
        this.resetSpeed();
        return true;
    }

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
