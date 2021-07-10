package processor.Exceptions;

public class DeterminantEqualsZeroException extends RuntimeException {
    public DeterminantEqualsZeroException() {
        super("This matrix doesn't have an inverse.", null);
    }
}
