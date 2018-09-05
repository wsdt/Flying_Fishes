package yourowngame.com.yourowngame.classes.actors.barriers;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.annotation.IntRange;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.activities.MainActivity;
import yourowngame.com.yourowngame.classes.actors.barriers.specializastions.Barrier_Easy;
import yourowngame.com.yourowngame.classes.actors.barriers.specializastions.Barrier_Hard;
import yourowngame.com.yourowngame.classes.actors.barriers.specializastions.Barrier_Medium;
import yourowngame.com.yourowngame.classes.manager.RandomMgr;
import yourowngame.com.yourowngame.gameEngine.surfaces.GameView;

/**
 * The BarrierMgr has an ArrayList that contains all Barriers, once a barrier is created, it gets added
 * once a barrier has passed the placer and is out of bounds, it gets removed
 *
 */


public class BarrierMgr {
    public static String TAG = "BarrierMgr";
    private static ArrayList<Barrier> barrierList; //we can do this better, static not really needed!

    private BarrierMgr(){}

    /**
     * Automatically adds an barrier-obj to the list and its easy as that:
     * As long as there is an barrier on the surface, dont spawn any others. (Easy difficulty, in hard, we can surely spawn 2 together!)
     *
     * @param difficulty int-value (0-2) for difficulty, where 2 = hardest
     * @param maxBarriersAtOnce describes how many barriers approach on the screen
     *
     *                          but were passing the activity here...
     */
    public static void spawnBarriers(Activity activity, int difficulty, int maxBarriersAtOnce){
        if (getBarrierList().size() != maxBarriersAtOnce){
            //this could be done more OO, if we want to add another barrier, we can add it here.
            switch(difficulty){                                                                                         //TODO here we need to get the height of the barrier
                case 0: addBarrierToList(new Barrier_Easy(activity, GameView.getDrawWidth(),  RandomMgr.getRandomFloat(100, GameView.getDrawHeight()), 5, 0));
                    break;
                case 1: addBarrierToList(new Barrier_Medium(activity, (double) GameView.getDrawWidth(), (double) RandomMgr.getRandomFloat(0, GameView.getDrawHeight()), 5, 5));
                    break;
                case 2: addBarrierToList(new Barrier_Hard(activity, (double) GameView.getDrawWidth(), (double) RandomMgr.getRandomFloat(0, GameView.getDrawHeight()), 5, 5));
                    break;
            }
            Log.d(TAG, "Position of barrier = " + getBarrierList().get(0).getPosX());
        }else{
            Log.d(TAG, "Maximum of Barriers already reached!");
        }
    }

    /** create, if not done yet */
    public static void instantiateBarrierList(){
        if (barrierList == null) {
            barrierList = new ArrayList<>();
        }
    }

    /** remove latest barrier */
    public static void removeBarrier(){
        if(getBarrierList().size() > 0)
            getBarrierList().remove(getBarrierList().size()-1);
        else{
            getBarrierList().remove(1);
        }
    }

    /** clears the list */
    public static void removeAllBarriers(){
        getBarrierList().clear();
    }

    /** add an Barrier*/
    private static void addBarrierToList(Barrier b){
        getBarrierList().add(b);
        Log.d(TAG, "Barrier " + barrierList.get(barrierList.size()-1) + " added!");
        Log.d(TAG, "Size of BarrierList = " + barrierList.get(0));
    }

    /** returns a barrier*/
    public static ArrayList<Barrier> getBarrierList(){
        instantiateBarrierList();
        return barrierList;
    }

}
