package yourowngame.com.yourowngame.classes.actors.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.Projectile;
import yourowngame.com.yourowngame.classes.actors.player.interfaces.IPlayer;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
import yourowngame.com.yourowngame.gameEngine.GameView;


public class Player extends GameObject implements IPlayer {
    private static final String TAG = "Player";
    private static final String TAG2 = "Projectile";

    //Projectiles
    private List<Projectile> projectileList = new ArrayList<>();
    private float fireRate = 0.5f; //fire rate from 1 to 0

    /*-- Preloaded --*/
    private int intrinsicHeightOfPlayer;
    private HashMap<String, Bitmap> loadedBitmaps; //must not be static


    public Player(@NonNull Context context, double posX, double posY, double speedX, double speedY, int img[], int rotationDegree, @Nullable String name) {
        super(context, posX, posY, speedX, speedY, img, rotationDegree, name);
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
            if (goUp != null) {
                if (goUp) {
                    this.setPosY(this.getPosY() - this.getSpeedY() * MOVE_UP_MULTIPLIER);
                    this.setRotationDegree(ROTATION_UP);
                } else {
                    this.setPosY(this.getPosY() + this.getSpeedY());
                    this.setRotationDegree(ROTATION_DOWN);
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
        float playerPosYWithoutImage = (float) this.getPosY();

        //compares, if player hits ground or top
        return (getWidthOfPlayer() > currentView.getLayout().getHeight() || playerPosYWithoutImage < 0);
    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        //SET current Bitmap, LOAD current Bitmap, DRAW current Bitmap
        this.setCurrentBitmap(loadedBitmaps.get(this.getRotationDegree() + "_" + ((int) loopCount % this.getImg().length))); //reference for collision detection etc.
        Log.d(TAG, "draw: Tried to draw bitmap index: " + this.getRotationDegree() + "_" + ((int) loopCount % this.getImg().length) + "/Bitmap->" + this.getCurrentBitmap());

        canvas.drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }


    //PRELOADING -----------------------------------

    /**
     * OBJ[0]: Activity
     */
    @Override
    @SafeVarargs
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        try {
            if (!isInitialized) {
                this.setIntrinsicHeightOfPlayer(this.getContext().getResources().getDrawable(this.getImg()[0]).getIntrinsicHeight());

                /*Load all bitmaps [load all rotations and all images from array] -------------------
                 * String of hashmap has following pattern: */
                HashMap<String, Bitmap> loadedBitmaps = new HashMap<>();
                Log.d(TAG, "initialize: Player img length: " + getImg().length);
                for (int imgFrame = 0; imgFrame < this.getImg().length; imgFrame++) {
                    loadedBitmaps.put(ROTATION_UP + "_" + imgFrame, this.getCraftedDynamicBitmap(imgFrame, ROTATION_UP, SCALED_WIDTH_PERCENTAGE, SCALED_HEIGHT_PERCENTAGE));
                    loadedBitmaps.put(ROTATION_DOWN + "_" + imgFrame, this.getCraftedDynamicBitmap(imgFrame, ROTATION_DOWN, SCALED_WIDTH_PERCENTAGE, SCALED_HEIGHT_PERCENTAGE));
                    loadedBitmaps.put(DEFAULT_ROTATION + "_" + imgFrame, this.getCraftedDynamicBitmap(imgFrame, DEFAULT_ROTATION, SCALED_WIDTH_PERCENTAGE, SCALED_HEIGHT_PERCENTAGE));
                    Log.d(TAG, "initialize: Loaded following bitmaps->" +
                            ROTATION_UP + "_" + imgFrame + "//" +
                            ROTATION_DOWN + "_" + imgFrame + "//" +
                            DEFAULT_ROTATION + "_" + imgFrame
                    );
                }
                this.setLoadedBitmaps(loadedBitmaps);
                this.setHeightOfBitmap(loadedBitmaps.get(ROTATION_UP + "_" + 0).getHeight());
                this.setWidthOfBitmap(loadedBitmaps.get(ROTATION_UP + "_" + 0).getWidth());

                isInitialized = true;
                Log.d(TAG, "HEIGHT of Bitmap = " + getHeightOfBitmap());
            }
        } catch (ClassCastException | NullPointerException | NoDrawableInArrayFound_Exception e) {
            //This should never be thrown! Just check in try block if null and if instance of to prevent issues!
            Log.e(TAG, "initialize: Initializing of Player object FAILED! See error below.");
            e.printStackTrace();
        }
        Log.d(TAG, "initialize: Initializing Player class successful!");
        return isInitialized;
    }


    @Override
    public boolean cleanup() {
        this.setPosY(Resources.getSystem().getDisplayMetrics().heightPixels / 4); //reset y when hitting ground
        for (Projectile projectile : projectileList) {
            projectile.cleanup();
        }
        return true;
    }

    /***********************************************
     *             PROJECTILES AREA                *
     ***********************************************/

    public void addProjectiles(@NonNull Activity activity) {
        projectileList.add(new Projectile(activity, this.getPosX() + this.getWidthOfBitmap() / 2, this.getPosY() + this.getHeightOfBitmap() / 2, 10, 0, new int[]{R.drawable.color_player_bullet}, 0, "bullet"));
    }

    public void drawProjectiles(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) {
        for (Projectile e : this.projectileList)
            e.draw(activity, canvas, loopCount);
    }

    //Here we need to access the array backwards, otherwise we will remove an index, that will be progressed, but isn't there anymore!
    public void updateProjectiles() {
        Log.d(TAG2, "Projectile Size = " + this.projectileList.size());
        if (!this.projectileList.isEmpty()) {
            for (int i = this.projectileList.size() - 1; i > -1; i--) {
                this.projectileList.get(i).update(null, null, null);

                if (this.projectileList.get(i).getPosX() > GameViewActivity.GAME_WIDTH - 50) {
                    Log.e(TAG2, "Bullet removed!");
                    this.projectileList.remove(this.projectileList.get(i));
                }
            }
        }
    }

    /*************************
     *  GETTER & SETTER      *
     *************************/

    public List getProjectiles() {
        return projectileList;
    }

    public Projectile getProjectileAtPosition(int pos) {
        return projectileList.get(pos);
    }

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

    public float getWidthOfPlayer() {
        return (float) this.getPosY() + (this.getIntrinsicHeightOfPlayer() * Constants.GameLogic.GameView.widthInPercentage);
    }

    public void incrementFireRate() {
        fireRate += 0.1;
        if (fireRate > IPlayer.fireRateMax)
            fireRate = IPlayer.fireRateMax; //max
    }

    public void decrementFireRate() {
        fireRate -= 0.1;
        if (fireRate < IPlayer.fireRateMin)
            fireRate = IPlayer.fireRateMin; //min
    }

    public float getFireRate() {
        return fireRate;
    }


}

