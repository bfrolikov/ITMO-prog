package expression.exceptions;

public class EndOfExpressionException extends ParseException {
    public EndOfExpressionException(final String message, final CharSource source) {
        super(message, source);
    }
}
