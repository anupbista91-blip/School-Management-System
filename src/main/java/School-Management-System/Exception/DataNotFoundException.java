/**
 * File: DataNotFoundException.java
 * Purpose: Thrown when data is not found in datastore/service.
 */
package School_Management_System.Exception;

/**
 * Exception indicating requested data was not found.
 */
public class DataNotFoundException extends Exception {
    /**
     * Constructs the exception with message.
     * @param message detail message
     */
    public DataNotFoundException(String message) {
        super(message);
    }
}
