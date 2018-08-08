package yourowngame.com.yourowngame.classes.actors.interfaces;


/** Use this interface for implementing */
public interface IHighscore_RewardableObj {
    /** This method get's called by HighScore.class for determining how many points the user gets!
     *
     * Recommended use:
     * getReward() {return HIGHSCORE_CONSTANT;} --> see Enemy or Fruit */
    int getReward();
}
