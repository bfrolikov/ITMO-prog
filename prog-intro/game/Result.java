package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public enum Result {
    WIN, INVALID, DRAW, UNKNOWN;
    private int player = -1;

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    @Override
    public String toString() {
        if (this == WIN) {
            return "Player " + (player + 1) + " won";
        } else if (this == INVALID) {
            return "Player " + (player + 1) + " made an invalid move";
        }
        return this.name();
    }
}
