package yourowngame.com.yourowngame.classes.actors.enemy;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.enemy.interfaces.IEnemy;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;

/**
 * So if we use the following pattern
 *
 * GameObject
 * --Enemy
 * ----HappenEnemy
 * ----RocketFish
 * ----ChemicalEnemy (and so on.. be imaginative!)
 *
 * Drawing, Updating as well as AI-Handling will be processed by the Subclasses,
 * so the Enemy Superclass is more like a "looks-good-no-feature" class,
 * but i would keep it that way without to think.
 *
 *
 */

public abstract class Enemy extends GameObject implements IEnemy {
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
        setPosX(RandomHandler.getRandomFloat(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 50));
        setPosY(RandomHandler.getRandomFloat(0, GameViewActivity.GAME_HEIGHT));
    }

    /** Would be very if this would be static, but Android (at least our min sdk) does not allow this to be abstract neither in an interface :( */
    public abstract void createRandomEnemies(int numberOfEnemies);

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
