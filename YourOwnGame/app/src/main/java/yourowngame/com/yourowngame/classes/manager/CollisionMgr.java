package yourowngame.com.yourowngame.classes.manager;


import android.content.Context;
import android.util.Log;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.counters.FruitCounter;
import yourowngame.com.yourowngame.classes.counters.HighScore;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;
import yourowngame.com.yourowngame.gameEngine.CollisionDetection;

/**
 * Collision Manager will check all forms of Collisions and react to it.
 */

public class CollisionMgr {
    private final String TAG = "CollisionMgr";
    private Level currLevel;
    private Context context;
    private HighScore highScore;

    public CollisionMgr(Level level, Context context, HighScore highScore){
        this.currLevel = level;
        this.context = context;
        this.highScore = highScore;
    }


    public boolean checkForCollisions(){
        playerToFruitCollision();
        projectileToEnemyCollision();

        return playerToEnemyCollision() || playerToBorderCollision();
    }

    /** check Projectile-to-Enemy collision */
    private void projectileToEnemyCollision(){
        for (Enemy e : currLevel.getAllEnemies()){
            for (int i = 0; i < currLevel.getPlayer().getProjectiles().size(); i++){
                if(CollisionDetection.checkCollision(e, currLevel.getPlayer().getProjectiles().get(i))){
                    //enemy dies, spawns on the other side
                    e.resetPos();
                    //projectile needs to be deleted
                    currLevel.getPlayer().getProjectiles().remove(currLevel.getPlayer().getProjectiles().get(i));
                    //play sound when enemy dies
                    CollisionDetection.playProjectileEnemyCollisionSound(context);
                    //increment the players highScore
                    highScore.increment(e);

                    Log.d(TAG, "HighScore = " + highScore.getValue());
                }
            }
        }
    }

    /** Check player to Fruit Collision*/
    private void playerToFruitCollision(){
        for (Fruit fruit : currLevel.getAllFruits()) {
            if (CollisionDetection.checkCollision(currLevel.getPlayer(), fruit)) {
                //increment highScore
                highScore.increment(fruit);
                //add collected Fruit
                FruitCounter.getInstance().fruitCollected(fruit);
                //reset fruit
                fruit.resetPos();
                Log.e(TAG, "Player collected a fruit.");
            }
            //fruits has left the screen, will rejoin
            if(fruit.hasLeftScreen()){
                fruit.resetPos();
            }

            Log.d(TAG, "Fruit " + fruit + " = " + fruit.getPosX());
        }
    }

    /** check Player-to-Enemy Collision */
    private boolean playerToEnemyCollision() {
        for (Enemy e : currLevel.getAllEnemies()) {
            if (CollisionDetection.checkCollision(currLevel.getPlayer(), e)) {
                CollisionDetection.playPlayerEnemyCollisionSound(context);
                return true;
            }
        }
        return false;
    }

    /** check Player to Border Collision*/
    public boolean playerToBorderCollision() {                                          //we need the height of the bitmap here, didn't had any time left sorry
        if (currLevel.getPlayer().getWidthOfPlayer() > GameViewActivity.GAME_HEIGHT || currLevel.getPlayer().getPosY() < 0) {
            return true;
        }
            return false;
    }
}
