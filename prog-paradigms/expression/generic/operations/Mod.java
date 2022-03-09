package expression.generic.operations;

import expression.generic.evaluators.Evaluator;

public class Mod<T> extends AbstractBinaryExpression<T> {
    public Mod(GeneralExpression<T> first, GeneralExpression<T> second, Evaluator<T> evaluator) {
        super(first, second, evaluator);
    }

    @Override
    protected T operation(T x, T y) {
        return evaluator.mod(x, y);
    }
}
