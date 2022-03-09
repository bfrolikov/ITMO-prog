package expression;

public class BitCount extends AbstractUnaryBitExpression {
    public BitCount(final PrioritizedExpression first) {
        super(first);
    }

    @Override
    public Operator getOperator() {
        return Operator.COUNT;
    }

    @Override
    protected int integerOperation(final int x) {
        return Integer.bitCount(x);
    }

}
