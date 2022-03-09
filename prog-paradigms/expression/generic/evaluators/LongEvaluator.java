package expression.generic.evaluators;

import expression.exceptions.ZeroDivisionException;

public class LongEvaluator extends EvaluatorWithParser<Long> {
    @Override
    public Long add(final Long first, final Long second) {
        return first + second;
    }

    @Override
    public Long subtract(final Long first, final Long second) {
        return first - second;
    }

    @Override
    public Long multiply(final Long first, final Long second) {
        return first * second;
    }

    @Override
    public Long divide(final Long first, final Long second) {
        if (second == 0) {
            throw new ZeroDivisionException("Division by zero happened during evaluation");
        }
        return first / second;
    }

    @Override
    public Long mod(final Long first, final Long second) {
        if (second == 0) {
            throw new ZeroDivisionException("Modulo by zero happened during evaluation");
        }
        return first % second;
    }

    @Override
    public Long negate(final Long first) {
        return -first;
    }

    @Override
    public Long abs(final Long first) {
        return Math.abs(first);
    }

    @Override
    public Long square(final Long first) {
        return first * first;
    }

    @Override
    public Long fromString(final String s) {
        return Long.parseLong(s);
    }

    @Override
    public Long fromInt(final int i) {
        return (long) i;
    }
}
