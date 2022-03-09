package expression;

public abstract class AbstractBinaryExpression extends AbstractExpressionWithOperator {
    private final PrioritizedExpression first;
    private final PrioritizedExpression second;

    protected AbstractBinaryExpression(final PrioritizedExpression first, final PrioritizedExpression second) {
        this.first = first;
        this.second = second;
    }

    protected abstract int integerOperation(int x, int y);

    protected abstract double doubleOperation(double x, double y);

    @Override
    public int evaluate(final int x) {
        return integerOperation(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public double evaluate(final double x) {
        return doubleOperation(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return integerOperation(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return String.format("(%s%s%s)", first.toString(), spacedOperator(), second.toString());
    }

    @Override
    public String toMiniString() {
        final StringBuilder sb = new StringBuilder();
        handleBrackets(sb, first, true);
        sb.append(spacedOperator());
        handleBrackets(sb, second, false);
        return sb.toString();

    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        AbstractBinaryExpression that = (AbstractBinaryExpression) obj;
        return first.equals(that.first) && second.equals(that.second);
    }

    @Override
    public int hashCode() {
        return ((first.hashCode() * 31 + second.hashCode()) * 31 + getClass().hashCode()) * 31;
    }

    private void handleBrackets(StringBuilder s, PrioritizedExpression exp, boolean first) {
        final int comp = exp.getPriority().compareTo(getPriority());
        if (comp < 0) {
            s.append("(").append(exp.toMiniString()).append(")");
        } else if (comp > 0 || first) {
            s.append(exp.toMiniString());
        } else {
            if (getOperator().equals(exp.getOperator())) {
                if (getOperator().isAssociative()) {
                    s.append(exp.toMiniString());
                } else {
                    s.append("(").append(exp.toMiniString()).append(")");
                }
            } else {
                if (getOperator().alters(exp.getOperator())) {
                    s.append("(").append(exp.toMiniString()).append(")");
                } else {
                    s.append(exp.toMiniString());
                }
            }

        }
    }

    private String spacedOperator() {
        return String.format(" %s ", getOperator().toString());
    }
}
