package yourowngame.com.yourowngame.classes.exceptions;

public class JsonToObjectMapper_Exception extends Exception {
    // Parameterless Constructor
    public JsonToObjectMapper_Exception() {}

    // Constructor that accepts a message
    public JsonToObjectMapper_Exception(String message)
    {
        super(message);
    }
}