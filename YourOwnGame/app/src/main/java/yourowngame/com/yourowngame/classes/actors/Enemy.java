package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.annotations.TestingPurpose;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;
import yourowngame.com.yourowngame.gameEngine.GameView;

/**
 * So we've been discussing about that class for quite a long time, going for bug to bug..
 *
 * I've now made two subclasses (empty) and i think they should be empty for now,
 * until we've solved our design-problem.
 *
 * We should now start from the beginning, first attempt to create the enemy class
 * was a total disaster, no need to build on that.
 *
 * I deleted the singletone, just causes problems.
 *
 * So basically we could have several possibilities,
 *
 * we could:
 *
 * (1) create enemys during runtime, every f.e 3 seconds spawns a new enemy
 * createRandomEnemy() -> addToList (in GameView) -> update/draw() the list
 *
 * (2) create enemys BEFORE runtime, then just spawn them (which i would prefer)
 * as weve already talked about, level 1 - 50 enemies
 *                               level 2 - 100 enemies ...
 *
 *
 *
 */

// If we make new realizations (super enemy etc., then we should make this abstract)
public abstract class Enemy extends GameObject {
    private static final String TAG = "Enemy";
    private List<Enemy> enemyList = new ArrayList<>(); //do not make that static, already Singleton class
    private Player player;

    // PRE LOADED ---------------
    private HashMap<String, Bitmap> loadedBitmaps = new HashMap<>();


    public Enemy(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {}


    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {}

    public void createRandomEnemies(int numberOfEnemys, int[] img) {

    }

    //that method has something
    private Enemy returnRandomEnemy(int[] img) {
        return new Enemy(
                RandomHandler.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 200),
                RandomHandler.getRandomInt(GameViewActivity.GAME_HEIGHT / 2, GameViewActivity.GAME_HEIGHT),
                RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedXmin, Constants.Actors.Enemy.speedXmax),
                RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedYmin, Constants.Actors.Enemy.speedYmax),
                img, Constants.Actors.Enemy.defaultRotation, "Random enemy: "+RandomHandler.getRandomInt(0,100000)/*Just for generating random id (debugging)*/);
    }

    @Override @SafeVarargs
    public final <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        try {
            if (allObjs != null) {
                Log.d(TAG, "initialize: AllObj: "+allObjs[0]);
                if (allObjs[0] instanceof Activity) {
                    Log.d(TAG, "initialize: Starting with bitmap extraction.");
                    Activity activity = (Activity) allObjs[0];

                    /*Load all bitmaps [load all rotations and all images from array] -------------------
                    * String of hashmap has following pattern: */

                    //Outside loop (because same hashmap, same references!)
                    for (int imgFrame = 0; imgFrame < this.getImg().length; imgFrame++) {
                            /*@Jori: Please read comment in createBitmap()*/
                        loadedBitmaps.put(Constants.Actors.Enemy.rotationFlyingUp + "_" + imgFrame, this.getCraftedDynamicBitmap(activity, imgFrame, Constants.Actors.Enemy.rotationFlyingUp, Constants.Actors.Enemy.widthPercentage, Constants.Actors.Enemy.heightPercentage)/*createBitmap(activity, currentEnemy, imgFrame)*/);
                        loadedBitmaps.put(Constants.Actors.Enemy.rotationFlyingDown + "_" + imgFrame, this.getCraftedDynamicBitmap(activity, imgFrame, Constants.Actors.Enemy.rotationFlyingDown, Constants.Actors.Enemy.widthPercentage, Constants.Actors.Enemy.heightPercentage));
                        loadedBitmaps.put(Constants.Actors.Enemy.defaultRotation + "_" + imgFrame, this.getCraftedDynamicBitmap(activity, imgFrame, Constants.Actors.Enemy.defaultRotation, Constants.Actors.Enemy.widthPercentage, Constants.Actors.Enemy.heightPercentage));
                        Log.d(TAG, "initialize: Generated bitmaps->"+Constants.Actors.Enemy.rotationFlyingUp + "_" + imgFrame+"//"+
                                Constants.Actors.Enemy.rotationFlyingDown + "_" + imgFrame+"//"+
                                Constants.Actors.Enemy.defaultRotation + "_" + imgFrame);
                    }

                    //Now set hashmap for all enemies
                    for(Enemy currentEnemy : getEnemys()) {
                        Log.d(TAG, "enemy length = " + getEnemys().size());
                        currentEnemy.setLoadedBitmaps(loadedBitmaps);
                    }
                }
            } else {return false;}
        } catch (ClassCastException | NullPointerException | NoDrawableInArrayFound_Exception e) {
            //This should never be thrown! Just check in try block if null and if instance of to prevent issues!
            Log.e(TAG, "initialize: Initializing of Enemy object FAILED! See error below.");
            e.printStackTrace();
            return false;
        }
        Log.d(TAG, "initialize Enemy: SUCCESS");

        return true;
    }

    @Override
    public boolean cleanup() {
        this.setLoadedBitmaps(null);
        return true;
    }

    /***********************
     *    AI & such stuff  *
     ***********************/
    public void aimToPlayer(Player player) {
        this.player = player; //not really necess

        for (int i = 0; i < enemyList.size(); i++){
            if(player.getPosX() < enemyList.get(i).getPosX())
                enemyList.get(i).setPosX(enemyList.get(i).getPosX() - enemyList.get(i).getSpeedX()); //why not use saved/declared X speed? so enemies can have different speed (same as you suggested in cloud class)
            else if(player.getPosX() > enemyList.get(i).getPosX())
                enemyList.get(i).setPosX(enemyList.get(i).getPosX() + enemyList.get(i).getSpeedX());

            if(player.getPosY() < enemyList.get(i).getPosY())
                enemyList.get(i).setPosY(enemyList.get(i).getPosY() - enemyList.get(i).getSpeedY());
            else if(player.getPosY() > enemyList.get(i).getPosY())
                enemyList.get(i).setPosY(enemyList.get(i).getPosY() + enemyList.get(i).getSpeedY());
        }
    }

    /************************
     *   GETTER & SETTER    *
     ************************/

    //Returns the enemyList, ready for drawing, rendering etc.
    public List<Enemy> getEnemys(){
        return enemyList;
    }

    public HashMap<String, Bitmap> getLoadedBitmaps() {
        return loadedBitmaps;
    }

    public void setLoadedBitmaps(HashMap<String, Bitmap> loadedBitmaps) {
        this.loadedBitmaps = loadedBitmaps;
    }
}
