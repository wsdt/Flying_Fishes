package yourowngame.com.yourowngame.gameEngine;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

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
     * @param bitmapOne     -> first Bitmap
     * @param x             -> x value of first Bitmap
     * @param y             -> y value of first Bitmap
     * @param bitmapTwo     -> second Bitmap
     * @param xx            -> x value of second Bitmap
     * @param yy            -> y value of second Bitmap
     * @return              -> returns true if 1 pixel of both bitmaps is no transparent (-> hitsTheGround)
     */
    public static boolean checkForCollision(Bitmap bitmapOne, int x, int y, Bitmap bitmapTwo, int xx, int yy){



        Rect bounds1 = new Rect(x, y, x+bitmapOne.getWidth(), y+bitmapOne.getHeight());
        Rect bounds2 = new Rect(xx, yy, xx+bitmapTwo.getWidth(), yy+bitmapTwo.getHeight());

        if(Rect.intersects(bounds1, bounds2)){
            Rect collisionArea = getCollisionArea(bounds1, bounds2);

            for(int i = collisionArea.left; i < collisionArea.right; i++){
                for (int j = collisionArea.top; j < collisionArea.bottom; i++){
                    int bitmap1Pixel = bitmapOne.getPixel(i-x, j-y);
                    int bitmap2Pixel = bitmapTwo.getPixel(i -xx, j - yy);
                    if(hasNoTransparentBackground(bitmap1Pixel) && hasNoTransparentBackground(bitmap2Pixel))
                        return true;
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
        int bottom = Math.max(rect1.bottom, rect2.bottom);

        return new Rect(left, top, right, bottom);
    }

    /**
     * @param pixel returns true if pixel is not transparent
     */
    private static boolean hasNoTransparentBackground(int pixel){
        return pixel != Color.TRANSPARENT;
    }

}
