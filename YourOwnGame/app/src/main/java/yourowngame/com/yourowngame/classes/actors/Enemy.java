package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public static Enemy getInstance() {
        return INSTANCE == null ? INSTANCE = new Enemy() : INSTANCE;
    }

    private Enemy(double posX, double posY, double speedX, double speedY, int[] img, float rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    private Enemy(){}

    @Override
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {}



    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        canvas.drawBitmap(this.getCraftedDynamicBitmap(activity, ((int) loopCount % this.getImg().length),
                this.getRotationDegree(), Constants.Actors.Player.widthPercentage, Constants.Actors.Player.heightPercentage), (int) this.getPosX(), (int) this.getPosY(), null);
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
    public void createRandomEnemies(GameView gameView, int numberOfEnemys, int[] img, @Nullable String name){
        //add enemy to list, numberOfEnemys-1 or the the enemy draws itself too!
        for(int i=0; i <= numberOfEnemys-1; i++) {
            Enemy.enemyList.add(new Enemy(
                                                                                                      // so 200 divided by the speed equals the amount of time, in which the enemys will spawn!
                    RandomHandler.getRandomInt(GameViewActivity.GAME_WIDTH, GameViewActivity.GAME_WIDTH + 200),
                    RandomHandler.getRandomInt(GameViewActivity.GAME_HEIGHT/2, GameViewActivity.GAME_HEIGHT),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedXmin, Constants.Actors.Enemy.speedXmax),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedYmin, Constants.Actors.Enemy.speedYmax),
                    img, Constants.Actors.Enemy.defaultRotation, name));
        }
    }
}
