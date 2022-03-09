package expression.exceptions;

import expression.Operator;
import expression.PrioritizedExpression;

public class CheckedSubtract extends AbstractCheckedBinaryExpression {

    public CheckedSubtract(final PrioritizedExpression first, final PrioritizedExpression second) {
        super(first, second);
    }

    @Override
    public Operator getOperator() {
        return Operator.SUBTRACT;
    }

    @Override
    protected int operationImpl(final int x, final int y) {
        return x - y;
    }

    @Override
    protected boolean checkOverflow(final int x, final int y) {
        if (x < 0 && y > 0) {
            return x < Integer.MIN_VALUE + y;
        } else {
            return x >= 0 && y < 0 && x > Integer.MAX_VALUE + y;
        }
    }
}
