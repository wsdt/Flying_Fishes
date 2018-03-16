package yourowngame.com.yourowngame.classes.actors;

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
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;

/**
 * Created on 12.03.2018.
 */

public class BomberEnemy extends Enemy {
    private static ArrayList<BomberEnemy> enemyList = new ArrayList<>(); //consistent name enemyList :)
    private static Bitmap[] images;
    private static final String TAG = "BomberEnemy";

    public BomberEnemy(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    public BomberEnemy() {}

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        this.setPosX(this.getPosX() - this.getSpeedX());
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
        for (BomberEnemy e : getEnemyList()) {
            Log.d(TAG, "Enemy X | Y : " + e.getPosX() + "|" + e.getPosY());

            e.draw(activity, canvas, loopCount);
        }
    }

    @Override
    public void createRandomEnemies(int numberOfEnemies) {
        for (int i = 0; i < numberOfEnemies; i++) {
            getEnemyList().add(new BomberEnemy(RandomHandler.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH),
                    RandomHandler.getRandomInt(GameViewActivity.GAME_HEIGHT / 2, GameViewActivity.GAME_HEIGHT),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedXmin, Constants.Actors.Enemy.speedXmax),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedYmin, Constants.Actors.Enemy.speedYmax),
                    null, Constants.Actors.Enemy.defaultRotation, "BomberEnemy " + i));

            getEnemyList().get(i).setCurrentBitmap(images[0]);
        }
    }


    @Override
    public <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        if (allObjs != null) {
            if (allObjs[0] instanceof Activity) {
                Activity activity = (Activity) allObjs[0];
                setImages(new Bitmap[2]);

                // same here, percentage, just for testing now
                getImages()[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bomb2), 64, 64, false);
                getImages()[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bomb2), 64, 64, false);

            } else {
                Log.d(TAG, "Super-Enemy: Initialize Failure!");
                return false;
            }
            Log.d(TAG, "Super-Enemy: Successfully initialized!");
            return true;
        }
        Log.d(TAG, "Super-Enemy: Initialize Failure!");
        return false;
    }

    @Override
    public boolean cleanup() {
        setEnemyList(null);
        return false;
    }


    //GETTER/SETTER ---------------------------
    public static ArrayList<BomberEnemy> getEnemyList() {
        return enemyList;
    }

    public static void setEnemyList(ArrayList<BomberEnemy> enemyList) {
        BomberEnemy.enemyList = enemyList;
    }

    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        BomberEnemy.images = images;
    }
}
