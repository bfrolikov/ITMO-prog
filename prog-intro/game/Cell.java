package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public enum Cell {
    X('X'), O('O'), DASH('-'), STROKE('|'), E('.'), N(' ');
    private static final int TURN_CHAR_MAX_INDEX = 3;
    private final char representation;

    Cell(final char representation) {
        this.representation = representation;
    }

    public static int getTurnCharMaxIndex() {
        return TURN_CHAR_MAX_INDEX;
    }

    public Cell getNextTurn(final int playerCount) {
        if (ordinal() > TURN_CHAR_MAX_INDEX)
            throw new IllegalArgumentException(this + "is not a valid turn character");
        if (playerCount > TURN_CHAR_MAX_INDEX + 1) {
            throw new IllegalArgumentException("Not enough turn characters for " + playerCount + " players");
        }
        return values()[(this.ordinal() + 1) % playerCount];
    }

    @Override
    public String toString() {
        return Character.toString(representation);
    }
}
