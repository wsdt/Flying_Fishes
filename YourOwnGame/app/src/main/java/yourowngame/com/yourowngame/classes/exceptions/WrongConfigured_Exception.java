package yourowngame.com.yourowngame.classes.exceptions;

/** Thrown when an object hasn't been configured by a developer the right way.
 * If you receive this exception don't catch it yourself, just configure your obj
 * the right way please!
 *
 * RunTimeException so we don't need to catch it. We force the application to stop/die. */
public class WrongConfigured_Exception extends RuntimeException {
    // Parameterless Constructor
    public WrongConfigured_Exception() {}

    // Constructor that accepts a message
    public WrongConfigured_Exception(String message)
    {
        super(message);
    }
}