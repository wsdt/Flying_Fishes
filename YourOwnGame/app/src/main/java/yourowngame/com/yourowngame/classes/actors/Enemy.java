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
import java.util.List;
import java.util.Random;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;
import yourowngame.com.yourowngame.gameEngine.GameView;

/** PLEASE READ
 *
 * So basically we're doing the following now (based on whatsapp)
 *
 * We're creating a Singleton-Enemy.class, which provides Enemies for each level
 * The Enemies are getting created in the @createRandomEnemies() method.
 *
 * If other classes need a reference on that, just use
 *
 * getInstance().getEnemys()
 *
 * that will always return the enemys for the current level.
 *
 */


public class Enemy extends GameObject {
    private static final String TAG = "Enemy";
    private static Enemy INSTANCE;
    private List<Enemy> enemyList = new ArrayList<>(); //do not make that static, already Singleton class
    private Player player;

    // PRE LOADED ---------------
    private HashMap<String, Bitmap> loadedBitmaps;


    public static Enemy getInstance() {
        return INSTANCE == null ? INSTANCE = new Enemy() : INSTANCE;
    }

    public Enemy(double posX, double posY, double speedX, double speedY, int[] img, float rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    private Enemy(){};

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {}


    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (Enemy enemy : getEnemys()) {
            //set the bitmap, here is the bug, getLoadedBitmaps() returns null, need to sleep now, im done haha
            //does also not belong here!
            enemy.setCurrentBitmap(enemy.getLoadedBitmaps().get(enemy.getRotationDegree() + "_" + ((int) loopCount % enemy.getImg().length)));
            Log.d(TAG, "Enemy getBitmap = " + enemy.getLoadedBitmaps().get(enemy.getRotationDegree() + "_" + ((int) loopCount % enemy.getImg().length)));
            //draw the enemy
            canvas.drawBitmap(enemy.getCurrentBitmap(), (int) enemy.getPosX(), (int) enemy.getPosY(), null);
        }
    }


    @Deprecated //do not use
    public void drawAllEnemies(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (Enemy enemy : getEnemys()) {
            enemy.draw(activity, canvas, loopCount);
        }
        if (getEnemys().size() <= 0) { //i am sure this will happen to us ^^
            Log.w(TAG, "drawAllEnemies: No enemies found. Maybe you did not call createRandomEnemies()!");
        }
    }


    //TODO: Current design says this method is called in init() of GameView, but it would be in future maybe more object-oriented if we do that in initialize() below
    //TODO: i would say the method should be called separately.. but let's flip for it! :D
    public void createRandomEnemies(int numberOfEnemys, int[] img) {
        for (int i = 0; i <= numberOfEnemys; i++) {
            getInstance().enemyList.add(new Enemy(
                    RandomHandler.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 200),
                    RandomHandler.getRandomInt(GameViewActivity.GAME_HEIGHT / 2, GameViewActivity.GAME_HEIGHT),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedXmin, Constants.Actors.Enemy.speedXmax),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedYmin, Constants.Actors.Enemy.speedYmax),
                    img, Constants.Actors.Enemy.defaultRotation, "Enemy Nr._" + i+1));
        }
    }

    /** OBJ[0]: Activity
     *
     * We currently do only set the image for each created Enemy, nothing more!
     * Later on we could create the enemies here.. but lets get it working first
     *
     * WORKS NOW! Bug was in the getCraftedDynamicBitmap, which was all the time referencing our fucking getInstance, zum blean haha
     *
     * So we really need to optimize the craftBitmap methods, one here, the other one in the gameview.
     * */
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

                    loadedBitmaps = new HashMap<>();

                    for(Enemy currentEnemy : getEnemys()) {
                        Log.d(TAG, "enemy length = " + getEnemys().size());
                        Log.d(TAG, "enemy img length = " + currentEnemy.getImg().length);
                        for (int imgFrame = 0; imgFrame < currentEnemy.getImg().length; imgFrame++) {
                                //Just create the f* bitmap and pass it onto the currentEnemy
                                loadedBitmaps.put(Constants.Actors.Enemy.rotationFlyingUp + "_" + imgFrame, createBitmap(activity, currentEnemy, imgFrame));
                                Log.d(TAG, "Created a bitmap! = " + loadedBitmaps.size());

                                // NOT WORKING, @GetCraftedDynamicBitmap will not work here!
                                // loadedBitmaps.put(Constants.Actors.Enemy.rotationFlyingUp + "_" + imgFrame, getCraftedDynamicBitmap(activity, e.getImg()[imgFrame], Constants.Actors.Enemy.rotationFlyingUp, Constants.Actors.Enemy.widthPercentage, Constants.Actors.Enemy.heightPercentage));
                                // loadedBitmaps.put(Constants.Actors.Enemy.rotationFlyingDown + "_" + imgFrame, getCraftedDynamicBitmap(activity, e.getImg()[imgFrame], Constants.Actors.Enemy.rotationFlyingUp, Constants.Actors.Enemy.widthPercentage, Constants.Actors.Enemy.heightPercentage));
                                // Log.d(TAG, "Bitmap added" + loadedBitmaps.size());
                        }
                        currentEnemy.setLoadedBitmaps(loadedBitmaps);
                    }
                }
            } else {return false;}
        } catch (ClassCastException | NullPointerException e) {
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

    /** Class intern method for creating bitmaps, getCr...Bitmap wont work, because of this-reference
     * i just implemented a just-get-a-fucking-img-method, because we really need to enhance our current
     * getCraftedDynamicDrawable-method, neither did I exception handling*/
    public Bitmap createBitmap(Activity context, Enemy e, int imgFrame){
        return BitmapFactory.decodeResource(context.getResources(), e.getImg()[imgFrame]);
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
