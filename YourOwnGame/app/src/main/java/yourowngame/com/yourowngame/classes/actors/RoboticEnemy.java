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
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;

/**
 * Created  on 12.03.2018.
 *
 */

public class RoboticEnemy extends Enemy {
    private static final String TAG = "RoboEnemy";
    private static Bitmap[] roboImages;
    private static ArrayList<RoboticEnemy> enemyList = new ArrayList<>();

    public RoboticEnemy(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    public RoboticEnemy(){}

    //This is the standard AI, other enemys will have their own way of trying to kill the player :>

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        Player player = (Player) obj;

        if(player.getPosX() < this.getPosX())
            this.setPosX(this.getPosX() - this.getSpeedX()); //why not use saved/declared X speed? so enemies can have different speed (same as you suggested in cloud class)
        else if(player.getPosX() > this.getPosX())
            this.setPosX(this.getPosX() + this.getSpeedX());

        if(player.getPosY() < this.getPosY())
            this.setPosY(this.getPosY() - this.getSpeedY());
        else if(player.getPosY() > this.getPosY())
            this.setPosY(this.getPosY() + this.getSpeedY());
    }

    public static void updateAll(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        for (int i = 0; i < getEnemyList().size(); i++){
            getEnemyList().get(i).update(obj,goUp,goForward);
        }
    }

    @Override
    public void createRandomEnemies(int numberOfRobos){
        for (int i = 0; i < numberOfRobos; i++){
            getEnemyList().add(new RoboticEnemy(RandomHandler.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH),
                    RandomHandler.getRandomInt(GameViewActivity.GAME_HEIGHT / 2, GameViewActivity.GAME_HEIGHT),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedXmin, Constants.Actors.Enemy.speedXmax),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedYmin, Constants.Actors.Enemy.speedYmax),
                    null, Constants.Actors.Enemy.defaultRotation, "RoboEnemy " + i));
            //img can be null, dynamic-bitmaps can be accessed by the static field roboImages, which gets initialized at start
        }
    }

    //in my opinion, a simple bitmap array would match the animation the best!
    //but we surely should do something to slow it down
    /** Single enemy should not draw all of them (not object-oriented) */
    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (int i = 0; i < roboImages.length; i++) {
            canvas.drawBitmap(roboImages[i], (int) this.getPosX(), (int) this.getPosY(), null);
        }
    }

    public static void drawAll(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (RoboticEnemy e : getEnemyList()) {
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
                roboImages = new Bitmap[2];
                                                                                                                                            //percentage, just for testing now
                roboImages[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.black_enemy_robotic), 64, 64, false);
                roboImages[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.black_enemy_robotic), 64, 64, false);

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
        roboImages = null;
        return false;
    }

    /** GETTER / SETTER */

    public static Bitmap[] getRoboImages() {
        return roboImages;
    }

    public static void setRoboImages(Bitmap[] roboImages) {
        RoboticEnemy.roboImages = roboImages;
    }

    public static ArrayList<RoboticEnemy> getEnemyList() {
        return enemyList;
    }

    public static void setEnemyList(ArrayList<RoboticEnemy> enemyList) {
        RoboticEnemy.enemyList = enemyList;
    }

}
