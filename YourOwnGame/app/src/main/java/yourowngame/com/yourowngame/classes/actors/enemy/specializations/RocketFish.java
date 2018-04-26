package yourowngame.com.yourowngame.classes.actors.enemy.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;

/**
 * Created on 12.03.2018.
 */

public class RocketFish extends Enemy {
    private static ArrayList<RocketFish> enemyList = new ArrayList<>(); //consistent name enemyList :)
    private static Bitmap[] images;
    private static final String TAG = "RocketFish";

    /** Used in highscore (only getter/setter, because Highscore is the one who should increment itself) [By default 0, so new enemies would not do anything]
     * -- PositivePoints: E.g. when user shoot down an enemy, each specific enemy supplies a different amount of points.
     * -- NegativePoints: E.g. when enemy was not shoot and passed user without colliding, then user get's negative points. TODO hey thats a nice idea!
     * --> These fields will be set by default from every subclass. So we can modify it at any time.
     *
     * --> SHOULD NOT BE STATIC also not in subclasses so we can modify also single enemies!*/

    public RocketFish(double posX, double posY, double speedX, double speedY, @NonNull int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);

        setPositivePoints(100);
        setNegativePoints(-100);
    }

    public RocketFish() {}

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        this.setPosX(this.getPosX() - this.getSpeedX());
        resetIfOutOfBounds();
    }

    public static void updateAll(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        for (int i = 0; i < getEnemyList().size(); i++) {
            getEnemyList().get(i).update(obj,goUp,goForward);
        }
    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (int i = 0; i < getImages().length; i++) {
            canvas.drawBitmap(getImages()[i], (int) this.getPosX(), (int) this.getPosY(), null);
        }
    }

    public static void drawAll(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (RocketFish e : getEnemyList()) {
            Log.d(TAG, "Enemy X | Y : " + e.getPosX() + "|" + e.getPosY());

            e.draw(activity, canvas, loopCount);
        }
    }

    @Override
    public void createRandomEnemies(int numberOfEnemies) {
        for (int i = 0; i < numberOfEnemies; i++) {
            getEnemyList().add(new RocketFish(RandomHandler.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 100),
                    RandomHandler.getRandomInt(0, GameViewActivity.GAME_HEIGHT),
                    RandomHandler.getRandomFloat(ROCKET_SPEED_MIN, ROCKET_SPEED_MAX),
                    RandomHandler.getRandomFloat(ROCKET_SPEED_MIN, ROCKET_SPEED_MAX),
                    new int[]{R.drawable.enemy_rocketfish}, DEFAULT_ROTATION, "Bomber"));

            getEnemyList().get(i).setCurrentBitmap(getImages()[0]);
        }
    }


    @Override
    @SafeVarargs
    @Enhance(message = {"I get crazy, we have a really bad design here/everywhere with thousands of different Image getters/setters etc. " +
            "Additionally we are not consistent because player has another directive.",
            "Additionally we should consider putting the initialize() method of all enemies into the abstract base class because they will all look the same!"})
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        //we really need to change the initialize, Object params, instanceOf..

        try {
            if (allObjs != null && !isInitialized) {
                if (allObjs[0] instanceof Activity) {
                    Activity activity = (Activity) allObjs[0];
                    setImages(new Bitmap[this.getImg().length]);

                    for (int imgFrame = 0; imgFrame < this.getImg().length; imgFrame++) {
                        getImages()[imgFrame] = this.getCraftedDynamicBitmap(activity, imgFrame, DEFAULT_ROTATION, null, null);
                    }
                } else {
                    Log.d(TAG, "Robo-Enemy: Initialize Failure!");
                    return false;
                }
                Log.d(TAG, "Robo-Enemy: Successfully initialized!");
                return true;
            }
        } catch (NoDrawableInArrayFound_Exception | ClassCastException | NullPointerException e) {
            Log.d(TAG, "Robo-Enemy: Initialize Failure!");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean cleanup() {
        resetWidthAndHeightOfEnemy(); //just reset y/x
        return true;
    }


    //GETTER/SETTER ---------------------------
    public static ArrayList<RocketFish> getEnemyList() {
        return enemyList;
    }

    public static void setEnemyList(ArrayList<RocketFish> enemyList) {
        RocketFish.enemyList = enemyList;
    }

    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        RocketFish.images = images;
    }
}
