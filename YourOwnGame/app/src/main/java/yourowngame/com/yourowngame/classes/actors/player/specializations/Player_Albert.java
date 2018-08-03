package yourowngame.com.yourowngame.classes.actors.player.specializations;


import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashMap;

import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.actors.player.interfaces.IPlayer;

public class Player_Albert extends Player implements IPlayer.PROPERTIES.ALBERT {
    private static final String TAG = "Albert";
    private HashMap<String, Bitmap> images; //must not be static

    public Player_Albert(@NonNull Activity activity, double posX, double posY, double speedX, double speedY) {
        super(activity, posX, posY, speedX, speedY);
    }

    @Override
    public void update() {
        if (this.isGoUp()) {
            this.setPosY(this.getPosY() - this.getSpeedY() * MOVE_UP_MULTIPLIER);
            this.setRotationDegree(ROTATION_UP);
        } else {
            this.setPosY(this.getPosY() + this.getSpeedY());
            this.setRotationDegree(ROTATION_DOWN);
        } //if false go down

        //Update X
        if (this.isGoForward()) {
            this.setPosX(this.getPosX() + this.getSpeedX()); //if true go forward
        } else {
            // should not go back, only if bonus of getting forward is no longer active
            //  this.setPosX(this.getPosX() - this.getSpeedX());
        } //if false go back
    }

    @Override
    public void draw() {
        //SET current Bitmap, LOAD current Bitmap, DRAW current Bitmap
        this.setCurrentBitmap(getImages().get(this.getRotationDegree() + "_" + ((int) this.getLoopCount() % IMAGE_FRAMES.length))); //reference for collision detection etc.
        Log.d(TAG, "draw: Tried to draw bitmap index: " + this.getRotationDegree() + "_" + ((int) this.getLoopCount() % IMAGE_FRAMES.length) + "/Bitmap->" + this.getCurrentBitmap());

        this.getCanvas().drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }

    //PRELOADING -----------------------------------
    @Override
    public void initialize() {
        try {
            if (!this.isInitialized()) {
                this.setIntrinsicHeightOfPlayer(this.getActivity().getResources().getDrawable(IMAGE_FRAMES[0]).getIntrinsicHeight());

                /*Load all bitmaps [load all rotations and all images from array] -------------------
                 * String of hashmap has following pattern: */
                HashMap<String, Bitmap> loadedBitmaps = new HashMap<>();
                Log.d(TAG, "initialize: Player img length: " + IMAGE_FRAMES.length);
                for (int imgFrame = 0; imgFrame < IMAGE_FRAMES.length; imgFrame++) {
                    loadedBitmaps.put(ROTATION_UP + "_" + imgFrame, getCraftedDynamicBitmap(this.getActivity(),IMAGE_FRAMES[imgFrame], ROTATION_UP, null,null));
                    loadedBitmaps.put(ROTATION_DOWN + "_" + imgFrame, getCraftedDynamicBitmap(this.getActivity(),IMAGE_FRAMES[imgFrame], ROTATION_DOWN, null,null));
                    loadedBitmaps.put(DEFAULT_ROTATION + "_" + imgFrame, getCraftedDynamicBitmap(this.getActivity(),IMAGE_FRAMES[imgFrame], DEFAULT_ROTATION, null, null));
                    Log.d(TAG, "initialize: Loaded following bitmaps->" +
                            ROTATION_UP + "_" + imgFrame + "//" +
                            ROTATION_DOWN + "_" + imgFrame + "//" +
                            DEFAULT_ROTATION + "_" + imgFrame
                    );
                }
                this.setImages(loadedBitmaps);
                this.setHeightOfBitmap(loadedBitmaps.get(ROTATION_UP + "_" + 0).getHeight());
                this.setWidthOfBitmap(loadedBitmaps.get(ROTATION_UP + "_" + 0).getWidth());

                this.setInitialized(true);
                Log.d(TAG, "HEIGHT of Bitmap = " + getHeightOfBitmap());
            }
        } catch (ClassCastException | NullPointerException e) {
            //This should never be thrown! Just check in try block if null and if instance of to prevent issues!
            Log.e(TAG, "initialize: Initializing of Player object FAILED! See error below.");
            e.printStackTrace();
        }
        Log.d(TAG, "initialize: Initializing Player class successful!");
    }

    //GETTER/SETTER ----------------------------------------
    public HashMap<String, Bitmap> getImages() {
        return images;
    }

    public void setImages(HashMap<String, Bitmap> images) {
        this.images = images;
    }
}
