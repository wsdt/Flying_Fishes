package yourowngame.com.yourowngame.classes.actors;

import android.support.annotation.Nullable;

/**
 * So if we use the following pattern
 *
 * GameObject
 * --Enemy
 * ----RoboticEnemy
 * ----SuperEnemy
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

    public Enemy(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    public abstract void createRandomEnemies(int numberOfEnemies);

    public Enemy(){}
}
