package advisor.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String ... args) {
        super(args.length == 0 ? "Please, provide access for application." : args[0], null);
    }
}
