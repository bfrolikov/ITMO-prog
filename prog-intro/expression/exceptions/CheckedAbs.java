package expression.exceptions;

import expression.Operator;
import expression.PrioritizedExpression;

public class CheckedAbs extends AbstractCheckedUnaryExpression {
    public CheckedAbs(final PrioritizedExpression first) {
        super(first);
    }

    @Override
    public Operator getOperator() {
        return Operator.ABS;
    }

    @Override
    protected int operationImpl(final int x) {
        return x >= 0 ? x : -x;
    }

    @Override
    protected boolean checkOverflow(final int x) {
        return x == Integer.MIN_VALUE;
    }
}
