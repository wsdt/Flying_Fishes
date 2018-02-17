package yourowngame.com.yourowngame.classes.exceptions;

public class NoDrawableInArrayFound_Exception extends Exception {
    // Parameterless Constructor
    public NoDrawableInArrayFound_Exception() {}

    // Constructor that accepts a message
    public NoDrawableInArrayFound_Exception(String message)
    {
        super(message);
    }
}