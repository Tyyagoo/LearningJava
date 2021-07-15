package life.exceptions;

public class UniverseOutOfIndex extends RuntimeException {
    public UniverseOutOfIndex(String ... message) {
        super((message.length == 0) ? "" : message[0], null);
    }
}
