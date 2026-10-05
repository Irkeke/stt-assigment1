/**
 * Thrown when an operation references an employee ID that does not exist
 * in the system (e.g. update, delete, search, or payment processing).
 */
public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
