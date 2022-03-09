package expression;

public class Subtract extends AbstractBinaryExpression {
    public Subtract(final PrioritizedExpression first, final PrioritizedExpression second) {
        super(first, second);
    }

    @Override
    public Operator getOperator() {
        return Operator.SUBTRACT;
    }

    @Override
    protected int integerOperation(final int x, final int y) {
        return x - y;
    }

    @Override
    protected double doubleOperation(final double x, final double y) {
        return x - y;
    }
}
