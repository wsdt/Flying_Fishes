package yourowngame.com.yourowngame.classes.manager.interfaces;

/** Used for async operations to provide consistent way of executing sth after specified operation is done
 * --> REALLY USEFUL :) [used this for inAppPurchases]*/
public interface ExecuteIfTrueSuccess_or_ifFalseFailure_afterCompletation {
    void success_is_true();
    void failure_is_false();
}
