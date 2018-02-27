package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;
import yourowngame.com.yourowngame.gameEngine.GameView;


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
    public void update(GameObject obj, @Nullable Boolean goUp, @Nullable Boolean goForward) {

    }

    @Override
    public boolean collision(View view, GameObject obj) {
        return false;
    }

    @Override
    public void draw(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {

    }

    public static void drawAllEnemies(@NonNull Activity activity, @NonNull Canvas canvas, long loopCount) throws NoDrawableInArrayFound_Exception {
        for (Enemy enemy : getEnemys()) {
            enemy.draw(activity, canvas, loopCount);
        }
        if (getEnemys().size() <= 0) { //i am sure this will happen to us ^^
            Log.w(TAG, "drawAllEnemies: No enemies found. Maybe you did not call createRandomEnemies()!");
        }
    }

    //So we have all parameters we need quite compact in the GameView.class (by creation)
    public void createRandomEnemies(GameView gameView, int numberOfEnemys, int[] img, @Nullable String name){
        //add enemy to list
        for(int i=0; i <= numberOfEnemys; i++) {
            Enemy.enemyList.add(new Enemy(
                    gameView.randomX(), gameView.randomY(),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedXmin, Constants.Actors.Enemy.speedXmax),
                    RandomHandler.getRandomFloat(Constants.Actors.Enemy.speedYmin, Constants.Actors.Enemy.speedYmax),
                    img, Constants.Actors.Enemy.defaultRotation, name));
        }
    }

    //Returns the enemyList, ready for drawing, rendering etc.
    public static List<Enemy> getEnemys(){
        return enemyList;
    }

    /**
     * And thats what i meant, GameObjects-update() method should be available for all members
     * currently the method is only suitable for the player class.
     *
     * So we need to put the update() Logic from GameObject into the Player.class
     * and offer a method that suits ALL subclasses.
     *
     * (So this method will later be the update() Method from the Enemy.class)
     */

    public void aimToPlayer(Player player) {
        this.player = player; //not really necess

        // move closer to Player
        // 1 circle, run through all the enemys, see how they can optimate their position
        // current speed per circle (Constants.Actors.Enemy.MOVING_SPEED = 10)
        for (int i = 0; i < enemyList.size(); i++){

            if(player.getPosX() < enemyList.get(i).getPosX())
                enemyList.get(i).setPosX(enemyList.get(i).getPosX() - enemyList.get(i).getSpeedX()); //why not use saved/declared X speed? so enemies can have different speed (same as you suggested in cloud class)
            else if(player.getPosX() > enemyList.get(i).getPosX())
                enemyList.get(i).setPosX(enemyList.get(i).getPosX() + enemyList.get(i).getSpeedX());

            if(player.getPosY() < enemyList.get(i).getPosY())
                enemyList.get(i).setPosX(enemyList.get(i).getPosX() - enemyList.get(i).getSpeedX());
            else if(player.getPosY() > enemyList.get(i).getPosY())
                enemyList.get(i).setPosX(enemyList.get(i).getPosX() + enemyList.get(i).getSpeedX());
        }
    }
}
