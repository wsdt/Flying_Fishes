package yourowngame.com.yourowngame.classes.counters;


import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Avoci;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Meloon;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Pinapo;
import yourowngame.com.yourowngame.classes.annotations.Enhance;

/**
 * is responsible for Counting the Fruits
 */


public class FruitCounter {
    private static final String TAG = "FruitCounter";
    private static FruitCounter INSTANCE;

    /**
     * only per level
     */
    private int levelAmountMeloons = 0;
    private int levelAmountPinapos = 0;
    private int levelAmountAvocis = 0;

    /**
     * total amount of fruits, need to be saved at the end & restored at starting app
     */
    private static int totalAmountOfMeloons = 0;
    private static int totalAmountOfPinapos = 0;
    private static int totalAmountOfAvocis = 0;


    public static FruitCounter getInstance() {
        return INSTANCE == null ? INSTANCE = new FruitCounter() : INSTANCE;
    }

    private FruitCounter() {
    }  //private access


    @Enhance(byDeveloper = "Solution",
            message = "get rid of that instanceOf, but how can we optimise checking which fruit has been collected without getting unnecessary lines of code?")
    public void fruitCollected(@NonNull Fruit fruit) {
        if (fruit instanceof Meloon) {
            levelAmountMeloons++;
            totalAmountOfMeloons++;
        } else if (fruit instanceof Pinapo) {
            levelAmountPinapos++;
            totalAmountOfPinapos++;
        } else if (fruit instanceof Avoci) {
            levelAmountAvocis++;
            totalAmountOfAvocis++;
        } else {
            Log.e(TAG, "fruitCollected: Could not determine which fruit has been collected-> "+fruit);
        }

        //Execute fruit power
        fruit.fruitCollected();
    }

    /**
     * After level is over, collected fruits will be transfered
     */
    public void transportFruits() {
        totalAmountOfMeloons += levelAmountMeloons;
        totalAmountOfAvocis += levelAmountAvocis;
        totalAmountOfPinapos += levelAmountPinapos;
    }

    /**
     * reset fruits after end
     */
    public void cleanUpFruitCounter() {
        levelAmountAvocis = levelAmountMeloons = levelAmountPinapos = 0;
    }

    /**
     * SETTERS AND GETTERS
     */
    public int getLevelAmountMeloons() {
        return levelAmountMeloons;
    }

    public int getLevelAmountPinapos() {
        return levelAmountPinapos;
    }

    public int getLevelAmountAvocis() {
        return levelAmountAvocis;
    }

    public static int getTotalAmountOfMeloons() {
        return totalAmountOfMeloons;
    }

    public static int getTotalAmountOfPinapos() {
        return totalAmountOfPinapos;
    }

    public static int getTotalAmountOfAvocis() {
        return totalAmountOfAvocis;
    }


}
