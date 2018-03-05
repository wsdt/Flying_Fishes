package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Bitmap;
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

/**
 * during development rich value methods should be on Top
 * draw()
 * update()
 * hitsTheGround()
 * aimToPlayer()
 *
 *
 *
 *
 */


public class Enemy extends GameObject {
    private static final String TAG = "Enemy";
    private static Enemy INSTANCE;
    private static List<Enemy> enemyList = new ArrayList<>();
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


    // same bug here..
    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
//       this.setCurrentBitmap(this.getLoadedBitmaps().get(this.getRotationDegree()+"_"+((int) loopCount % this.getImg().length)));
//        canvas.drawBitmap(this.getCurrentBitmap(),(int) this.getPosX(), (int) this.getPosY(), null);
    }

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

    //Returns the enemyList, ready for drawing, rendering etc.
    public List<Enemy> getEnemys(){
        return enemyList;
    }

    public void drawAllEnemies(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (Enemy enemy : getEnemys()) {
            enemy.draw(activity, canvas, loopCount);
        }
        if (getEnemys().size() <= 0) { //i am sure this will happen to us ^^
            Log.w(TAG, "drawAllEnemies: No enemies found. Maybe you did not call createRandomEnemies()!");
        }
    }

    //So we have all parameters we need quite compact in the GameView.class (by creation)
    //TODO: Current design says this method is called in init() of GameView, but it would be in future maybe more object-oriented if we do that in initialize() below
    public void createRandomEnemies(GameView gameView, int numberOfEnemys, int[] img, @Nullable String name) {
        //add enemy to list, numberOfEnemys-1 or the the enemy draws itself too!
        for (int i = 0; i <= numberOfEnemys; i++) {
            Enemy.enemyList.add(new Enemy(
                    // so 200 divided by the speed equals the amount of time, in which the enemys will spawn!
                    RandomHandler.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 200),
                    RandomHandler.getRandomInt(GameViewActivity.GAME_HEIGHT / 2, GameViewActivity.GAME_HEIGHT),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedXmin, Constants.Actors.Enemy.speedXmax),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedYmin, Constants.Actors.Enemy.speedYmax),
                    img, Constants.Actors.Enemy.defaultRotation, name));

            //Enemy.enemyList.get(i).setImg(img);
        }
    }


    /** TODO what happens here really, we need to set the bitmaps for every Enemy e! this. -> wont work here, we've got no this, every Enemey e needs its own drawables, somehow...  */

    /** OBJ[0]: Activity */
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

                    HashMap<String, Bitmap> loadedBitmaps = new HashMap<>();

                    for(Enemy e : getEnemys()) {
                        Log.d(TAG, "enemy length = " + getEnemys().size());
                        for (int imgFrame = 0; imgFrame < e.getImg().length; imgFrame++) {

                                loadedBitmaps.put(Constants.Actors.Enemy.rotationFlyingUp + "_" + imgFrame, getCraftedDynamicBitmap(activity, e.getImg()[imgFrame], Constants.Actors.Enemy.rotationFlyingUp, Constants.Actors.Enemy.widthPercentage, Constants.Actors.Enemy.heightPercentage));
                                loadedBitmaps.put(Constants.Actors.Enemy.rotationFlyingDown + "_" + imgFrame, getCraftedDynamicBitmap(activity, e.getImg()[imgFrame], Constants.Actors.Enemy.rotationFlyingUp, Constants.Actors.Enemy.widthPercentage, Constants.Actors.Enemy.heightPercentage));
                                Log.d(TAG, "Bitmap added" + loadedBitmaps.size());
                        }
                        e.setLoadedBitmaps(loadedBitmaps);
                    }

                }
            } else {return false;}
        } catch (ClassCastException | NullPointerException | NoDrawableInArrayFound_Exception e) {
            //This should never be thrown! Just check in try block if null and if instance of to prevent issues!
            Log.e(TAG, "initialize: Initializing of Enemy object FAILED! See error below.");
            e.printStackTrace();
            return false;
        }
        Log.d(TAG, "initialize: Tried to initialize Enemy object!");

        return true;
    }

    @Override
    public boolean cleanup() {
        this.setLoadedBitmaps(null);
        return true;
    }

    //GETTER/SETTER -----------------------------------
    public HashMap<String, Bitmap> getLoadedBitmaps() {
        return loadedBitmaps;
    }

    public void setLoadedBitmaps(HashMap<String, Bitmap> loadedBitmaps) {
        this.loadedBitmaps = loadedBitmaps;
    }
}
