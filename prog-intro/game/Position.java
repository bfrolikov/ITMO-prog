package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Position {
    boolean isValid(Move move);
    int getHeight();
    int getWidth();
    Cell getCell(int r, int c);
}
