package expression.generic.operations;

import expression.generic.evaluators.Evaluator;

public class Multiply<T> extends AbstractBinaryExpression<T> {
    public Multiply(GeneralExpression<T> first, GeneralExpression<T> second, Evaluator<T> evaluator) {
        super(first, second, evaluator);
    }

    @Override
    protected T operation(T x, T y) {
        return evaluator.multiply(x, y);
    }
}
