package processor.Exceptions;

public class InvalidMatrixSizeException extends RuntimeException {
    public InvalidMatrixSizeException() {
        super("The operation cannot be performed.", null);
    }
}
