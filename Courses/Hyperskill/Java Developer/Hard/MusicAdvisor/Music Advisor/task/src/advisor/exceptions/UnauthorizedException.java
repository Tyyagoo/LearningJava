package advisor.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Please, provide access for application.", null);
    }
}
