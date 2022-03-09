package expression.generic.operations;

public class Variable<T> implements GeneralExpression<T> {
    private final String name;

    public Variable(final String name) {
        this.name = name;
    }

    @Override
    public T evaluate(T x) {
        return x;
    }

    @Override
    public T evaluate(final T x, final T y, final T z) {
        switch (name) {
            case "z":
                return z;
            case "y":
                return y;
            default:
                return x;
        }
    }

}
