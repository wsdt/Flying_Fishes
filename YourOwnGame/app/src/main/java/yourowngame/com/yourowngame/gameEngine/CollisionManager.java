package yourowngame.com.yourowngame.gameEngine;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.classes.actors.Enemy;
import yourowngame.com.yourowngame.classes.actors.Player;

/**
 * Created  on 02.03.2018.
 *
 * The CollisionManager checks whether two bitmaps are
 * colliding or not, if they collide, then he gets the "area-of-colliding"
 * -> the pixels that overlap and if just 1 pixel of both images
 * does not contain transparent background, the hitsTheGround occurred.
 *
 */

public class CollisionManager {

    private static final String TAG = "Collision";

    /** private Access, no instantiation!*/
    private CollisionManager(){};

    /**
     * @param player     -> first Bitmap
     * @param enemy      -> x value of first Bitmap
     * @return           -> returns true if 1 pixel of both bitmaps is no transparent (-> hitsTheGround)
     */
    public static boolean checkForCollision(@NonNull Player player, @NonNull Enemy enemy) { //<-- more readable and less error-prone
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

}