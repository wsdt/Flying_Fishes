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
    private Bitmap projectile;

    public Projectile(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
        Log.d(TAG, "New Projectile ready to fire!");
    }


    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        this.setPosX(getPosX() + getSpeedX());
    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) {
        this.setCurrentBitmap(BitmapFactory.decodeResource(activity.getResources(), this.getImg()[0]));
        canvas.drawBitmap(this.getCurrentBitmap(), (int) this.getPosX(), (int) this.getPosY(), null);
    }

    /** OBJ[0]: Activity */
    @Override @SafeVarargs
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
       return true;
    }


    @Override
    public boolean cleanup() {
        return false;
    }

}
