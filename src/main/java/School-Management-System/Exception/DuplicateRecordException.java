/**
 * File: DuplicateRecordException.java
 * Purpose: Thrown when attempting to create a duplicate record.
 */
package School_Management_System.Exception;

/**
 * Exception for duplicate data records.
 */
public class DuplicateRecordException extends Exception {
    /**
     * Constructs the exception with message.
     * @param message detail message
     */
    public DuplicateRecordException(String message) {
        super(message);
    }
}