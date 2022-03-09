package expression.parser;

import expression.*;

import java.util.Map;
import java.util.Set;

public class ExpressionParser implements Parser {


    public TripleExpression parse(final CharSource source) {
        return new ExpressionParserUtility(source).parse();
    }

    @Override
    public TripleExpression parse(final String expression) {
        return parse(new StringSource(expression));
    }

    private static class ExpressionParserUtility extends BaseParser {
        private static final Map<String, Variable> VALID_VARS = Map.of(
                "x", new Variable("x"),
                "y", new Variable("y"),
                "z", new Variable("z")
        );

        public ExpressionParserUtility(final CharSource source) {
            super(source);
            nextChar();
        }

        public PrioritizedExpression parse() {
            final PrioritizedExpression res = bitOr();
            if (!eof()) {
                throw error("End of expression expected");
            }
            return res;
        }


        private Const getConst(final String sign) {
            return new Const(Integer.parseInt(sign + parseNumber()));
        }

        private PrioritizedExpression parsePrimary() {
            skipWhiteSpace();
            PrioritizedExpression result;
            if (Character.isDigit(ch)) {
                result = getConst("");
            } else if (test('-')) {
                if (Character.isDigit(ch)) {
                    result = getConst("-");
                } else {
                    result = new UnaryMinus(parsePrimary());
                }
            } else if (test('~')) {
                result = new BitNot(parsePrimary());
            } else if (test('c')) {
                expect("ount");
                result = new BitCount(parsePrimary());
            } else if (test('(')) {
                PrioritizedExpression first = bitOr();
                expect(')');
                result = first;
            } else if (Character.isLetter(ch)) {
                final String varName = parseVarName();
                result = VALID_VARS.get(varName);
                if (result == null) {
                    throw error(String.format("Illegal variable name: %s", varName));
                }
            } else {
                throw error("Expected primary expression");
            }
            skipWhiteSpace();
            return result;
        }

        private PrioritizedExpression divMult() {
            PrioritizedExpression first = parsePrimary();
            while (true) {
                if (test('*')) {
                    first = new Multiply(first, parsePrimary());
                } else if (test('/')) {
                    first = new Divide(first, parsePrimary());
                } else {
                    return first;
                }
            }
        }

        private PrioritizedExpression plusMinus() {
            PrioritizedExpression first = divMult();
            while (true) {
                if (test('+')) {
                    first = new Add(first, divMult());
                } else if (test('-')) {
                    first = new Subtract(first, divMult());
                } else {
                    return first;
                }
            }
        }

        private PrioritizedExpression bitAnd() {
            PrioritizedExpression first = plusMinus();
            while (true) {
                if (test('&')) {
                    first = new BitAnd(first, plusMinus());
                } else {
                    return first;
                }
            }
        }

        private PrioritizedExpression bitXor() {
            PrioritizedExpression first = bitAnd();
            while (true) {
                if (test('^')) {
                    first = new BitXor(first, bitAnd());
                } else {
                    return first;
                }
            }
        }

        private PrioritizedExpression bitOr() {
            PrioritizedExpression first = bitXor();
            while (true) {
                if (test('|')) {
                    first = new BitOr(first, bitXor());
                } else {
                    return first;
                }
            }
        }


        private String parseVarName() {
            final StringBuilder nameBuilder = new StringBuilder();
            while (Character.isLetter(ch)) {
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

        private void skipWhiteSpace() {
            while (Character.isWhitespace(ch)) {
                nextChar();
            }
        }
    }
}


