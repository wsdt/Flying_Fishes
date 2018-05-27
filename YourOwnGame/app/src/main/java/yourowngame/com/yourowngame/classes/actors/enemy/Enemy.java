package yourowngame.com.yourowngame.classes.actors.enemy;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.enemy.interfaces.IEnemy;
import yourowngame.com.yourowngame.classes.actors.interfaces.IHighscore_RewardableObj;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

/**
 *
 */

public abstract class Enemy extends GameObject implements IEnemy.PROPERTIES.DEFAULT, IHighscore_RewardableObj {
    private static final String TAG = "Enemy";

    /**Creates random enemy*/
    public Enemy(@NonNull Activity activity){super(activity);}

    //If you change this change it too in EnemyMgr (also when you add params in subclasses!)
    public Enemy(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, @NonNull int[] img, int rotationDegree, @Nullable String name) {
        super(activity, posX, posY, speedX, speedY, img, rotationDegree, name);
    }


    // just for making things easier, if enemy dies or is out of bounds, just reset him!
    //             for X [GAME_WIDTH | GAME_WIDTH + 100]
    //             for Y [     0     |    GAME_HEIGHT  ]
    // so we can easily control this behavior by just editing it here!
    //Now also used in cleanup()
    @Override
    public void resetPos(){
        setPosX(RandomMgr.getRandomFloat(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + ADDITIONAL_GAME_WIDTH));
        setPosY(RandomMgr.getRandomFloat(0, GameViewActivity.GAME_HEIGHT));
    }

    @Override
    public boolean cleanup() {
        resetPos(); //just reset y/x
        return true;
    }
}
