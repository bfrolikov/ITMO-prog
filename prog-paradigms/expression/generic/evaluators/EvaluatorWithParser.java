package expression.generic.evaluators;

import expression.generic.operations.TripleExpression;
import expression.generic.parser.ExpressionParser;
import expression.exceptions.ParseException;
import expression.generic.parser.Parser;

public abstract class EvaluatorWithParser<T> implements Evaluator<T> {
    protected final Parser<T> parser;

    protected EvaluatorWithParser() {
        this.parser = new ExpressionParser<>(this);
    }

    public TripleExpression<T> parse(final String expr) throws ParseException {
        return parser.parse(expr);
    }
}
