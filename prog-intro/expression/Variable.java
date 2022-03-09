package expression;

public class Variable extends AbstractExpressionWithOperator {
    private final String name;

    public Variable(final String name) {
        this.name = name;
    }

    @Override
    public Operator getOperator() {
        return Operator.NONE;
    }

    @Override
    public int evaluate(final int x) {
        return x;
    }

    @Override
    public double evaluate(final double x) {
        return x;
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        switch (name) {
            case "z":
                return z;
            case "y":
                return y;
            default:
                return x;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Variable that = (Variable) obj;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
