package expression;

public class Divide extends AbstractBinaryExpression {
    public Divide(final PrioritizedExpression first, final PrioritizedExpression second) {
        super(first, second);
    }

    @Override
    public Operator getOperator() {
        return Operator.DIVIDE;
    }

    @Override
    protected int integerOperation(final int x, final int y) {
        return x / y;
    }

    @Override
    protected double doubleOperation(final double x, final double y) {
        return x / y;
    }

}
