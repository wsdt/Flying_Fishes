package yourowngame.com.yourowngame.classes.actors.fruits;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import yourowngame.com.yourowngame.classes.actors.GameObject;

/**
 * PLEASE READ
 *
 * Rewards will be shown sometimes.
 *
 * GOLD - gives the player gold to buy other stuff
 * DOUBLE GOLD - double amount
 * TRIPLE GOLD - triple amount
 * LIFE POINTS - player gets a second life
 * MOVE FORWARDS - ability to move forward
 * INVISIBLE MODE - Player cant die while effect is active
 *
 * And so on ...
 *
 * So we really need an non plus ultra reward system if it comes to performance
 *
 */


public abstract class Fruit extends GameObject {

    private int reward;

    public Fruit(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree,name);
    }

    private enum rewards{
        MELOONS, AVOCIS, PINAPOS,
    }

    @Override
    public abstract void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward);


    @Override
    public abstract void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount);

    @Override @SafeVarargs
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        return false;
    }

    @Override
    public abstract boolean cleanup();

    public int getReward(){
        return reward;
    }
}
