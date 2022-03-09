package expression;

public abstract class AbstractUnaryBitExpression extends AbstractUnaryExpression {

    protected AbstractUnaryBitExpression(final PrioritizedExpression first) {
        super(first);
    }

    @Override
    protected double doubleOperation(final double x) {
        throw new UnsupportedOperationException("Cannot call a bitwise operator on double value");
    }
}
