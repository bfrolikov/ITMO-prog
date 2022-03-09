package expression.exceptions;

public class ParseException extends Exception {
    public ParseException(final String message, final CharSource source) {
        super(source.errorString(message));
    }

    public ParseException(final String message, final Throwable cause, final CharSource source) {
        super(source.errorString(message), cause);
    }

    public ParseException(final Throwable cause) {
        super(cause);
    }
}
