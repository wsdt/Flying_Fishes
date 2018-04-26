package yourowngame.com.yourowngame.classes.actors.enemy.specializations;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;

/**
 *
 * Enemy, which explodes when hit, makes area defect!
 *
 * NOT IN ALPHA RELEASE
 *
 */


public class DynamiteEnemy extends Enemy{
    @Override
    public void createRandomEnemies(int numberOfEnemies) {

    }

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {

    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {

    }

    @Override
    public <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        return false;
    }

    @Override
    public boolean cleanup() {
        return true;
    }
}
