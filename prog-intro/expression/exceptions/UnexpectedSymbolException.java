package expression.exceptions;

public class UnexpectedSymbolException extends ParseException {
    public UnexpectedSymbolException(final String message, final CharSource source) {
        super(message, source);
    }
}
