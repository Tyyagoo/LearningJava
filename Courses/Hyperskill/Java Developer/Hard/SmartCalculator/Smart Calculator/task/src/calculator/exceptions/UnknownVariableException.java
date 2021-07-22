package calculator.exceptions;

public class UnknownVariableException extends RuntimeException {
    public UnknownVariableException() {
        super("Unknown variable", null);
    }
}
