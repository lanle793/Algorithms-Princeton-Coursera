/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int numOpenSites;
    private final int size;
    private final int virtualTop;
    private final int virtualBottom;
    private final WeightedQuickUnionUF wQuickUnion;

    // create n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size has to be larger than 0");
        }

        // site status: 0-blocked, 1-open
        this.grid = new boolean[n][n];
        this.size = n;
        this.virtualTop = n * n;
        this.virtualBottom = n * n + 1;
        this.wQuickUnion = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Row or column index out of range");
        }

        int index = getIndex(row, col);
        grid[row - 1][col - 1] = true;
        numOpenSites++;

        if (row == 1) {
            wQuickUnion.union(virtualTop, index);
        }

        if (row == size) {
            wQuickUnion.union(virtualBottom, index);
        }

        // connect this site to open neighbors
        if (col < size && grid[row - 1][col]) { // right
            wQuickUnion.union(index, index + 1);
        }
        if (col - 2 >= 0 && grid[row - 1][col - 2]) { // left
            wQuickUnion.union(index, index - 1);
        }
        if (row < size && grid[row][col - 1]) { // down
            wQuickUnion.union(index, index + size);
        }
        if (row - 2 >= 0 && grid[row - 2][col - 1]) { // up
            wQuickUnion.union(index, index - size);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Row or column index out of range");
        }

        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Row or column index out of range");
        }

        int index = getIndex(row, col);
        return isConnected(virtualTop, index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return isConnected(virtualTop, virtualBottom);
    }

    // get the index of the site in WeightedQuickUnionUF
    private int getIndex(int row, int col) {
        return (row - 1) * size + col - 1;
    }

    private boolean isConnected(int p, int q) {
        return wQuickUnion.find(p) == wQuickUnion.find(q);
    }
}
