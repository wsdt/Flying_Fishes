package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.annotations.TestingPurpose;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.gameEngine.GameView;
import yourowngame.com.yourowngame.gameEngine.Initializer;


public class Player extends GameObject {
    private static final String TAG = "Player";
    private static final String TAG2 = "Projectile";

    //holds all the projectiles the player shoots
    private List<Projectile> projectileList = new ArrayList<>();
    private static Bitmap[] images;

    /*-- Preloaded --*/
    private int intrinsicHeightOfPlayer;
    //private HashMap<String,Bitmap> loadedBitmaps; //must not be static


    public Player(double posX, double posY, double speedX, double speedY, int img[], int rotationDegree, @Nullable String name) {
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
        //Gets the scaled-size of the current player image
        float playerPosYWithImage = (float) this.getPosY() + (this.getIntrinsicHeightOfPlayer() * Constants.GameLogic.GameView.widthInPercentage);
        float playerPosYWithoutImage = (float) this.getPosY();

        //compares, if player hits ground or top
        return (playerPosYWithImage > currentView.getLayout().getHeight() || playerPosYWithoutImage < 0);
    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (int i = 0; i < getImages().length; i++) {
            setCurrentBitmap(getImages()[i]); /** here we need to adjust the current bitmap, just for collision detection!*/
            canvas.drawBitmap(getImages()[i], (int) this.getPosX(), (int) this.getPosY(), null);
        }
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

                    //TODO animation is too fast, if we manage it to progress the min of 25 fps or 30, we could highly improve performance
                   setImages(new Bitmap[4]);
                   images[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.player_heli_blue_1), 128, 128, false);
                   images[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.player_heli_blue_2), 128, 128, false);
                   images[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.player_heli_blue_3), 128, 128, false);
                   images[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.player_heli_blue_4), 128, 128, false);

                   //just use the first one, no further calculation needed, works fine
                   this.setHeightOfBitmap(images[0].getHeight());
                   this.setWidthOfBitmap(images[0].getWidth());

                   Log.d(TAG, "HEIGHT of Bitmap = " + getHeightOfBitmap());
                }

            } else {
                return false;
            }
        } catch (ClassCastException | NullPointerException e) {
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
        images = null;
        return true;
    }

    /***********************************************
     *             PROJECTILES AREA                *
     ***********************************************/

    public void addProjectiles(@NonNull Activity activity){
        projectileList.add(new Projectile(activity,this.getPosX() + this.getWidthOfBitmap()/2, this.getPosY() + this.getHeightOfBitmap()/2, 10, 0, new int[]{R.drawable.color_player_bullet}, 0, "bullet"));
    }

    public void drawProjectiles(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount){
        for (Projectile e : this.projectileList)
            e.draw(activity, canvas, loopCount);
    }

    //Here we need to access the array backwards, otherwise we will remove an index, that will be progressed, but isn't there anymore!
    public void updateProjectiles(){
        Log.d(TAG2, "Projectile Size = " + this.projectileList.size());
        if(!this.projectileList.isEmpty()){
            for (int i = this.projectileList.size() - 1; i > -1; i--){
                this.projectileList.get(i).update(null, null, null);

                if (this.projectileList.get(i).getPosX() > GameViewActivity.GAME_WIDTH){
                    Log.e(TAG2, "Bullet removed!");
                    this.projectileList.remove(this.projectileList.get(i));
                }
            }
        }
    }

    /*************************
     *  GETTER & SETTER      *
     *************************/
    public List getProjectiles(){
        return projectileList;
    }

    public Projectile getProjectileAtPosition(int pos){
        return projectileList.get(pos);
    }

    public int getIntrinsicHeightOfPlayer() {
        return intrinsicHeightOfPlayer;
    }

    public void setIntrinsicHeightOfPlayer(int intrinsicHeightOfPlayer) {
        this.intrinsicHeightOfPlayer = intrinsicHeightOfPlayer;
    }
    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        Player.images = images;
    }
}

