package expression.exceptions;

import expression.Operator;
import expression.PrioritizedExpression;

public class CheckedSqrt extends AbstractCheckedUnaryExpression {
    public CheckedSqrt(final PrioritizedExpression first) {
        super(first);
    }

    @Override
    public Operator getOperator() {
        return Operator.SQRT;
    }

    @Override
    protected int operationImpl(final int x) {
        if (x < 0) {
            throw new NegativeSqrtException("Cannot take square root of a negative number");
        }
        return (int) Math.sqrt((double) x);
    }

    @Override
    protected boolean checkOverflow(final int x) {
        return false;
    }
}
