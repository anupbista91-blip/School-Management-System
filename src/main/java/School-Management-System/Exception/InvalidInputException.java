/**
 * File: InvalidInputException.java
 * Purpose: Thrown when input validation fails.
 */
package School_Management_System.Exception;

/**
 * Exception for invalid input scenarios.
 */
public class InvalidInputException extends Exception {
    /**
     * Constructs the exception with message.
     * @param message detail message
     */
    public InvalidInputException(String message) {
        super(message);
    }
}