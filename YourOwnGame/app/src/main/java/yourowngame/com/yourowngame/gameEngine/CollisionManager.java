package yourowngame.com.yourowngame.gameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import yourowngame.com.yourowngame.classes.actors.enemy.Enemy;
import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.player.Player;
import yourowngame.com.yourowngame.classes.actors.projectiles.Projectile;
import yourowngame.com.yourowngame.classes.manager.SoundMgr;

/**
 * Created  on 02.03.2018.
 *
 * The CollisionManager checks whether two bitmaps are
 * colliding or not, if they collide, then he gets the "area-of-colliding"
 * -> the pixels that overlap and if just 1 pixel of both images
 * does not contain transparent background, the collision occurred.
 *
 */

public class CollisionManager {
    private static SoundMgr soundMgr = new SoundMgr(); //no setter
    private static final String TAG = "Collision";

    /** private Access, no instantiation!*/
    private CollisionManager(){};

    /**
     * @param player     -> first Bitmap
     * @param enemy      -> x getValue of first Bitmap
     * @return           -> returns true if 1 pixel of both bitmaps is no transparent (-> hitsTheGround)
     */
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

    public static boolean checkCollision(@NonNull Enemy enemy, @NonNull Projectile projectile) {
        Bitmap playerBitmap = enemy.getCurrentBitmap();
        Bitmap projectileBitmap = projectile.getCurrentBitmap();

        if (playerBitmap != null && projectileBitmap != null) {

            int enemyPosX = (int) enemy.getPosX();
            int enemyPosY = (int) enemy.getPosY();
            int projectilePosX = (int) projectile.getPosX();
            int projectilePosY = (int) projectile.getPosY();

            Rect bounds1 = new Rect(enemyPosX, enemyPosY, enemyPosX + playerBitmap.getWidth(), enemyPosY + playerBitmap.getHeight());
            Rect bounds2 = new Rect(projectilePosX, projectilePosY, projectilePosX + projectileBitmap.getWidth(), projectilePosY + projectileBitmap.getHeight());

            if (Rect.intersects(bounds1, bounds2)) {
                Rect collisionArea = getCollisionArea(bounds1, bounds2);

                try {
                    for (int i = collisionArea.left; i < collisionArea.right; i++) {
                        for (int j = collisionArea.top; j < collisionArea.bottom; j++) {
                            int bitmap1Pixel = playerBitmap.getPixel(i - enemyPosX, j - enemyPosY);
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
        //TODO: [search for sound res] --> CollisionManager.soundMgr.play(context, R.raw.collisionSound1,false);
    }
    public static void playPlayerEnemyCollisionSound(@NonNull Context context) {
        //TODO: [search for sound res] --> CollisionManager.soundMgr.play(context, R.raw.collisionSound2,false);
    }
}
