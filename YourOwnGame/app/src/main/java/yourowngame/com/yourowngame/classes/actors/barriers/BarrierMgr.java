package yourowngame.com.yourowngame.classes.actors.barriers;

import android.app.Activity;
import android.util.Log;
import java.util.ArrayList;

/**
 * The BarrierMgr has an ArrayList that contains all Barriers, once a barrier is created, it gets added
 * once a barrier has passed the placer and is out of bounds, it gets removed
 *
 */


public class BarrierMgr {
    public static String TAG = "BarrierMgr";
    private static ArrayList<Barrier> barrierList; //we can do this better, static not really needed!

    private BarrierMgr(){}


    public static void spawnBarriers(Activity activity, int difficulty, int maxBarriersAtOnce){

        //here we can create custom Barriers...
    }


}
