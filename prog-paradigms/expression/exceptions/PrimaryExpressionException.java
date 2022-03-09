package expression.exceptions;

import expression.generic.parser.CharSource;

public class PrimaryExpressionException extends ParseException {
    public PrimaryExpressionException(final String message, final CharSource source) {
        super(message, source);
    }
}
