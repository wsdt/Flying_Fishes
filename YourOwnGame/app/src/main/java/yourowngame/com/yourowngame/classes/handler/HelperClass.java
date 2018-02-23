package yourowngame.com.yourowngame.classes.handler;

import java.util.Random;

public class HelperClass {
    private static Random random;

    private static void initiateRandom() {
        if (random == null) { //to avoid unnecessary object allocations (when used in loops e.g.)
            random = new Random();
        }
    }

    public static int getRandomInt(int minInt, int maxInt) {
        initiateRandom();
        return random.nextInt(maxInt-minInt+1)+minInt;
    }

    public static float getRandomFloat(float minFloat, float maxFloat) {
        initiateRandom();
        return random.nextFloat()*(maxFloat-minFloat)+minFloat;
    }

}
