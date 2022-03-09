package expression.generic.evaluators;

public interface Evaluator<T> {
    T add(final T first, final T second);

    T subtract(final T first, final T second);

    T multiply(final T first, final T second);

    T divide(final T first, final T second);

    T mod(final T first, final T second);

    T negate(final T first);

    T abs(final T first);

    T square(final T first);

    T fromString(final String s);

    T fromInt(final int i);
}
