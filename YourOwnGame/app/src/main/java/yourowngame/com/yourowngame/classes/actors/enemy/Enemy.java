package yourowngame.com.yourowngame.classes.actors.enemy;

import android.app.Activity;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.interfaces.IHighscore_RewardableObj;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;


/**
 *
 */

public abstract class Enemy extends GameObject implements IHighscore_RewardableObj {
    private static final String TAG = "Enemy";

    /**
     * Default Constants ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * > protected so that they are accessible in subclasses
     */
    protected static final int HIGHSCORE_REWARD = 100;
    protected static final float SPEED_X_MIN = 5f;
    protected static final float SPEED_X_MAX = 10f; //TODO: could be also level dependent :) this will surely be level dependent :)
    protected static final float SPEED_Y_MIN = 2f; //TODO: Just use these here and add a lvlConstant (e.g. a difficulty e.g. *1.1 etc.
    protected static final float SPEED_Y_MAX = 3f;

    /**
     * Target GameObj (mostly Player) so enemies can adapt their movements correlating to player.
     */
    private GameObject targetGameObj;

    /** This value is different for each enemy-subclass. Usually the higher this value the harder
     * the enemy is to kill. This value has also different ranges which are usual for each enemy
     * (e.g. [0...1],[1...500] etc.). Each enemy subclass (at least those who use this value) has
     * a short description how to use this value.
     *
     * This value is by default 1, which should (but doesn't has to) not have any effect on any enemies.
     * But as mentioned every enemy uses that value differently, so please take a look. */
    private double aggressivity = 1;

    /**
     * Creates random enemy
     */
    public Enemy(@NonNull Activity activity) {
        super(activity);
    }

    //If you change this change it too in EnemyMgr (also when you add params in subclasses!)
    public Enemy(@NonNull Activity activity, double posX, double posY, double speedX, double speedY, double aggressivity) {
        super(activity, posX, posY, speedX, speedY);
        this.setAggressivity(aggressivity);
    }


    // just for making things easier, if enemy dies or is out of bounds, just reset him!
    //             for X [GAME_WIDTH | GAME_WIDTH + 100]
    //             for Y [     0     |    GAME_HEIGHT  ]
    // so we can easily control this behavior by just editing it here!
    //Now also used in cleanup()
    @Override
    public void resetPos() {
        setPosX(RandomMgr.getRandomFloat(DrawableSurfaces.getDrawWidth()+this.getWidthOfBitmap(), DrawableSurfaces.getDrawWidth() + this.getWidthOfBitmap())); //+width of bitmap to spawn outside of screen));
        setPosY(RandomMgr.getRandomFloat(this.getHeightOfBitmap(), DrawableSurfaces.getDrawHeight()-this.getHeightOfBitmap()));
    }

    @Override
    public void resetSpeed() {
        setSpeedX(RandomMgr.getRandomFloat(SPEED_X_MIN, SPEED_X_MAX));
        setSpeedY(RandomMgr.getRandomFloat(SPEED_Y_MIN, SPEED_Y_MAX));
    }

    //GETTER /SETTER ----------------------------------------
    public GameObject getTargetGameObj() {
        return targetGameObj;
    }

    public void setTargetGameObj(GameObject targetGameObj) {
        this.targetGameObj = targetGameObj;
    }

    public double getAggressivity() {
        return aggressivity;
    }

    public void setAggressivity(double aggressivity) {
        this.aggressivity = aggressivity;
    }
}
