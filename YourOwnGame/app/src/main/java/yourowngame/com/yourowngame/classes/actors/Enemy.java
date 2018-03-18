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
    //private static final String TAG = "Enemy";

    /** Used in highscore (only getter/setter, because Highscore is the one who should increment itself) [By default 0, so new enemies would not do anything]
     * -- PositivePoints: E.g. when user shoot down an enemy, each specific enemy supplies a different amount of points.
     * -- NegativePoints: E.g. when enemy was not shoot and passed user without colliding, then user get's negative points.
     * --> These fields will be set by default from every subclass. So we can modify it at any time.
     *
     * --> SHOULD NOT BE STATIC also not in subclasses so we can modify also single enemies!*/
    private int positivePoints;
    private int negativePoints;
    //Specific Levels contain an Arraylist with all enemies this class implements arraylist here (so they have the same name)

    public Enemy(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }


    /** Would be very if this would be static, but Android (at least our min sdk) does not allow this to be abstract neither in an interface :( */
    public abstract void createRandomEnemies(int numberOfEnemies);

    public Enemy(){}

    /** Highscore methods */
    public int getPositivePoints() {
        return positivePoints;
    }

    public void setPositivePoints(int positivePoints) {
        this.positivePoints = positivePoints;
    }

    public int getNegativePoints() {
        return negativePoints;
    }

    public void setNegativePoints(int negativePoints) {
        this.negativePoints = negativePoints;
    }
}
