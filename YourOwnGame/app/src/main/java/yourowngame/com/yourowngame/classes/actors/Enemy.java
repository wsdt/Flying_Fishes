package yourowngame.com.yourowngame.classes.actors;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * So if we use the following pattern
 *
 * GameObject
 * --Enemy
 * ----RoboticEnemy
 * ----BomberEnemy
 * ----ChemicalEnemy (and so on.. be imaginative!)
 *
 * Drawing, Updating as well as AI-Handling will be processed by the Subclasses,
 * so the Enemy Superclass is more like a "looks-good-no-feature" class,
 * but i would keep it that way without to think.
 *
 *
 */

public abstract class Enemy extends GameObject {
    private static final String TAG = "Enemy";

    //Specific Levels contain an Arraylist with all enemies this class implements arraylist here (so they have the same name)

    public Enemy(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }


    /** Would be very if this would be static, but Android (at least our min sdk) does not allow this to be abstract neither in an interface :( */
    public abstract void createRandomEnemies(int numberOfEnemies);

    public Enemy(){}

}
