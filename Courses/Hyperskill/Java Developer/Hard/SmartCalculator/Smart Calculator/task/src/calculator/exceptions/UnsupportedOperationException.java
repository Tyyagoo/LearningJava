package calculator.exceptions;

public class UnsupportedOperationException extends RuntimeException {
    public UnsupportedOperationException(String ... args) {
        super(args.length == 0 ? "Unsupported Operation." : args[0], null);
    }
}
