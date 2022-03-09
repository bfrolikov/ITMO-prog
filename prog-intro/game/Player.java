package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
// :NOTE: Вернуть игроков
public interface Player {
    Move move(Position position, Cell cell);
}
