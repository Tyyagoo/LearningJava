package calculator.exceptions;

public class UnbalancedExpressionException extends RuntimeException {
    public UnbalancedExpressionException() {
        super("Unbalanced Expression.", null);
    }
}
