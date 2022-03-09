package expression.exceptions;

import expression.generic.parser.CharSource;

public class UnexpectedSymbolException extends ParseException {
    public UnexpectedSymbolException(final String message, final CharSource source) {
        super(message, source);
    }
}
