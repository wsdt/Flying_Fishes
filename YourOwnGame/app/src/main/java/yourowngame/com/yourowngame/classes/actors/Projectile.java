package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;

/**
 * Created by Solution on 27.02.2018.
 *
 *  Projectiles which the player can shoot to kill enemys!
 *  Later on, projectiles will be available to buy in the shop (f.e)
 *
 *  Projectiles will be used as Singletone
 *
 *  Everytime the player hits a button or something else, projectiles will be shown
 *
 *  So shall we create a Singletone with a List of Projectiles?
 */

public class Projectile extends GameObject {
    private final String TAG = "Projectile";
    private static int fireRate = 0;
    //Static so we can avoid bitmap creation when firing

    //TODO: Bad design, because we have made currentBitmap() a non-static member, so we have to do this! :( [maybe we can change that in future]
    //TODO: absolutely, we currently have bitmap-arrays as images[], the currentBitmap which only holds the latest bitmap for collission and this sharedBitmap here...
    private static Bitmap sharedBitmap;


    public Projectile(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
        Log.d(TAG, "New Projectile ready to fire!");
        initialize(activity);
    }


    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        this.setPosX(getPosX() + getSpeedX());
    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) {
        canvas.drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }

    /** OBJ[0]: Activity */
    @Override @SafeVarargs
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        try {
            if (allObjs != null) {
                if (allObjs[0] instanceof Activity) {
                    if (getSharedBitmap() == null) { //only do it if null (performance enhancement)
                        setSharedBitmap(this.getCraftedDynamicBitmap((Activity) allObjs[0],this.getImg().length-1 /*just use first img (works as long as no animation)*/,null,null,null));
                        Log.d(TAG, "initialize: Shared Bitmap->"+getSharedBitmap());
                    }
                    //Always set bullet
                    this.setCurrentBitmap(getSharedBitmap()); //todo: bad design (but here to avoid further error when calculating collisions with projectiles etc.)
                }
            }
        } catch (ClassCastException | NullPointerException | NoDrawableInArrayFound_Exception e) {
            Log.e(TAG, "initialize: Could not initialize Projectiles!");
            e.printStackTrace();
        }
       return true;
    }


    @Override
    public boolean cleanup() {
        setSharedBitmap(null);
        return true;
    }

    //GETTER/SETTER ---------------------------
    public static Bitmap getSharedBitmap() {
        return sharedBitmap;
    }

    public static void setSharedBitmap(Bitmap sharedBitmap) {
        Projectile.sharedBitmap = sharedBitmap;
    }
}
