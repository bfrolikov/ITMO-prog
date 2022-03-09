package expression.exceptions;

import expression.AbstractUnaryExpression;
import expression.Operator;
import expression.PrioritizedExpression;

public abstract class AbstractCheckedUnaryExpression extends AbstractUnaryExpression {
    protected AbstractCheckedUnaryExpression(final PrioritizedExpression first) {
        super(first);
    }

    @Override
    public int integerOperation(final int x) {
        if (checkOverflow(x)) {
            throw new OverflowException("Integer overflow happened during evaluation");
        }
        return operationImpl(x);
    }

    protected abstract int operationImpl(final int x);

    protected abstract boolean checkOverflow(final int x);

    @Override
    protected double doubleOperation(final double x) {
        throw new DoubleException("Cannot call a double evaluate on a checked expression");
    }
}
