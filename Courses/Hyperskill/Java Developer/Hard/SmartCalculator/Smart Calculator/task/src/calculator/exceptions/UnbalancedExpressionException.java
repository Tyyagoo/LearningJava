package calculator.exceptions;

public class UnbalancedExpressionException extends RuntimeException {
    public UnbalancedExpressionException() {
        super("Invalid expression", null);
    }
}
