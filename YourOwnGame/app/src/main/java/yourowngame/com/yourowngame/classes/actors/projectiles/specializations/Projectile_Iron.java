package yourowngame.com.yourowngame.classes.actors.projectiles.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.actors.projectiles.Projectile;

public class Projectile_Iron extends Projectile {
    private static final String TAG = "IronProjectile";
    private static Bitmap[] images;

    /**
     * Iron projectile constants ++++++++++++++++++++++++++
     */
    private static final int[] IMAGE_FRAMES = new int[]{R.drawable.color_player_bullet};
    private static final short SHOOT_FREQUENCY = 5;

    public Projectile_Iron(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    public Projectile_Iron(@NonNull Activity activity, double speedX, double speedY) {
        super(activity, 0, 0, speedX, speedY);
    }


    @Override
    public void draw() {
        this.setCurrentBitmap(getImages()[((int) this.getLoopCount() % IMAGE_FRAMES.length)]);
        this.getCanvas().drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }

    @Override
    public void update() {
        super.update();
        this.setPosX(getPosX() + getSpeedX());
    }

    @Override
    public void initialize() {
        try {
            if (!this.isInitialized()) {
                setImages(new Bitmap[IMAGE_FRAMES.length]);

                for (int imgFrame = 0; imgFrame < IMAGE_FRAMES.length; imgFrame++) {
                    getImages()[imgFrame] = getCraftedDynamicBitmap(this.getActivity(), IMAGE_FRAMES[imgFrame], ROTATION_DEFAULT, null, null);
                }
                this.setCurrentBitmap(getImages()[0]);

                Log.d(TAG, "Initialize: Successfully initialized!");
                this.setInitialized(true);
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "Initialize: Initialize Failure!");
            e.printStackTrace();
        }
    }

    @Override
    public short getShootFrequency() {
        return SHOOT_FREQUENCY;
    }

    //GETTER/SETTER ----------------------------------------
    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        Projectile_Iron.images = images;
    }
}
