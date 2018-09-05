package yourowngame.com.yourowngame.classes.manager;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Iterator;

import yourowngame.com.yourowngame.classes.actors.GameObject;
import yourowngame.com.yourowngame.classes.actors.barriers.Barrier;
import yourowngame.com.yourowngame.classes.actors.barriers.BarrierMgr;
import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.projectiles.Projectile;
import yourowngame.com.yourowngame.classes.actors.projectiles.ProjectileMgr;
import yourowngame.com.yourowngame.classes.game_modes.DrawableLevel;
import yourowngame.com.yourowngame.classes.observer.Observer_HighScore;
import yourowngame.com.yourowngame.classes.game_modes.mode_adventure.Level;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;


/**
 * Collision Manager will check all forms of Collisions and react to it.
 *
 * Only resetPos() ! Do not resetSpeed or similar as they would get modified by fruit powers etc.
 */

public class CollisionMgr {
    private final String TAG = "CollisionMgr";
    private DrawableLevel currLevel;
    private Context context;
    private Observer_HighScore highScore;

    public CollisionMgr(DrawableLevel level, Context context, Observer_HighScore highScore){
        this.currLevel = level;
        this.context = context;
        this.highScore = highScore;
    }


    public boolean checkForCollisions(){
        playerToFruitCollision();
        projectileToEnemyCollision();
        playerToBarrierCollision();

        return playerToEnemyCollision() || playerToBorderCollision() || playerToBarrierCollision();
    }

    /** check Projectile-to-Enemy collision */
    private void projectileToEnemyCollision(){
        for (Enemy e : currLevel.getEnemies()){
            for (Iterator<Projectile> it = ProjectileMgr.getShotProjectiles().iterator(); it.hasNext();){
                Projectile p = it.next();

                if(CollisionDetection.checkCollision(e, p)){
                    //enemy dies, spawns on the other side
                    e.resetPos();
                    //projectile needs to be deleted and reloaded into other lists (also here otherwise when enemy shot the bullet would never reload)
                    ProjectileMgr.reuseBullet(p,it);

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
        for (Fruit fruit : currLevel.getFruits()) {
            if (CollisionDetection.checkCollision(currLevel.getPlayer(), fruit)) {
                //increment highScore
                highScore.increment(fruit);
                //activate the fruits power
                fruit.getFruitPowers().get(0).execute();
                //add collected Fruit
                Level.getLevelFruitCounter().fruitCollected(fruit);
                //reset fruit
                fruit.resetPos();
                Log.e(TAG, "Player collected a fruit.");
            }
            //fruits has left the screen, will rejoin
            if(fruit.isNotVisible()){
                fruit.cleanup();
            }

            Log.d(TAG, "Fruit " + fruit + " = " + fruit.getPosX());
        }
    }

    /** check Player-to-Enemy Collision */
    private boolean playerToEnemyCollision() {
        for (Enemy e : currLevel.getEnemies()) {
            if (CollisionDetection.checkCollision(currLevel.getPlayer(), e)) {
                CollisionDetection.playPlayerEnemyCollisionSound(context);
                return true;
            }
        }
        return false;
    }

    /** check Player to Barrier Collision */
    private boolean playerToBarrierCollision() {
        for (Barrier b : BarrierMgr.getBarrierList()){
            if (CollisionDetection.checkCollision(currLevel.getPlayer(), b)){
                //todo that sound needs to be hilarious!! haha
                return true;
            }
        }
        return false;
    }

    /** check Player to Border Collision*/
    public boolean playerToBorderCollision() {                                          //we need the height of the bitmap here, didn't had any time left sorry
        if (currLevel.getPlayer().getWidthOfBitmap() > DrawableSurfaces.getDrawHeight() || currLevel.getPlayer().getPosY() < 0) {
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

        /** Check Collision of two gameObjs. */
        public static boolean checkCollision(@NonNull GameObject obj1, @NonNull GameObject obj2) {
            Bitmap playerBitmap = obj1.getCurrentBitmap();
            Bitmap enemyBitmap = obj2.getCurrentBitmap();

            //Only check for collision if bitmaps are not null (if null, then just return that no collision happened)
            if (playerBitmap != null && enemyBitmap != null) {
                //when constraint above ok, then assign rest
                int playerPosX = (int) obj1.getPosX();
                int playerPosY = (int) obj1.getPosY();
                int enemyPosX = (int) obj2.getPosX();
                int enemyPosY = (int) obj2.getPosY();

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
