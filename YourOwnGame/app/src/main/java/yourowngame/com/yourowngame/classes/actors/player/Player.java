package yourowngame.com.yourowngame.classes.actors.player;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.player.interfaces.IPlayer;
import yourowngame.com.yourowngame.classes.actors.projectiles.Projectile;
import yourowngame.com.yourowngame.classes.actors.projectiles.specializations.IronProjectile;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
import yourowngame.com.yourowngame.gameEngine.GameView;


public class Player extends GameObject implements IPlayer {
    private static final String TAG = "Player";
    /**
     * Use wrapperClasses to determine whether they are set or not.
     */
    private boolean goUp = false;
    private boolean goForward = false;

    //Projectiles
    private List<Projectile> projectileList = new ArrayList<>();

    /*-- Preloaded --*/
    private int intrinsicHeightOfPlayer;
    private HashMap<String, Bitmap> loadedBitmaps; //must not be static
    private Bitmap currentBitmap;


    public Player(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, int img[]) {
        super(activity, posX, posY, speedX, speedY, img);
    }

    /**
     * Block here creating simplified playerInstances, bc. we don't need random players.
     */
    private Player(@NonNull Activity activity) {
        super(activity);
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


    public boolean hitsTheGround(@NonNull GameView currentView) {
        float playerPosYWithoutImage = (float) this.getPosY();

        //compares, if player hits ground or top
        return (getWidthOfPlayer() > currentView.getLayout().getHeight() || playerPosYWithoutImage < 0);
    }

    @Override
    public void draw() {
        //SET current Bitmap, LOAD current Bitmap, DRAW current Bitmap
        this.setCurrentBitmap(loadedBitmaps.get(this.getRotationDegree() + "_" + ((int) this.getLoopCount() % this.getImg().length))); //reference for collision detection etc.
        Log.d(TAG, "draw: Tried to draw bitmap index: " + this.getRotationDegree() + "_" + ((int) this.getLoopCount() % this.getImg().length) + "/Bitmap->" + this.getCurrentBitmap());

        this.getCanvas().drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }


    //PRELOADING -----------------------------------
    @Override
    public void initialize() {
        try {
            if (!this.isInitialized()) {
                this.setIntrinsicHeightOfPlayer(this.getActivity().getResources().getDrawable(this.getImg()[0]).getIntrinsicHeight());

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

                this.setInitialized(true);
                Log.d(TAG, "HEIGHT of Bitmap = " + getHeightOfBitmap());
            }
        } catch (ClassCastException | NullPointerException | NoDrawableInArrayFound_Exception e) {
            //This should never be thrown! Just check in try block if null and if instance of to prevent issues!
            Log.e(TAG, "initialize: Initializing of Player object FAILED! See error below.");
            e.printStackTrace();
        }
        Log.d(TAG, "initialize: Initializing Player class successful!");
    }


    @Override
    public boolean cleanup() {
        resetPos();
        for (Projectile projectile : this.getProjectiles()) {
            projectile.cleanup();
        }
        return true;
    }

    @Override
    public void resetPos() {
        this.setPosY(Resources.getSystem().getDisplayMetrics().heightPixels / 4); //reset y when hitting ground
    }

    /***********************************************
     *             PROJECTILES AREA                *
     ***********************************************/

    public void addProjectiles() {
        Projectile projectile = new IronProjectile(this.getActivity(), this.getPosX() + this.getWidthOfBitmap() / 2, this.getPosY() + this.getHeightOfBitmap() / 2, 10, 0, new int[]{R.drawable.color_player_bullet});
        projectile.initialize();
        projectileList.add(projectile);
    }

    public void drawProjectiles() {
        for (Projectile e : this.projectileList) {
            e.draw();
        }
    }

    //Here we need to access the array backwards, otherwise we will remove an index, that will be progressed, but isn't there anymore!
    public void updateProjectiles() {
        Log.d(TAG, "updateProjectiles: Projectile Size = " + this.projectileList.size());
        if (!this.projectileList.isEmpty()) {
            for (int i = this.projectileList.size() - 1; i > -1; i--) {
                this.projectileList.get(i).update();

                if (this.projectileList.get(i).getPosX() > GameViewActivity.GAME_WIDTH - 50) {
                    Log.e(TAG, "updateProjectiles: Bullet removed!");
                    this.projectileList.remove(this.projectileList.get(i));
                }
            }
        }
    }

    /*************************
     *  GETTER & SETTER      *
     *************************/

    public List<Projectile> getProjectiles() {
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

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
    }

    /**
     * Here to determine whether obj should goUp/Down or Forward/or standing still [no backwards
     * movement allowed] on next drawCycle. Here and not in DrawableObj bc. Background cannot move
     * as a whole. Additionally only in Player class, bc. enemies and fruits need no commands to move.
     */
    public boolean isGoUp() {
        return goUp;
    }

    public void setGoUp(boolean goUp) {
        this.goUp = goUp;
    }

    public boolean isGoForward() {
        return goForward;
    }

    public void setGoForward(boolean goForward) {
        this.goForward = goForward;
    }
}

