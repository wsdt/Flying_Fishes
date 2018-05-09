package yourowngame.com.yourowngame.classes.counters;


import yourowngame.com.yourowngame.classes.actors.fruits.Fruit;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Avoci;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Meloon;
import yourowngame.com.yourowngame.classes.actors.fruits.specializations.Pinapo;
import yourowngame.com.yourowngame.classes.annotations.Enhance;

/**
 * is responsible for Counting the Fruits
 *
 *
 *
 */


public class FruitCounter {
    private static FruitCounter INSTANCE;

    /** only per level */
    private int levelAmountMeloons = 0;
    private int levelAmountPinapos = 0;
    private int levelAmountAvocis  = 0;

    /** total amount of fruits, need to be saved at the end & restored at starting app */
    private int totalAmountOfMeloons = 0;
    private int totalAmountOfPinapos = 0;
    private int totalAmountOfAvocis  = 0;


    public static FruitCounter getInstance(){
        return INSTANCE == null ? INSTANCE = new FruitCounter() : INSTANCE;
    }

    private FruitCounter(){}  //private access


    @Enhance(byDeveloper = "Solution",
    message = "get rid of that instanceOf, but how can we optimise checking which fruit has been collected without getting unnecessary lines of code?")
    public void fruitCollected(Fruit fruit){
        if(fruit instanceof Meloon)
            levelAmountMeloons++;
        if(fruit instanceof Pinapo)
            levelAmountPinapos++;
        if(fruit instanceof Avoci)
            levelAmountAvocis++;
    }

    /** After level is over, collected fruits will be transfered*/
    public void transportFruits(){
        totalAmountOfMeloons += levelAmountMeloons;
        totalAmountOfAvocis += levelAmountAvocis;
        totalAmountOfPinapos += levelAmountPinapos;
    }

    /** reset fruits after end */
    public void cleanUpFruitCounter(){
        levelAmountAvocis = levelAmountMeloons = levelAmountPinapos = 0;
    }



    /** INSTANCE *******/
    public static FruitCounter getINSTANCE() {
        return INSTANCE;
    }




    /** SETTERS AND GETTERS */
    public int getLevelAmountMeloons() {
        return levelAmountMeloons;
    }
    public int getLevelAmountPinapos() {
        return levelAmountPinapos;
    }
    public int getLevelAmountAvocis() {
        return levelAmountAvocis;
    }
    public int getTotalAmountOfMeloons() {
        return totalAmountOfMeloons;
    }
    public int getTotalAmountOfPinapos() {
        return totalAmountOfPinapos;
    }
    public int getTotalAmountOfAvocis() {
        return totalAmountOfAvocis;
    }


}
