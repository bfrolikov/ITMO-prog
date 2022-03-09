package expression.exceptions;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */


public abstract class BaseParser {
    protected final CharSource source;
    protected char ch = 0xffff;
    public static final char END = '\0';

    protected BaseParser(final CharSource source) {
        this.source = source;
    }

    protected void nextChar() {
        ch = source.hasNext() ? source.next() : END;
    }

    protected boolean test(char expected) {
        if (ch == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected void expect(final char c) throws ParseException {
        if (ch != c) {
            throw new UnexpectedSymbolException(String.format("Expected '%c, found '%c'", c, ch), source);
        }
        nextChar();
    }

    protected void expect(final String value) throws ParseException {
        for (char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean eof() {
        return test(END);
    }

}

