package yourowngame.com.yourowngame.classes.observer.interfaces;

/** Observer-Pattern: Notifies GameActivity when HighScore has changed (if so then update UI = better performance :)) */
public interface IHighscore_Observer {
    /** Gets called when HighScore has been changed. */
    void onHighscoreChanged();

    /* Maybe also in future:
     * onHighscoreIncremented();
     * onHighscoreDecremented();
     * onHighscoreNotChanged();
     * */
}
