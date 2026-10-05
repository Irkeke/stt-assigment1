/**
 * Thrown when attempting to add an employee whose ID is already
 * registered in the system.
 */
public class DuplicateEmployeeException extends Exception {
    public DuplicateEmployeeException(String message) {
        super(message);
    }
}
