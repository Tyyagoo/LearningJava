package calculator.exceptions;

public class InvalidAssignmentException extends RuntimeException {
    public InvalidAssignmentException() {
        super("Invalid assignment", null);
    }
}
