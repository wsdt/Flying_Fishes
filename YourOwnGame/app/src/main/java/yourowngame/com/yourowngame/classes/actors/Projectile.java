package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

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

    public Projectile(double posX, double posY, double speedX, double speedY, int[] img, float rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }


    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {

    }

    @Override
    public boolean collision(View view, GameObject obj) {
        return false;
    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) {

    }


}
