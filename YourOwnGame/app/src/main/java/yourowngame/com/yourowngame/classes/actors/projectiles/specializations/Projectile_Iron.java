package yourowngame.com.yourowngame.classes.actors.projectiles.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.classes.actors.projectiles.Projectile;
import yourowngame.com.yourowngame.classes.actors.projectiles.interfaces.IProjectile;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;

public class Projectile_Iron extends Projectile implements IProjectile.PROPERTIES.IRON {
    private static final String TAG = "IronProjectile";
    private static Bitmap[] images;

    public Projectile_Iron(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    public Projectile_Iron(@NonNull Activity activity, double speedX, double speedY) {
        super(activity, 0, 0, speedX, speedY);
    }

    @Override
    public void update() {
        this.setPosX(getPosX() + getSpeedX());
    }

    @Override
    public void draw() {
        this.setCurrentBitmap(getImages()[((int) this.getLoopCount() % IMAGE_FRAMES.length)]);
        this.getCanvas().drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }

    @Override
    public void initialize() {
        try {
            if (!this.isInitialized()) {
                setImages(new Bitmap[IMAGE_FRAMES.length]);

                for (int imgFrame = 0; imgFrame < IMAGE_FRAMES.length; imgFrame++) {
                    getImages()[imgFrame] = this.getCraftedDynamicBitmap(IMAGE_FRAMES, imgFrame, DEFAULT_ROTATION, null, null);
                }
                this.setCurrentBitmap(getImages()[0]);

                Log.d(TAG, "Initialize: Successfully initialized!");
                this.setInitialized(true);
            }
        } catch (NoDrawableInArrayFound_Exception | NullPointerException e) {
            Log.d(TAG, "Initialize: Initialize Failure!");
            e.printStackTrace();
        }
    }

    @Override
    public short getShortFrequency() {
        return IProjectile.PROPERTIES.IRON.SHOOT_FREQUENCY;
    }

    //GETTER/SETTER ----------------------------------------
    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        Projectile_Iron.images = images;
    }
}
