package expression.generic.operations;

import expression.generic.evaluators.Evaluator;

public abstract class AbstractUnaryExpression<T> implements GeneralExpression<T> {
    private final GeneralExpression<T> first;
    protected final Evaluator<T> evaluator;

    protected AbstractUnaryExpression(final GeneralExpression<T> first, final Evaluator<T> evaluator) {
        this.first = first;
        this.evaluator = evaluator;
    }

    protected abstract T operation(T x);

    @Override
    public T evaluate(final T x) {
        return operation(first.evaluate(x));
    }

    @Override
    public T evaluate(final T x, final T y, final T z) {
        return operation(first.evaluate(x, y, z));
    }

}
