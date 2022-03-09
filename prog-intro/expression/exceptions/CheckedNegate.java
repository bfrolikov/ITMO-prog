package expression.exceptions;

import expression.Operator;
import expression.PrioritizedExpression;

public class CheckedNegate extends AbstractCheckedUnaryExpression {
    public CheckedNegate(final PrioritizedExpression first) {
        super(first);
    }

    @Override
    public Operator getOperator() {
        return Operator.UNARY_MINUS;
    }

    @Override
    protected int operationImpl(final int x) {
        return -x;
    }

    @Override
    protected boolean checkOverflow(final int x) {
        return x == Integer.MIN_VALUE;
    }
}
