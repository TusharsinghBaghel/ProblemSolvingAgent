package search.puzzles.eightpuzzle;

import java.util.Arrays;

public class State {
    private final int[] tiles;  // Flattened 3x3 board
    private final int zeroIndex; // Position of blank (0)

    public State(int[] tiles) {
        this.tiles = Arrays.copyOf(tiles, tiles.length);
        this.zeroIndex = findZero();
    }

    private int findZero() {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) return i;
        }
        throw new IllegalArgumentException("No blank (0) tile found!");
    }

    public int[] getTiles() {
        return Arrays.copyOf(tiles, tiles.length);
    }

    public int getZeroIndex() {
        return zeroIndex;
    }

    public int getRow() { return zeroIndex / 3; }
    public int getCol() { return zeroIndex % 3; }

    // Generate new state after a move
    public State move(Action action) {
        int row = getRow(), col = getCol();
        int newRow = row, newCol = col;

        switch (action) {
            case UP -> newRow = row - 1;
            case DOWN -> newRow = row + 1;
            case LEFT -> newCol = col - 1;
            case RIGHT -> newCol = col + 1;
        }

        // Check bounds
        if (newRow < 0 || newRow > 2 || newCol < 0 || newCol > 2) {
            return null; // illegal move
        }

        int newZeroIndex = newRow * 3 + newCol;
        int[] newTiles = Arrays.copyOf(tiles, tiles.length);

        // Swap blank with target tile
        newTiles[zeroIndex] = newTiles[newZeroIndex];
        newTiles[newZeroIndex] = 0;

        return new State(newTiles);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof State other)) return false;
        return Arrays.equals(this.tiles, other.tiles);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tiles);
    }

    @Override
    public String toString() {
        return String.format("%d %d %d\n%d %d %d\n%d %d %d\n",
                tiles[0], tiles[1], tiles[2],
                tiles[3], tiles[4], tiles[5],
                tiles[6], tiles[7], tiles[8]);
    }
}
