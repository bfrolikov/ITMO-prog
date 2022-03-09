package expression;

public abstract class AbstractBinaryBitExpression extends AbstractBinaryExpression {

    protected AbstractBinaryBitExpression(final PrioritizedExpression first, final PrioritizedExpression second) {
        super(first, second);
    }

    @Override
    protected double doubleOperation(final double x, final double y) {
        throw new UnsupportedOperationException("Cannot call a bitwise operator on double value");
    }

}
