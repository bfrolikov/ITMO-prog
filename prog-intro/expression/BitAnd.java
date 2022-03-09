package expression;

public class BitAnd extends AbstractBinaryBitExpression {
    public BitAnd(final PrioritizedExpression first, final PrioritizedExpression second) {
        super(first, second);
    }

    @Override
    public Operator getOperator() {
        return Operator.AND;
    }

    @Override
    protected int integerOperation(final int x, final int y) {
        return x & y;
    }

}
