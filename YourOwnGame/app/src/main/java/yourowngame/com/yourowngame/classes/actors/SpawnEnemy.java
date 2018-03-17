package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.annotations.TestingPurpose;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;

/**
 * Created  on 12.03.2018.
 *
 */

public class SpawnEnemy extends Enemy {
    private static final String TAG = "SpawnEnemy";
    private static Bitmap[] images;
    private static ArrayList<SpawnEnemy> enemyList = new ArrayList<>();

    public SpawnEnemy(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    public SpawnEnemy(){}


    @Override @TestingPurpose (
            createdBy = Constants.Developers.WSDT,
            lastModified = "04.03.2018 : 11:30",
            deleteWhenUnused = false
    )
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        //TODO spawn sometimes(!) in different y values
        this.setPosY(RandomHandler.getRandomFloat(50,300));
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
            getEnemyList().add(new SpawnEnemy(RandomHandler.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH),
                    RandomHandler.getRandomInt(GameViewActivity.GAME_HEIGHT / 2, GameViewActivity.GAME_HEIGHT),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedXmin, Constants.Actors.Enemy.speedXmax),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedYmin, Constants.Actors.Enemy.speedYmax),
                    null, Constants.Actors.Enemy.defaultRotation, "Spawn"));

            getEnemyList().get(i).setCurrentBitmap(images[0]);
        }
    }

    //TODO: in my opinion, a simple bitmap array would match the animation the best! --> YES BUT WE ARE INCONSTENT :( (when we do this we should also do it in Player etc. instead of img[] drawable int arr)
    //but we surely should do something to slow it down
    /** Single enemy should not draw all of them (not object-oriented) */
    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (int i = 0; i < getImages().length; i++) {
            canvas.drawBitmap(getImages()[i], (int) this.getPosX(), (int) this.getPosY(), null);
        }
    }

    public static void drawAll(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (SpawnEnemy e : getEnemyList()) {
            Log.d(TAG, "Enemy X | Y : " + e.getPosX() + "|" + e.getPosY());

            e.draw(activity,canvas,loopCount);
        }
    }

    @Override
    public <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        //we really need to change the initialize, Object params, instanceOf..

        if (allObjs != null) {
            if (allObjs[0] instanceof Activity) {
                Activity activity = (Activity) allObjs[0];
                setImages(new Bitmap[2]);
                //TODO: For testing robo img
                getImages()[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.app_icon_gameboy), 64, 64, false);
                getImages()[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.app_icon_gameboy), 64, 64, false);

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
        setImages(null);
        return false;
    }

    /** GETTER / SETTER */

    public static ArrayList<SpawnEnemy> getEnemyList() {
        return enemyList;
    }

    public static void setEnemyList(ArrayList<SpawnEnemy> enemyList) {
        SpawnEnemy.enemyList = enemyList;
    }

    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        SpawnEnemy.images = images;
    }

}
