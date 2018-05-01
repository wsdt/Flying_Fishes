package yourowngame.com.yourowngame.classes.actors.enemy.specializations;

import android.app.Activity;
import android.graphics.Bitmap;
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
import yourowngame.com.yourowngame.classes.manager.RandomMgr;

/**
 * Created  on 12.03.2018.
 *
 */

public class HappenEnemy extends Enemy {
    private static final String TAG = "RoboEnemy";
    private static Bitmap[] images;
    private static ArrayList<HappenEnemy> enemyList = new ArrayList<>();

    public HappenEnemy(double posX, double posY, double speedX, double speedY, @NonNull int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    public HappenEnemy(){}

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
    public void createRandomEnemies(int numberOfHappens){
        for (int i = 0; i < numberOfHappens; i++){
            getEnemyList().add(new HappenEnemy(RandomMgr.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 100),
                    RandomMgr.getRandomInt(0, GameViewActivity.GAME_HEIGHT + 100),
                    RandomMgr.getRandomFloat(SPEED_X_MIN, SPEED_X_MAX),
                    RandomMgr.getRandomFloat(SPEED_Y_MIN, SPEED_Y_MAX),
                    new int[] {R.drawable.enemy_happen_1, R.drawable.enemy_happen_2,
                            R.drawable.enemy_happen_3,R.drawable.enemy_happen_2}, DEFAULT_ROTATION, "Robotic"));

            getEnemyList().get(i).setCurrentBitmap(getImages()[0]);
        }
    }

    //in my opinion, a simple bitmap array would match the animation the best!
    //but we surely should do something to slow it down
    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (int i = 0; i < getImages().length; i++) {
            canvas.drawBitmap(getImages()[i], (int) this.getPosX(), (int) this.getPosY(), null);
        }
    }

    public static void drawAll(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (HappenEnemy e : getEnemyList()) {
            Log.d(TAG, "Enemy X | Y : " + e.getPosX() + "|" + e.getPosY());

            e.draw(activity,canvas,loopCount);
        }
    }

    @Override
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        try {
            if (allObjs != null && !isInitialized) {
                if (allObjs[0] instanceof Activity) {
                    Activity activity = (Activity) allObjs[0];
                    setImages(new Bitmap[this.getImg().length]);

                    for (int imgFrame = 0; imgFrame < this.getImg().length; imgFrame++) {
                        getImages()[imgFrame] = this.getCraftedDynamicBitmap(activity, imgFrame, DEFAULT_ROTATION, null, null);
                    }
                } else {
                    Log.d(TAG, "Happen-Enemy: Initialize Failure!");
                }
                Log.d(TAG, "Happen-Enemy: Successfully initialized!");
                isInitialized = true;
            }
        } catch (NoDrawableInArrayFound_Exception | ClassCastException | NullPointerException e) {
            Log.d(TAG, "Happen-Enemy: Initialize Failure!");
            e.printStackTrace();
        }
        return isInitialized;
    }

    @Override
    public boolean cleanup() {
        resetWidthAndHeightOfEnemy(); //just reset y/x
        return true;
    }

    /** GETTER / SETTER */
    public static ArrayList<HappenEnemy> getEnemyList() {
        return enemyList;
    }

    public static void setEnemyList(ArrayList<HappenEnemy> enemyList) {
        HappenEnemy.enemyList = enemyList;
    }

    public static Bitmap[] getImages() {
        return images;
    }

    public static void setImages(Bitmap[] images) {
        HappenEnemy.images = images;
    }

    /** Get reward method for highscore */
    @Override
    public int getReward() {
        return REWARDS.HAPPEN_ENEMY;
    }
}
