package expression.generic.parser;

import expression.exceptions.*;
import expression.generic.evaluators.Evaluator;
import expression.generic.operations.*;

import java.util.Map;
import java.util.Set;

public class ExpressionParser<T> implements Parser<T> {

    private final Evaluator<T> evaluator;

    public ExpressionParser(Evaluator<T> evaluator) {
        this.evaluator = evaluator;
    }

    public TripleExpression<T> parse(final CharSource source) throws ParseException {
        return new ExpressionParserUtility(source).parse();
    }

    @Override
    public TripleExpression<T> parse(final String expression) throws ParseException {
        return parse(new StringSource(expression));
    }

    private class ExpressionParserUtility extends BaseParser {
        private final Map<String, Variable<T>> VALID_VARS = Map.of(
                "x", new Variable<>("x"),
                "y", new Variable<>("y"),
                "z", new Variable<>("z")
        );

        private final Set<String> STRING_BINARY_OPERATORS = Set.of("min", "max", "mod");

        public ExpressionParserUtility(final CharSource source) {
            super(source);
            nextChar();
        }

        public GeneralExpression<T> parse() throws ParseException {
            final GeneralExpression<T> res = plusMinus();
            if (!eof()) {
                throw new EndOfExpressionException(String.format("Expected end of expression, got %c", ch), source);
            }
            return res;
        }


        private GeneralExpression<T> parsePrimary() throws ParseException {
            skipWhiteSpace();
            GeneralExpression<T> result;
            if (Character.isDigit(ch)) {
                result = getConst("");
            } else if (test('-')) {
                if (Character.isDigit(ch)) {
                    result = getConst("-");
                } else {
                    result = new UnaryMinus<>(parsePrimary(), evaluator);
                }
            } else if (test('a')) {
                expect("bs");
                checkMalformedOperator("abs");
                result = new Abs<>(parsePrimary(), evaluator);
            } else if (test('s')) {
                expect("quare");
                checkMalformedOperator("square");
                result = new Square<>(parsePrimary(), evaluator);
            } else if (test('(')) {
                result = plusMinus();
                expect(')');
            } else if (Character.isLetter(ch)) {
                final String sv = parseString();
                if (STRING_BINARY_OPERATORS.contains(sv)) {
                    throw new PrimaryExpressionException("Expected primary expression", source);
                }
                result = VALID_VARS.get(sv);
                if (result == null) {
                    throw new IllegalIdentifierException(String.format("Illegal identifier: %s", sv), source);
                }
            } else {
                throw new PrimaryExpressionException("Expected primary expression", source);
            }
            skipWhiteSpace();
            return result;
        }

        private GeneralExpression<T> mod() throws ParseException {
            GeneralExpression<T> first = parsePrimary();
            while (true) {
                if (test('m')) {
                    expect("od");
                    checkMalformedOperator("mod");
                    first = new Mod<>(first, parsePrimary(), evaluator);
                } else {
                    return first;
                }
            }
        }

        private GeneralExpression<T> divMult() throws ParseException {
            GeneralExpression<T> first = mod();
            while (true) {
                if (test('*')) {
                    first = new Multiply<T>(first, parsePrimary(), evaluator);
                } else if (test('/')) {
                    first = new Divide<T>(first, parsePrimary(), evaluator);
                } else {
                    return first;
                }
            }
        }

        private GeneralExpression<T> plusMinus() throws ParseException {
            GeneralExpression<T> first = divMult();
            while (true) {
                if (test('+')) {
                    first = new Add<>(first, divMult(), evaluator);
                } else if (test('-')) {
                    first = new Subtract<>(first, divMult(), evaluator);
                } else {
                    return first;
                }
            }
        }

        private Const<T> getConst(final String sign) throws ParseException {
            final String numberString = sign + parseNumber();
            try {
                return new Const<>(evaluator.fromString(numberString));
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
                throw new IllegalIdentifierException(String.format("Illegal identifier: %s%s", op, parseString()), source);
            }
        }

        private void skipWhiteSpace() {
            while (Character.isWhitespace(ch)) {
                nextChar();
            }
        }

    }
}



