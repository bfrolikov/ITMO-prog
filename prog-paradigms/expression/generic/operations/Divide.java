package expression.generic.operations;

import expression.generic.evaluators.Evaluator;

public class Divide<T> extends AbstractBinaryExpression<T> {
    public Divide(GeneralExpression<T> first, GeneralExpression<T> second, Evaluator<T> evaluator) {
        super(first, second, evaluator);
    }

    @Override
    protected T operation(T x, T y) {
        return evaluator.divide(x, y);
    }
}
