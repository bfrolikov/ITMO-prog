package expression;

public class Add extends AbstractBinaryExpression {
    public Add(final PrioritizedExpression first, final PrioritizedExpression second) {
        super(first, second);
    }

    @Override
    public Operator getOperator() {
        return Operator.ADD;
    }

    @Override
    protected int integerOperation(final int x, final int y) {
        return x + y;
    }

    @Override
    protected double doubleOperation(final double x, final double y) {
        return x + y;
    }

}
