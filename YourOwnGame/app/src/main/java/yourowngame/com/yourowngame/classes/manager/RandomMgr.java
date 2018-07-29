package yourowngame.com.yourowngame.classes.manager;

import java.util.Random;

/*
* Renamed to RandomHandler, for improving Cohesion of Class
* Handles Random-Values in any case!
*
 */
public class RandomMgr {
    private final String TAG = "RandomHandler";
    private static Random random;

    private RandomMgr() {} //no instance allowed

    //RandomHandler is a static class and will only hold static references, so initiating a Random is not necessary
    private static void initiateRandom() {
        if (random == null) { //to avoid unnecessary object allocations (when used in loops e.g.)
            random = new Random();
        }
    }

    /** returns a single Random Int */
    public static int getRandomInt(int minInt, int maxInt) {
        initiateRandom();
        return random.nextInt(maxInt-minInt+1)+minInt;
    }

    /** returns a single Random Float*/
    public static float getRandomFloat(float minFloat, float maxFloat) {
        initiateRandom();
        return random.nextFloat()*(maxFloat-minFloat)+minFloat;
    }


}
