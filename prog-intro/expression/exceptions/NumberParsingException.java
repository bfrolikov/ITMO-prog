package expression.exceptions;

public class NumberParsingException extends ParseException {
    public NumberParsingException(final String message, final Throwable cause, final CharSource source) {
        super(message, cause, source);
    }
}
