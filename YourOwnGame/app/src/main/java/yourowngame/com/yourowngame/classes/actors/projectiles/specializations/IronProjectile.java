package yourowngame.com.yourowngame.classes.actors.projectiles.specializations;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.projectiles.Projectile;
import yourowngame.com.yourowngame.classes.actors.projectiles.interfaces.IProjectile;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;

public class IronProjectile extends Projectile implements IProjectile.PROPERTIES.IRON {
    private static final String TAG = "IronProjectile";
    private static Bitmap[] images;

    public IronProjectile(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(activity, posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    @Override
    public void update() {
        this.setPosX(getPosX() + getSpeedX());
    }

    @Override
    public void draw() {
        this.setCurrentBitmap(getImages()[((int) this.getLoopCount() % this.getImg().length)]);
        this.getCanvas().drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }

    @Override
    public void initialize() {
        //we really need to change the initialize, Object params, instanceOf..
        this.setImg(IMAGE_FRAMES); //current design (bad!)

        try {
            if (!this.isInitialized()) {
                setImages(new Bitmap[this.getImg().length]);

                for (int imgFrame = 0; imgFrame < this.getImg().length; imgFrame++) {
                    getImages()[imgFrame] = this.getCraftedDynamicBitmap(imgFrame, DEFAULT_ROTATION, null, null);
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

    //GETTER/SETTER ----------------------------------------
    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        IronProjectile.images = images;
    }
}
