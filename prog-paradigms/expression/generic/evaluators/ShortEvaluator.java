package expression.generic.evaluators;

import expression.exceptions.ZeroDivisionException;

public class ShortEvaluator extends EvaluatorWithParser<Short> {
    @Override
    public Short add(final Short first,final Short second) {
        return (short) (first + second);
    }

    @Override
    public Short subtract(final Short first,final Short second) {
        return (short) (first - second);
    }

    @Override
    public Short multiply(final Short first,final Short second) {
        return (short) (first * second);
    }

    @Override
    public Short divide(final Short first,final Short second) {
        if (second == 0) {
            throw new ZeroDivisionException("Division by zero happened during evaluation");
        }
        return (short) (first / second);
    }

    @Override
    public Short mod(final Short first,final Short second) {
        if (second == 0) {
            throw new ZeroDivisionException("Modulo by zero happened during evaluation");
        }
        return (short) (first % second);
    }

    @Override
    public Short negate(final Short first) {
        return (short) -first;
    }

    @Override
    public Short abs(final Short first) {
        return (short) Math.abs(first);
    }

    @Override
    public Short square(final Short first) {
        return (short) (first * first);
    }

    @Override
    public Short fromString(final String s) {
        return Short.parseShort(s);
    }

    @Override
    public Short fromInt(final int i) {
        return (short) i;
    }
}
