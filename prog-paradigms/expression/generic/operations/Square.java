package expression.generic.operations;

import expression.generic.evaluators.Evaluator;

public class Square<T> extends AbstractUnaryExpression<T> {


    public Square(GeneralExpression<T> first, Evaluator<T> evaluator) {
        super(first, evaluator);
    }

    @Override
    protected T operation(T x) {
        return evaluator.square(x);
    }
}
