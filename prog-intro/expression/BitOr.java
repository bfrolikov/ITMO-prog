package expression;

public class BitOr extends AbstractBinaryBitExpression {
    public BitOr(final PrioritizedExpression first, final PrioritizedExpression second) {
        super(first, second);
    }

    @Override
    public Operator getOperator() {
        return Operator.OR;
    }

    @Override
    protected int integerOperation(final int x, final int y) {
        return x | y;
    }

}
