package expression;

public class Min extends AbstractBinaryExpression {
    public Min(final PrioritizedExpression first, final PrioritizedExpression second) {
        super(first, second);
    }

    @Override
    public Operator getOperator() {
        return Operator.MIN;
    }

    @Override
    protected int integerOperation(final int x, final int y) {
        return x <= y ? x : y;
    }

    @Override
    protected double doubleOperation(final double x, final double y) {
        return x <= y ? x : y;
    }
}
