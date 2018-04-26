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
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;

/**
 * Created  on 12.03.2018.
 *
 */

public class BobaEnemy extends Enemy {
    private static final String TAG = "BobaEnemy";
    private static Bitmap[] images;
    private static ArrayList<BobaEnemy> enemyList = new ArrayList<>();

    /** Used in highscore (only getter/setter, because Highscore is the one who should increment itself) [By default 0, so new enemies would not do anything]
     * -- PositivePoints: E.g. when user shoot down an enemy, each specific enemy supplies a different amount of points.
     * -- NegativePoints: E.g. when enemy was not shoot and passed user without colliding, then user get's negative points.
     * --> These fields will be set by default from every subclass. So we can modify it at any time.
     *
     * --> SHOULD NOT BE STATIC also not in subclasses so we can modify also single enemies!*/


    public BobaEnemy(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);

        setPositivePoints(250);
        setNegativePoints(-100);
    }

    public BobaEnemy(){}


    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        resetIfOutOfBounds();

        //add a smooth movement here
        this.setPosX(this.getPosX() - this.getSpeedX());

    }

    public static void updateAll(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        for (int i = 0; i < getEnemyList().size(); i++){
            getEnemyList().get(i).update(obj,goUp,goForward);
        }
    }

    @Override
    public void createRandomEnemies(int count){
        for (int i = 0; i < count; i++){
            getEnemyList().add(new BobaEnemy(RandomHandler.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 100),
                    RandomHandler.getRandomInt(0, GameViewActivity.GAME_HEIGHT),
                    RandomHandler.getRandomFloat(SPEED_X_MIN, SPEED_X_MAX),
                    RandomHandler.getRandomFloat(SPEED_Y_MIN, SPEED_Y_MAX),
                    null, DEFAULT_ROTATION, "Spawn"));

            getEnemyList().get(i).setCurrentBitmap(images[0]);
        }
    }

    //but we surely should do something to slow it down
    /** Single enemy should not draw all of them (not object-oriented) */
    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (int i = 0; i < getImages().length; i++) {
            canvas.drawBitmap(getImages()[i], (int) this.getPosX(), (int) this.getPosY(), null);
        }
    }

    public static void drawAll(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (BobaEnemy e : getEnemyList()) {
            Log.d(TAG, "Enemy X | Y : " + e.getPosX() + "|" + e.getPosY());

            e.draw(activity,canvas,loopCount);
        }
    }

    @Override @SafeVarargs
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        //we really need to change the initialize, Object params, instanceOf..

        if (allObjs != null) {
            if (allObjs[0] instanceof Activity) {
                Activity activity = (Activity) allObjs[0];
                setImages(new Bitmap[2]);
                //TODO: For testing robo img
                getImages()[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.boba), 96, 96, false);
                getImages()[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.boba), 96, 96, false);

            } else {
                Log.d(TAG, "Robo-Enemy: Initialize Failure!");
                return false;
            }
            Log.d(TAG, "Robo-Enemy: Successfully initialized!");
            return true;
        }
        Log.d(TAG, "Robo-Enemy: Initialize Failure!");
        return false;
    }

    @Override
    public boolean cleanup() {
        setEnemyList(new ArrayList<BobaEnemy>());
        setImages(null);
        return true;
    }

    /** GETTER / SETTER */

    public static ArrayList<BobaEnemy> getEnemyList() {
        return enemyList;
    }

    public static void setEnemyList(ArrayList<BobaEnemy> enemyList) {
        BobaEnemy.enemyList = enemyList;
    }

    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        BobaEnemy.images = images;
    }

}
