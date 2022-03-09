package game;

import java.util.Arrays;
import java.util.List;

public class MNKBoard implements Board {

    protected final Cell[][] cells;
    protected final int m, n, k;
    private final List<Direction> directions;
    private final Position position;
    private int playerCount;
    private int count;
    private Cell turn;

    public MNKBoard(final int m, final int n, final int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        cells = new Cell[n][m];
        playerCount = 2;
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        count = 0;
        directions = List.of(
                new Direction(0, 1, true),
                new Direction(1, 0, true),
                new Direction(1, 1, true),
                new Direction(1, -1, true)
        );
        position = new Position() {
            @Override
            public boolean isValid(final Move move) {
                return checkBorders(move.getRow(), move.getColumn())
                        && cells[move.getRow()][move.getColumn()] == Cell.E
                        && turn == move.getValue();
            }

            @Override
            public int getHeight() {
                return n;
            }

            @Override
            public int getWidth() {
                return m;
            }

            @Override
            public Cell getCell(final int r, final int c) {
                return cells[r][c];
            }

            @Override
            public String toString() {
                return MNKBoard.this.toString();
            }
        };
    }

    @Override
    public void setPlayerCount(final int playerCount) {
        this.playerCount = playerCount;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!position.isValid(move)) {
            return Result.INVALID;
        }
        final int moveRow = move.getRow();
        final int moveCol = move.getColumn();
        cells[moveRow][moveCol] = move.getValue();
        count++;
        for (Direction d : directions) {
            d.reset();
            d.checkDirection(moveRow, moveCol);
        }
        for (Direction d : directions) {
            if (d.getCount() >= k)
                return Result.WIN;
        }
        if (count == m * n) {
            return Result.DRAW;
        }
        turn = getNextTurn();
        return Result.UNKNOWN;
    }

    private Cell getNextTurn() {
        return turn.getNextTurn(playerCount);
    }


    private boolean checkBorders(final int row, final int col) {
        return 0 <= row && row < n
                && 0 <= col && col < m;
    }

    private boolean cellIsTurn(final int row, final int col) {
        return checkBorders(row, col) && cells[row][col] == turn;
    }


    private int getDigitCount(final int number) {
        return number == 0 ? 1 : (int) Math.log10(number) + 1;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ".repeat(getDigitCount(n) + 1));
        final int nDigitCount = getDigitCount(n);
        final int mDigitCount = getDigitCount(m);
        for (int c = 0; c < m; c++) {
            sb.append(c);
            sb.append(" ".repeat(mDigitCount - getDigitCount(c) + 1));
        }
        for (int r = 0; r < n; r++) {
            sb.append("\n");
            sb.append(r);
            sb.append(" ".repeat(nDigitCount - getDigitCount(r) + 1));
            for (int c = 0; c < m; c++) {
                sb.append(cells[r][c]);
                sb.append(" ".repeat(mDigitCount));
            }
        }
        return sb.toString();
    }


    private class Direction {
        private final int cx, cy;
        private final boolean reversible;
        private int count;

        private Direction(final int cx, final int cy, final boolean reversible) {
            this.cx = cx;
            this.cy = cy;
            this.reversible = reversible;
            count = 0;
        }

        private void checkDirection(final int row, final int col) {
            checkSurrounding(row, col, false);
            if (reversible)
                checkSurrounding(row, col, true);
            count += cellIsTurn(row, col) ? 1 : 0;
        }

        private void checkSurrounding(final int row, final int col, final boolean reverse) {
            final int rev = reverse ? -1 : 1;
            int i = 1;
            while (cellIsTurn(row + cx * i * rev, col + cy * i * rev)) {
                count++;
                i++;
            }
        }

        private void reset() {
            count = 0;
        }

        private int getCount() {
            return count;
        }
    }

}
