package yourowngame.com.yourowngame.classes.configuration;

/**
 * Constants interfaces are referencing NOT translateable (global) values [e.g. numbers, auth-tokens, arbitrary strings etc.].
 * If a string can be translated (sensefully) then place it into Strings.xml Resource file!
 *
 * Please group constants within superior Interface 'Constants' by additional interfaces [e.g. Constants > GameObject > {values}]
 */

public interface Constants {
    interface Testing {
        boolean useTestMode = true; //Use: Test ads or real ads etc..
    }
}
