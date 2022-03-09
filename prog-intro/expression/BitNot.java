package expression;

public class BitNot extends AbstractUnaryBitExpression {
    public BitNot(PrioritizedExpression first) {
        super(first);
    }

    @Override
    public Operator getOperator() {
        return Operator.NOT;
    }

    @Override
    protected int integerOperation(final int x) {
        return ~x;
    }

}
