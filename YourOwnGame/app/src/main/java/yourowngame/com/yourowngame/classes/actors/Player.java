package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.gameEngine.GameView;
import yourowngame.com.yourowngame.gameEngine.Initializer;


public class Player extends GameObject {
    private static final String TAG = "Player";

    /*-- Preloaded --*/
    private int intrinsicHeightOfPlayer;
    private HashMap<String,Bitmap> loadedBitmaps; //must not be static


    public Player(double posX, double posY, double speedX, double speedY, int img[], float rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);

    }

    /**
     * @param obj       currently not used!
     * @param goUp      check if go up
     * @param goForward check if go forward
     */
    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {

        if (goForward == null && goUp == null) {
            Log.i(TAG, "update: Called update-method without a valid Boolean param!");
        } else {
            // Update Y
            // replaced x/y game starts at 0|0 which is the top left corner of the view
            // if player "jumps" the y value decreases (cause y grows towards the bottom of the view)
            if (goUp != null) {
                if (goUp) {
                    this.setPosY(this.getPosY() - this.getSpeedY() * Constants.Actors.GameObject.MOVE_UP_MULTIPLIER);
                    this.setRotationDegree(Constants.Actors.Player.rotationFlyingUp);
                } else {
                    this.setPosY(this.getPosY() + this.getSpeedY());
                    this.setRotationDegree(Constants.Actors.Player.rotationFlyingDown);
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

    public boolean hitsTheGround(@NonNull GameView currentView) {
        /** The only problem we've got here is, that we need to cut the helicopter images to the best!
         the current PNG has a margin from about 10-20 pixel, which leads to a hitsTheGround earlier!
         So we'll need to cut all helicopte images to the maximum! then this method will work just fine*/

        //Gets the scaled-size of the current player image
        float playerPosYWithImage = (float) this.getPosY() + (this.getIntrinsicHeightOfPlayer() * Constants.GameLogic.GameView.widthInPercentage);
        float playerPosYWithoutImage = (float) this.getPosY();

        //compares, if player hits ground or top
        return (playerPosYWithImage > currentView.getLayout().getHeight() || playerPosYWithoutImage < 0);
    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        this.setCurrentBitmap(loadedBitmaps.get(this.getRotationDegree()+"_"+((int) loopCount%this.getImg().length))); //reference for collision detection etc.
        //works, the bitmaps are referenced!
        Log.d(TAG, "current Bitmap is: " + getCurrentBitmap());
        canvas.drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }

    //PRELOADING -----------------------------------

    /** OBJ[0]: Activity */
    @Override @SafeVarargs
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        try {
            if (allObjs != null) {
                if (allObjs[0] instanceof Activity) {
                    Activity activity = (Activity) allObjs[0];
                    this.setIntrinsicHeightOfPlayer(activity.getResources().getDrawable(this.getImg()[0]).getIntrinsicHeight());

                    /*Load all bitmaps [load all rotations and all images from array] -------------------
                    * String of hashmap has following pattern: */
                    HashMap<String, Bitmap> loadedBitmaps = new HashMap<>();
                    Log.d(TAG, "initialize: Player img length: "+ this.getImg());
                    for (int imgFrame = 0; imgFrame < this.getImg().length; imgFrame++) {
                        loadedBitmaps.put(Constants.Actors.Player.rotationFlyingUp + "_" + imgFrame, this.getCraftedDynamicBitmap(activity, this.getImg()[imgFrame], Constants.Actors.Player.rotationFlyingUp, Constants.Actors.Player.widthPercentage, Constants.Actors.Player.heightPercentage));
                        loadedBitmaps.put(Constants.Actors.Player.rotationFlyingDown + "_" + imgFrame, this.getCraftedDynamicBitmap(activity, this.getImg()[imgFrame], Constants.Actors.Player.rotationFlyingUp, Constants.Actors.Player.widthPercentage, Constants.Actors.Player.heightPercentage));
                    }
                    this.setLoadedBitmaps(loadedBitmaps);
                }
            } else {
                return false;
            }
        } catch (ClassCastException | NullPointerException | NoDrawableInArrayFound_Exception e) {
            //This should never be thrown! Just check in try block if null and if instance of to prevent issues!
            Log.e(TAG, "initialize: Initializing of Player object FAILED! See error below.");
            e.printStackTrace();
            return false;
        }
        Log.d(TAG, "initialize: Initializing Player class successful!");
        return true;
    }

    @Override
    public boolean cleanup() {
        //Set to illegal values/null
        this.setIntrinsicHeightOfPlayer(Initializer.PRIMITIVES_ILLEGAL_VALUE);
        this.setLoadedBitmaps(null);
        return true;
    }

    //GETTER/SETTER -------------------------------
    public int getIntrinsicHeightOfPlayer() {
        return intrinsicHeightOfPlayer;
    }

    public void setIntrinsicHeightOfPlayer(int intrinsicHeightOfPlayer) {
        this.intrinsicHeightOfPlayer = intrinsicHeightOfPlayer;
    }

    public HashMap<String, Bitmap> getLoadedBitmaps() {
        return loadedBitmaps;
    }

    public void setLoadedBitmaps(HashMap<String, Bitmap> loadedBitmaps) {
        this.loadedBitmaps = loadedBitmaps;
    }
}

