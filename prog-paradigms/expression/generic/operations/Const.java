package expression.generic.operations;

public class Const<T> implements GeneralExpression<T> {
    private final T value;

    public Const(T value) {
        this.value = value;
    }

    @Override
    public T evaluate(T x) {
        return value;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return value;
    }

}
