package yourowngame.com.yourowngame.classes.observer.interfaces;

/** Observer-Pattern: Notifies GameActivity when FruitCount has changed (if so then update UI = better performance :)) */
public interface IFruitCounter_Observer {
    /** Gets called when FruitCount has been changed. */
    void onFruitCountChanged();

    /* Maybe also in future for each fruit? How to program generically? */
}
