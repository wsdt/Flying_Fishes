package yourowngame.com.yourowngame.classes.manager;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.actors.projectiles.Projectile;
import yourowngame.com.yourowngame.classes.counters.FruitCounter;
import yourowngame.com.yourowngame.classes.counters.HighScore;
import yourowngame.com.yourowngame.classes.gamelevels.Level;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;


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

    /**********************************************************************
     * NESTED CLASS - CollisionDetection                                  *
     *                                                                    *
     * Provides methods for collision calculation                         *
     **********************************************************************/
    public static class CollisionDetection {
        private static final String TAG = "Collision";

        /** private Access, no instantiation!*/
        private CollisionDetection(){}

        /** Check Collision: Player to Enemy*/
        public static boolean checkCollision(@NonNull Player player, @NonNull Enemy enemy) {
            Bitmap playerBitmap = player.getCurrentBitmap();
            Bitmap enemyBitmap = enemy.getCurrentBitmap();

            //Only check for collision if bitmaps are not null (if null, then just return that no collision happened)
            if (playerBitmap != null && enemyBitmap != null) {
                //when constraint above ok, then assign rest
                int playerPosX = (int) player.getPosX();
                int playerPosY = (int) player.getPosY();
                int enemyPosX = (int) enemy.getPosX();
                int enemyPosY = (int) enemy.getPosY();

                Rect bounds1 = new Rect(playerPosX, playerPosY, playerPosX + playerBitmap.getWidth(), playerPosY + playerBitmap.getHeight());
                Rect bounds2 = new Rect(enemyPosX, enemyPosY, enemyPosX + enemyBitmap.getWidth(), enemyPosY + enemyBitmap.getHeight());

                if (Rect.intersects(bounds1, bounds2)) {
                    Rect collisionArea = getCollisionArea(bounds1, bounds2);

                    try {
                        for (int i = collisionArea.left; i < collisionArea.right; i++) {
                            for (int j = collisionArea.top; j < collisionArea.bottom; j++) {
                                int bitmap1Pixel = playerBitmap.getPixel(i - playerPosX, j - playerPosY);
                                int bitmap2Pixel = enemyBitmap.getPixel(i - enemyPosX, j - enemyPosY);
                                if (hasNoTransparentBackground(bitmap1Pixel) && hasNoTransparentBackground(bitmap2Pixel))
                                    return true;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        //TODO: Only temporary (we should solve this problem!)
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }

        /** Check Collision: Projectile to Enemy*/
        public static boolean checkCollision(@NonNull Enemy enemy, @NonNull Projectile projectile) {
            Bitmap enemyBitmap = enemy.getCurrentBitmap();
            Bitmap projectileBitmap = projectile.getCurrentBitmap();

            if (enemyBitmap != null && projectileBitmap != null) {

                int enemyPosX = (int) enemy.getPosX();
                int enemyPosY = (int) enemy.getPosY();
                int projectilePosX = (int) projectile.getPosX();
                int projectilePosY = (int) projectile.getPosY();

                Rect bounds1 = new Rect(enemyPosX, enemyPosY, enemyPosX + enemyBitmap.getWidth(), enemyPosY + enemyBitmap.getHeight());
                Rect bounds2 = new Rect(projectilePosX, projectilePosY, projectilePosX + projectileBitmap.getWidth(), projectilePosY + projectileBitmap.getHeight());

                if (Rect.intersects(bounds1, bounds2)) {
                    Rect collisionArea = getCollisionArea(bounds1, bounds2);

                    try {
                        for (int i = collisionArea.left; i < collisionArea.right; i++) {
                            for (int j = collisionArea.top; j < collisionArea.bottom; j++) {
                                int bitmap1Pixel = enemyBitmap.getPixel(i - enemyPosX, j - enemyPosY);
                                int bitmap2Pixel = projectileBitmap.getPixel(i - projectilePosX, j - projectilePosY);
                                if (hasNoTransparentBackground(bitmap1Pixel) && hasNoTransparentBackground(bitmap2Pixel))
                                    return true;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        //TODO: Only temporary (we should solve this problem!)
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }

        /** Check Collision: Player to Fruit*/
        public static boolean checkCollision(@NonNull Player player, @NonNull Fruit fruit) {
            Bitmap playerBitmap = player.getCurrentBitmap();
            Bitmap fruitBitmap = fruit.getCurrentBitmap();

            //Only check for collision if bitmaps are not null (if null, then just return that no collision happened)
            if (playerBitmap != null && fruitBitmap != null) {
                //when constraint above ok, then assign rest
                int playerPosX = (int) player.getPosX();
                int playerPosY = (int) player.getPosY();
                int fruitPosX = (int) fruit.getPosX();
                int fruitPosY = (int) fruit.getPosY();

                Rect bounds1 = new Rect(playerPosX, playerPosY, playerPosX + playerBitmap.getWidth(), playerPosY + playerBitmap.getHeight());
                Rect bounds2 = new Rect(fruitPosX, fruitPosY, fruitPosX + fruitBitmap.getWidth(), fruitPosY + fruitBitmap.getHeight());

                if (Rect.intersects(bounds1, bounds2)) {
                    Rect collisionArea = getCollisionArea(bounds1, bounds2);

                    try {
                        for (int i = collisionArea.left; i < collisionArea.right; i++) {
                            for (int j = collisionArea.top; j < collisionArea.bottom; j++) {
                                int bitmap1Pixel = playerBitmap.getPixel(i - playerPosX, j - playerPosY);
                                int bitmap2Pixel = fruitBitmap.getPixel(i - fruitPosX, j - fruitPosY);
                                if (hasNoTransparentBackground(bitmap1Pixel) && hasNoTransparentBackground(bitmap2Pixel))
                                    return true;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        //TODO: Only temporary (we should solve this problem!)
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }

        /**
         * @param rect1 area of colliding bitmap One
         * @param rect2 area of colliding bitmap Two
         * @return Returns the area, in which the two bitmaps are colliding
         */
        private static Rect getCollisionArea(Rect rect1, Rect rect2){
            int left =   Math.max(rect1.left, rect2.left);
            int top =    Math.max(rect1.top, rect2.top);
            int right =  Math.min(rect1.right, rect2.right);
            int bottom = Math.min(rect1.bottom, rect2.bottom);

            return new Rect(left, top, right, bottom);
        }

        /**
         * @param pixel returns true if pixel is not transparent
         */
        private static boolean hasNoTransparentBackground(int pixel){
            return pixel != Color.TRANSPARENT;
        }

        /** Collision Sound effects */
        public static void playProjectileEnemyCollisionSound(@NonNull Context context) {
            //TODO: [search for sound res] --> CollisionDetection.soundMgr.play(context, R.raw.collisionSound1,false);
        }
        public static void playPlayerEnemyCollisionSound(@NonNull Context context) {
            //TODO: [search for sound res] --> CollisionDetection.soundMgr.play(context, R.raw.collisionSound2,false);
        }
    }

}
