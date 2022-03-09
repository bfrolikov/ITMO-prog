package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Board {
    Position getPosition();
    Cell getCell(); //previously getTurn
    Result makeMove(Move move);
    void setPlayerCount(int playerCount);
}
