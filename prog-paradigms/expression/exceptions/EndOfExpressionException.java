package expression.exceptions;

import expression.generic.parser.CharSource;

public class EndOfExpressionException extends ParseException {
    public EndOfExpressionException(final String message, final CharSource source) {
        super(message, source);
    }
}
