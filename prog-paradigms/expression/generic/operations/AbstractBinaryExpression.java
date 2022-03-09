package expression.generic.operations;

import expression.generic.evaluators.Evaluator;

public abstract class AbstractBinaryExpression<T> implements GeneralExpression<T> {
    private final GeneralExpression<T> first;
    private final GeneralExpression<T> second;
    protected final Evaluator<T> evaluator;

    protected AbstractBinaryExpression(final GeneralExpression<T> first, final GeneralExpression<T> second, final Evaluator<T> evaluator) {
        this.first = first;
        this.second = second;
        this.evaluator = evaluator;
    }

    protected abstract T operation(T x, T y);

    @Override
    public T evaluate(T x) {
        return operation(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return operation(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

}
