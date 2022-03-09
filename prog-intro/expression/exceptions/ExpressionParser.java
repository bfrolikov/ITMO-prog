package expression.exceptions;

import expression.*;


import java.util.Map;
import java.util.Set;

public class ExpressionParser implements Parser {


    public TripleExpression parse(final CharSource source) throws ParseException {
        return new ExpressionParserUtility(source).parse();
    }

    @Override
    public TripleExpression parse(final String expression) throws ParseException {
        return parse(new StringSource(expression));
    }

    private static class ExpressionParserUtility extends BaseParser {
        private static final Map<String, Variable> VALID_VARS = Map.of(
                "x", new Variable("x"),
                "y", new Variable("y"),
                "z", new Variable("z")
        );

        private static final Set<String> STRING_BINARY_OPERATORS = Set.of("min", "max");

        public ExpressionParserUtility(final CharSource source) {
            super(source);
            nextChar();
        }

        public PrioritizedExpression parse() throws ParseException {
            final PrioritizedExpression res = minMax();
            if (!eof()) {
                throw new EndOfExpressionException(String.format("Expected end of expression, got %c", ch), source);
            }
            return res;
        }


        private PrioritizedExpression parsePrimary() throws ParseException {
            skipWhiteSpace();
            PrioritizedExpression result;
            if (Character.isDigit(ch)) {
                result = getConst("");
            } else if (test('-')) {
                if (Character.isDigit(ch)) {
                    result = getConst("-");
                } else {
                    result = new CheckedNegate(parsePrimary());
                }
            } else if (test('~')) {
                result = new BitNot(parsePrimary());
            } else if (test('c')) {
                expect("ount");
                result = new BitCount(parsePrimary());
            } else if (test('a')) {
                expect("bs");
                checkMalformedOperator("abs");
                result = new CheckedAbs(parsePrimary());
            } else if (test('s')) {
                expect("qrt");
                checkMalformedOperator("sqrt");
                result = new CheckedSqrt(parsePrimary());
            } else if (test('(')) {
                result = minMax();
                expect(')');
            } else if (Character.isLetter(ch)) {
                final String sv = parseString();
                if (STRING_BINARY_OPERATORS.contains(sv)) {
                    throw new PrimaryExpressionException("Expected primary expression", source);
                }
                result = VALID_VARS.get(sv);
                if (result == null) {
                    throw new IllegalSequenceException(String.format("Illegal sequence: %s", sv), source);
                }
            } else {
                throw new PrimaryExpressionException("Expected primary expression", source);
            }
            skipWhiteSpace();
            return result;
        }

        private PrioritizedExpression divMult() throws ParseException {
            PrioritizedExpression first = parsePrimary();
            while (true) {
                if (test('*')) {
                    first = new CheckedMultiply(first, parsePrimary());
                } else if (test('/')) {
                    first = new CheckedDivide(first, parsePrimary());
                } else {
                    return first;
                }
            }
        }

        private PrioritizedExpression plusMinus() throws ParseException {
            PrioritizedExpression first = divMult();
            while (true) {
                if (test('+')) {
                    first = new CheckedAdd(first, divMult());
                } else if (test('-')) {
                    first = new CheckedSubtract(first, divMult());
                } else {
                    return first;
                }
            }
        }

        private PrioritizedExpression bitAnd() throws ParseException {
            PrioritizedExpression first = plusMinus();
            while (true) {
                if (test('&')) {
                    first = new BitAnd(first, plusMinus());
                } else {
                    return first;
                }
            }
        }

        private PrioritizedExpression bitXor() throws ParseException {
            PrioritizedExpression first = bitAnd();
            while (true) {
                if (test('^')) {
                    first = new BitXor(first, bitAnd());
                } else {
                    return first;
                }
            }
        }

        private PrioritizedExpression bitOr() throws ParseException {
            PrioritizedExpression first = bitXor();
            while (true) {
                if (test('|')) {
                    first = new BitOr(first, bitXor());
                } else {
                    return first;
                }
            }
        }

        private PrioritizedExpression minMax() throws ParseException {
            PrioritizedExpression first = bitOr();
            while (true) {
                if (test('m')) {
                    if (test('i')) {
                        expect('n');
                        checkMalformedOperator("min");
                        first = new Min(first, bitOr());
                    } else {
                        expect("ax");
                        checkMalformedOperator("max");
                        first = new Max(first, bitOr());
                    }
                } else {
                    return first;
                }
            }
        }

        private Const getConst(final String sign) throws ParseException {
            final String numberString = sign + parseNumber();
            try {
                return new Const(Integer.parseInt(numberString));
            } catch (NumberFormatException e) {
                throw new NumberParsingException(String.format("Could not parse number %s", numberString), e, source);
            }
        }

        private String parseString() {
            final StringBuilder nameBuilder = new StringBuilder();
            while (Character.isLetter(ch) || Character.isDigit(ch)) {
                nameBuilder.append(ch);
                nextChar();
            }
            return nameBuilder.toString();
        }


        private String parseNumber() {
            final StringBuilder numberBuilder = new StringBuilder();
            while (Character.isDigit(ch)) {
                numberBuilder.append(ch);
                nextChar();
            }
            return numberBuilder.toString();
        }

        private void checkMalformedOperator(String op) throws ParseException {
            if (Character.isDigit(ch) || Character.isLetter(ch)) {
                throw new IllegalSequenceException(String.format("Illegal sequence: %s%s", op, parseString()), source);
            }
        }

        private void skipWhiteSpace() {
            while (Character.isWhitespace(ch)) {
                nextChar();
            }
        }

    }
}


