package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;

/**
 * Created on 12.03.2018.
 */

public class SuperEnemy extends Enemy {

    private static Bitmap[] superBitmaps;
    private List<SuperEnemy> superEnemyList = new ArrayList<>();
    private final String TAG = "SuperEnemy";

    public SuperEnemy(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    public SuperEnemy(){};

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {
        //i know, i know, that looks quite dumb for a superenemy ^^ just to do sth other haha
        Player player = (Player) obj;

        for (int i = 0; i < this.getSuperEnemyList().size(); i++){
            getSuperEnemyList().get(i).setPosX(getSuperEnemyList().get(i).getPosX() - this.getSuperEnemyList().get(i).getSpeedX());
        }

  /*      for (int i = 0; i < this.getSuperEnemyList().size(); i++){

            if(player.getPosX() < this.getSuperEnemyList().get(i).getPosX())
                this.getSuperEnemyList().get(i).setPosX(this.getSuperEnemyList().get(i).getPosX() - this.getSuperEnemyList().get(i).getSpeedX()); //why not use saved/declared X speed? so enemies can have different speed (same as you suggested in cloud class)
            else if(player.getPosX() > this.getSuperEnemyList().get(i).getPosX())
                this.getSuperEnemyList().get(i).setPosX(this.getSuperEnemyList().get(i).getPosX() + this.getSuperEnemyList().get(i).getSpeedX());

            if(player.getPosY() < this.getSuperEnemyList().get(i).getPosY())
                this.getSuperEnemyList().get(i).setPosY(this.getSuperEnemyList().get(i).getPosY() - this.getSuperEnemyList().get(i).getSpeedY());
            else if(player.getPosY() > this.getSuperEnemyList().get(i).getPosY())
                this.getSuperEnemyList().get(i).setPosY(this.getSuperEnemyList().get(i).getPosY() + this.getSuperEnemyList().get(i).getSpeedY());
        }
        */
    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (SuperEnemy sE : superEnemyList) {
            Log.d(TAG, "Enemy 22222222 X | Y : " + sE.getPosX() + "|" + sE.getPosY());

            for (int i = 0; i < superBitmaps.length; i++) {
                canvas.drawBitmap(superBitmaps[i], (int) sE.getPosX(), (int) sE.getPosY(), null);
            }
        }
    }

    @Override
    public void createRandomEnemies(int numberOfEnemies) {
        for (int i = 0; i < numberOfEnemies; i++){
            superEnemyList.add(new SuperEnemy(RandomHandler.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH),
                RandomHandler.getRandomInt(GameViewActivity.GAME_HEIGHT / 2, GameViewActivity.GAME_HEIGHT),
                RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedXmin, Constants.Actors.Enemy.speedXmax),
                RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedYmin, Constants.Actors.Enemy.speedYmax),
                null, Constants.Actors.Enemy.defaultRotation, "SuperEnemy " + i));
        }
    }


    @Override
    public <OBJ> boolean initialize(@Nullable OBJ... allObjs) {
        if (allObjs != null) {
            if (allObjs[0] instanceof Activity) {
                Activity activity = (Activity) allObjs[0];
                superBitmaps = new Bitmap[2];
                                                                                                                                    // same here, percentage, just for testing now
                superBitmaps[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bomb2), 64, 64, false);
                superBitmaps[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bomb2), 64, 64, false);

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
        superEnemyList = null;
        return false;
    }

    public static Bitmap[] getSuperBitmaps() {
        return superBitmaps;
    }

    public static void setSuperBitmaps(Bitmap[] superBitmaps) {
        SuperEnemy.superBitmaps = superBitmaps;
    }

    public List<SuperEnemy> getSuperEnemyList() {
        return superEnemyList;
    }

    public void setSuperEnemyList(List<SuperEnemy> superEnemyList) {
        this.superEnemyList = superEnemyList;
    }

    public String getTAG() {
        return TAG;
    }
}
