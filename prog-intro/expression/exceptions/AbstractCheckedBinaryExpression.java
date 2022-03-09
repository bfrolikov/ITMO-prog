package expression.exceptions;

import expression.AbstractBinaryExpression;
import expression.Operator;
import expression.PrioritizedExpression;

public abstract class AbstractCheckedBinaryExpression extends AbstractBinaryExpression {

    protected AbstractCheckedBinaryExpression(final PrioritizedExpression first, final PrioritizedExpression second) {
        super(first, second);
    }

    @Override
    public int integerOperation(final int x, final int y) {
        if (checkOverflow(x, y)) {
            throw new OverflowException("Integer overflow happened during evaluation");
        }
        return operationImpl(x, y);
    }

    protected abstract int operationImpl(final int x, final int y);

    protected abstract boolean checkOverflow(final int x, final int y);

    @Override
    protected double doubleOperation(final double x, final double y) {
        throw new DoubleException("Cannot call a double evaluate on a checked expression");
    }
}
