package expression.generic.operations;

import expression.generic.evaluators.Evaluator;

public class UnaryMinus<T> extends AbstractUnaryExpression<T> {


    public UnaryMinus(GeneralExpression<T> first, Evaluator<T> evaluator) {
        super(first, evaluator);
    }

    @Override
    protected T operation(T x) {
        return evaluator.negate(x);
    }
}
