package expression.generic.operations;

public interface Expression<T> {
    T evaluate(T x);
}