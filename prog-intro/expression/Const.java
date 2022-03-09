package expression;

public class Const extends AbstractExpressionWithOperator {
    private final Number value;

    public Const(final int integerValue) {
        value = integerValue;
    }

    public Const(final double doubleValue) {
        value = doubleValue;
    }
    @Override
    public Operator getOperator() {
        return Operator.NONE;
    }
    @Override
    public int evaluate(final int x) {
        return value.intValue();
    }

    @Override
    public double evaluate(final double x) {
        return value.doubleValue();
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return value.intValue();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Const that = (Const) obj;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
