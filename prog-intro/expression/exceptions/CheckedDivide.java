package expression.exceptions;

import expression.Operator;
import expression.PrioritizedExpression;

public class CheckedDivide extends AbstractCheckedBinaryExpression {
    public CheckedDivide(final PrioritizedExpression first, final PrioritizedExpression second) {
        super(first, second);
    }

    @Override
    public Operator getOperator() {
        return Operator.DIVIDE;
    }

    @Override
    protected int operationImpl(final int x, final int y) {
        if (y == 0) {
            throw new ZeroDivisionException("Division by 0 happened during evaluation");
        }
        return x / y;
    }

    @Override
    protected boolean checkOverflow(final int x, final int y) {
        return x == Integer.MIN_VALUE && y == -1;
    }
}
