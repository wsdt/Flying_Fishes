package yourowngame.com.yourowngame.gameEngine.interfaces;

/** Observer-Pattern: Notifies GameActivity when Highscore has changed (if so then update UI = better performance :)) */
public interface IHighscore_Observer {
    /** Gets called when Highscore has been changed. */
    void onHighscoreChanged();

    /**Maybe also in future:
     * onHighscoreIncremented();
     * onHighscoreDecremented();
     * onHighscoreNotChanged();
     * */
}
