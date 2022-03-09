package expression.generic.operations;

import expression.generic.evaluators.Evaluator;

public class Abs<T> extends AbstractUnaryExpression<T> {


    public Abs(GeneralExpression<T> first, Evaluator<T> evaluator) {
        super(first, evaluator);
    }

    @Override
    protected T operation(T x) {
        return evaluator.abs(x);
    }
}