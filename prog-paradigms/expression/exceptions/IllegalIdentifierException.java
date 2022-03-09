package expression.exceptions;

import expression.generic.parser.CharSource;

public class IllegalIdentifierException extends ParseException {
    public IllegalIdentifierException(final String message, final CharSource source) {
        super(message, source);
    }
}