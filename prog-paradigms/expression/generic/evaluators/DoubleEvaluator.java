package expression.generic.evaluators;


public class DoubleEvaluator extends EvaluatorWithParser<Double> {
    @Override
    public Double add(final Double first, final Double second) {
        return first + second;
    }

    @Override
    public Double subtract(final Double first, final Double second) {
        return first - second;
    }

    @Override
    public Double multiply(final Double first, final Double second) {
        return first * second;
    }

    @Override
    public Double divide(final Double first, final Double second) {
        return first / second;
    }

    @Override
    public Double mod(final Double first, final Double second) {
        return first % second;
    }

    @Override
    public Double negate(final Double first) {
        return -first;
    }

    @Override
    public Double abs(final Double first) {
        return Math.abs(first);
    }

    @Override
    public Double square(final Double first) {
        return first * first;
    }

    @Override
    public Double fromString(final String s) {
        return Double.parseDouble(s);
    }

    @Override
    public Double fromInt(final int i) {
        return (double) i;
    }
}
