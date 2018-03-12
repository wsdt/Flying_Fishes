package yourowngame.com.yourowngame.classes.actors;

import android.support.annotation.Nullable;

/**
 * Created  on 12.03.2018.
 */

public class RoboticEnemy extends Enemy {
    public RoboticEnemy(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }

}
