package expression;

public abstract class AbstractUnaryExpression extends AbstractExpressionWithOperator {
    private final PrioritizedExpression first;

    protected AbstractUnaryExpression(final PrioritizedExpression first) {
        this.first = first;
    }

    protected abstract int integerOperation(int x);

    protected abstract double doubleOperation(double x);

    @Override
    public int evaluate(final int x) {
        return integerOperation(first.evaluate(x));
    }

    @Override
    public double evaluate(final double x) {
        return doubleOperation(first.evaluate(x));
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return integerOperation(first.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getOperator().toString(), first.toString());
    }

    @Override
    public String toMiniString() {
        if (first.getOperator().equals(Operator.NONE)) {
            return String.format("%s%s", getOperator().toString(), first.toMiniString());
        } else {
            return String.format("%s(%s)", getOperator().toString(), first.toMiniString());
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        AbstractUnaryExpression that = (AbstractUnaryExpression) obj;
        return first.equals(that.first);
    }

    @Override
    public int hashCode() {
        return ((first.hashCode()) * 31 + getClass().hashCode()) * 31;
    }
}
