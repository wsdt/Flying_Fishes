package yourowngame.com.yourowngame.classes.actors.enemy;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.enemy.interfaces.IEnemy;
import yourowngame.com.yourowngame.classes.actors.interfaces.IHighscore_RewardableObj;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

/**
 *
 */

public abstract class Enemy extends GameObject implements IEnemy, IHighscore_RewardableObj {
    private static final String TAG = "Enemy";
    //Specific Levels contain an Arraylist with all enemies this class implements arraylist here (so they have the same name)

    public Enemy(){}

    public Enemy(double posX, double posY, double speedX, double speedY, @NonNull int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);

        this.initialize(LevelManager.getBackgroundManager().getGameView().getActivityContext());
    }

    /** we also need to make sure that those enemies who pass the player and the screen rejoin at the end of the scene! */
    public void resetIfOutOfBounds(){
        if(getPosX() <= 0){
            resetWidthAndHeightOfEnemy();
        }
    }

    // just for making things easier, if enemy dies or is out of bounds, just reset him!
    //             for X [GAME_WIDTH | GAME_WIDTH + 100]
    //             for Y [     0     |    GAME_HEIGHT  ]
    // so we can easily control this behavior by just editing it here!
    //Now also used in cleanup()
    public void resetWidthAndHeightOfEnemy(){
        setPosX(RandomMgr.getRandomFloat(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 50));
        setPosY(RandomMgr.getRandomFloat(0, GameViewActivity.GAME_HEIGHT));
    }

    /** Would be very nice if this would be static, but Android (at least our min sdk) does not allow this to be abstract neither in an interface :( */
    public abstract void createRandomEnemies(int numberOfEnemies);
}
