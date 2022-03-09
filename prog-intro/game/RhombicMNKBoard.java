package game;

import java.util.Arrays;

public class RhombicMNKBoard extends MNKBoard {

    public RhombicMNKBoard(final int n, final int k) {
        super(n, n, k);
        fillCells();
    }

    private void fillCells() {
        final int diagTop = (n - 1) / 2;
        final int diagBottom = n % 2 == 0 ? diagTop + 1 : diagTop;
        int padding = 0;
        for (int r = diagTop - 1; r >= 0; r--, padding++) {
            fillRow(padding, r);
        }
        padding = 0;
        for (int r = diagBottom + 1; r < n; r++, padding++) {
            fillRow(padding, r);
        }
    }

    private void fillRow(final int padding, final int row) {
        Arrays.fill(cells[row], 0, padding + 1, Cell.N);
        Arrays.fill(cells[row], n - padding - 1, n, Cell.N);
    }
}
