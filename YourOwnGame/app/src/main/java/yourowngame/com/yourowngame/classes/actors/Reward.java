package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

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


public class Reward extends GameObject {

    private int reward;

    public Reward(double posX, double posY, double speedX, double speedY, int[] img,int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree,name);
    }

    private enum rewards{
        GOLD, DOUBLE_GOLD, TRIPLE_GOLD,
        LIFE_POINTS, MOVE_FORWARD, INVISIBLE,
        //just add some more! based on these we will check what has been collected.
    }

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {

    }


    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) {

    }

    @Override @SafeVarargs
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        return false;
    }

    @Override
    public boolean cleanup() {
        return false;
    }

    public int getReward(){
        return reward;
    }
}
