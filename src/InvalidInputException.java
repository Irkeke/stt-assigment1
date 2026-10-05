/**
 * Thrown when a caller supplies an out-of-range or otherwise invalid
 * value (negative salary, negative hours, overtime above the allowed
 * cap, blank ID/name, etc.).
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
