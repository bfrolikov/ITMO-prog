package game;

import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Game {
    private final boolean log;
    private final List<Player> players;

    public Game(final boolean log, final List<Player> players) {
        this.log = log;
        this.players = players;
    }

    public Result play(final Board board) {
        board.setPlayerCount(players.size());
        while (true) {
            for (int p = 0; p < players.size(); p++) {
                final Result result = move(board, p);
                if (result != Result.UNKNOWN) {
                    return result;
                }
            }
        }
    }

    private Result move(final Board board, final int no) {
        final Player player = players.get(no);
        final Move move = player.move(board.getPosition(), board.getCell());
        final Result result = board.makeMove(move);
        log("Player " + no + " move: " + move);
        log("Position:\n" + board);
        if (result == Result.WIN) {
            log("Player " + no + " won");
            result.setPlayer(no);
        } else if (result == Result.INVALID) {
            log("Player " + no + " made an invalid move");
            result.setPlayer(no);
        } else if (result == Result.DRAW) {
            log("Draw");
        }
        return result;
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
