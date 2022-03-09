package expression.exceptions;

public class IllegalSequenceException extends ParseException {
    public IllegalSequenceException(final String message, final CharSource source) {
        super(message, source);
    }
}
