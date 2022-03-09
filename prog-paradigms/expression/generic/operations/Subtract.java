package expression.generic.operations;

import expression.generic.evaluators.Evaluator;

public class Subtract<T> extends AbstractBinaryExpression<T> {
    public Subtract(GeneralExpression<T> first, GeneralExpression<T> second, Evaluator<T> evaluator) {
        super(first, second, evaluator);
    }

    @Override
    protected T operation(T x, T y) {
        return evaluator.subtract(x, y);
    }
}
