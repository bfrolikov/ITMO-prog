package expression.exceptions;

import expression.Operator;
import expression.PrioritizedExpression;

public class CheckedAdd extends AbstractCheckedBinaryExpression {
    public CheckedAdd(final PrioritizedExpression first, final PrioritizedExpression second) {
        super(first, second);
    }

    @Override
    public Operator getOperator() {
        return Operator.ADD;
    }

    @Override
    protected int operationImpl(final int x, final int y) {
        return x + y;
    }

    @Override
    protected boolean checkOverflow(final int x, final int y) {
        if (x > 0 && y > 0) {
            return x > Integer.MAX_VALUE - y;
        } else if (x < 0 && y < 0) {
            return x < Integer.MIN_VALUE - y;
        }
        return false;
    }
}
