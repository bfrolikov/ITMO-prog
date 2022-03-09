package ru.itmo.wp.util.tictactoe;

import java.util.List;

public class State {
    private final Cell[][] cells;
    private final int size;
    private boolean crossesMove;
    private final List<Direction> directions;
    private int occupiedCells;

    private Phase phase;

    public State(int size) {
        this.size = size;
        cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = null;
            }
        }
        directions = List.of(
                new Direction(0, 1),
                new Direction(1, 0),
                new Direction(1, 1),
                new Direction(1, -1)
        );
        occupiedCells = 0;
        phase = Phase.RUNNING;
        crossesMove = true;
    }

    public void makeMove(int x, int y) {
        if (phase != Phase.RUNNING || !checkBorders(x, y)) {
            return;
        }
        cells[x][y] = getTurnCell();
        occupiedCells++;
        for (Direction d : directions) {
            if (d.checkDirection(x, y) >= size) {
                phase = crossesMove ? Phase.WON_X : Phase.WON_O;
                return;
            }
        }
        if (occupiedCells == size * size) {
            phase = Phase.DRAW;
            return;
        }
        crossesMove = !crossesMove;
    }

    private boolean checkBorders(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    private Cell getTurnCell() {
        return crossesMove ? Cell.X : Cell.O;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getSize() {
        return size;
    }

    public boolean isCrossesMove() {
        return crossesMove;
    }

    public Phase getPhase() {
        return phase;
    }

    private class Direction {
        private final int dx;
        private final int dy;

        public Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public int checkDirection(int x, int y) {
            int count = 0;
            count += checkSurrounding(x, y, false);
            count += checkSurrounding(x, y, true);
            count += cellIsTurn(x, y) ? 1 : 0;
            return count;
        }

        private int checkSurrounding(int x, int y, boolean reverse) {
            final int rev = reverse ? -1 : 1;
            int i = 1, count = 0;
            while (cellIsTurn(x + dx * i * rev, y + dy * i * rev)) {
                count++;
                i++;
            }
            return count;
        }

        private boolean cellIsTurn(int x, int y) {
            return checkBorders(x, y) && cells[x][y] == getTurnCell();
        }
    }


}