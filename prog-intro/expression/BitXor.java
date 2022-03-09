package expression;

public class BitXor extends AbstractBinaryBitExpression {
    public BitXor(final PrioritizedExpression first, final PrioritizedExpression second) {
        super(first, second);
    }

    @Override
    public Operator getOperator() {
        return Operator.XOR;
    }
    @Override
    protected int integerOperation(final int x, final int y) {
        return x ^ y;
    }

}
