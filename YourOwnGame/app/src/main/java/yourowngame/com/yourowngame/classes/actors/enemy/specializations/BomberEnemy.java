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
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;

/**
 * Created  on 12.03.2018.
 *
 */

public class BomberEnemy extends Enemy {
    private static final String TAG = "RoboEnemy";
    private static Bitmap[] images;
    private static ArrayList<BomberEnemy> enemyList = new ArrayList<>();

    public BomberEnemy(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);

        setPositivePoints(100);
        setNegativePoints(-100);
    }

    public BomberEnemy(){}

    //This is the standard AI, other enemys will have their own way of trying to kill the player :>

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        resetIfOutOfBounds();

        Player player = (Player) obj;

            if (player.getPosX() < this.getPosX())
                this.setPosX(this.getPosX() - this.getSpeedX()); //why not use saved/declared X speed? so enemies can have different speed (same as you suggested in cloud class)
            else if (player.getPosX() > this.getPosX())
                this.setPosX(this.getPosX() + this.getSpeedX());

            if (player.getPosY() < this.getPosY())
                this.setPosY(this.getPosY() - this.getSpeedY());
            else if (player.getPosY() > this.getPosY())
                this.setPosY(this.getPosY() + this.getSpeedY());


            //@TODO it somehow doesnt look smooth, i dont know, not good if they're overlapping and bouncing all the time
            //@TODO maybe for other enemys?

        //this.setPosX(this.getPosX() - getSpeedX()); for normal mode, just de-comment

    }

    public static void updateAll(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        for (int i = 0; i < getEnemyList().size(); i++){
            getEnemyList().get(i).update(obj,goUp,goForward);
        }
    }

    @Override
    public void createRandomEnemies(int numberOfRobos){
        for (int i = 0; i < numberOfRobos; i++){
            getEnemyList().add(new BomberEnemy(RandomHandler.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 100),
                    RandomHandler.getRandomInt(0, GameViewActivity.GAME_HEIGHT + 100),
                    RandomHandler.getRandomFloat(SPEED_X_MIN, SPEED_X_MAX),
                    RandomHandler.getRandomFloat(SPEED_Y_MIN, SPEED_Y_MAX),
                    null, DEFAULT_ROTATION, "Robotic"));

            getEnemyList().get(i).setCurrentBitmap(images[0]);
        }
    }

    //in my opinion, a simple bitmap array would match the animation the best!
    //but we surely should do something to slow it down
    /** Single enemy should not draw all of them (not object-oriented)
     * that's what i've always wanted to prevent..*/
    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (int i = 0; i < getImages().length; i++) {
            canvas.drawBitmap(getImages()[i], (int) this.getPosX(), (int) this.getPosY(), null);
        }
    }

    public static void drawAll(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (BomberEnemy e : getEnemyList()) {
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
                setImages(new Bitmap[1]);
                                                                                                                                            //percentage, just for testing now
                getImages()[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.flyingbombernew), 128, 128, false);

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
        setEnemyList(new ArrayList<BomberEnemy>());
        setImages(null);
        return true;
    }

    /** GETTER / SETTER */

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
