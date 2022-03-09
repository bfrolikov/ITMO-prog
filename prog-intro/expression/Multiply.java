package expression;

public class Multiply extends AbstractBinaryExpression {
    public Multiply(final PrioritizedExpression first, final PrioritizedExpression second) {
        super(first, second);
    }

    @Override
    public Operator getOperator() {
        return Operator.MULTIPLY;
    }

    @Override
    protected int integerOperation(final int x, final int y) {
        return x * y;
    }

    @Override
    protected double doubleOperation(final double x, final double y) {
        return x * y;
    }

}
