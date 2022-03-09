package expression.exceptions;

public class PrimaryExpressionException extends ParseException {
    public PrimaryExpressionException(final String message, final CharSource source) {
        super(message, source);
    }
}
