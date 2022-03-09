package expression.exceptions;

import expression.generic.parser.CharSource;

public class NumberParsingException extends ParseException {
    public NumberParsingException(final String message, final Throwable cause, final CharSource source) {
        super(message, cause, source);
    }
}
