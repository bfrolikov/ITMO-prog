package expression;

public class UnaryMinus extends AbstractUnaryExpression {
    public UnaryMinus(final PrioritizedExpression first) {
        super(first);
    }

    @Override
    public Operator getOperator() {
        return Operator.UNARY_MINUS;
    }
    @Override
    protected int integerOperation(final int x) {
        return -x;
    }

    @Override
    protected double doubleOperation(final double x) {
        return -x;
    }

}
