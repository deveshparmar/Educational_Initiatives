package SmartHomeSystem.Exceptions;

/**
 * The {@code InvalidTriggerException} class represents custom exception for unauthorized access
 */

public class UnauthorizedAccessException extends Exception{
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
