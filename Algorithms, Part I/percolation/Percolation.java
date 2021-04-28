/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int size;
    private WeightedQuickUnionUF wQuickUnion;

    // create n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size has to be larger than 0");
        }

        // site status: 0-blocked, 1-open
        this.grid = new int[n][n];
        this.size = n;
        this.wQuickUnion = new WeightedQuickUnionUF(n * n);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Row or column index out of range");
        }

        int index = getIndex(row, col);
        grid[row - 1][col - 1] = 1;

        // connect this site to open neighbors
        if (col < size && grid[row - 1][col] == 1) { // right
            wQuickUnion.union(index, index + 1);
        }
        if (col - 2 >= 0 && grid[row - 1][col - 2] == 1) { // left
            wQuickUnion.union(index, index - 1);
        }
        if (row < size && grid[row][col - 1] == 1) { // down
            wQuickUnion.union(index, index + size);
        }
        if (row - 2 >= 0 && grid[row - 2][col - 1] == 1) { // up
            wQuickUnion.union(index, index - size);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Row or column index out of range");
        }

        return grid[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Row or column index out of range");
        }

        int index = getIndex(row, col);
        for (int i = 0; i < size; i++) {
            if (isOpen(1, i + 1) && connected(i, index)) {
                return true;
            }
        }

        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int numOpen = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isOpen(i + 1, j + 1)) {
                    numOpen++;
                }
            }
        }
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < size; i++) {
            if (isFull(size, i + 1)) {
                return true;
            }
        }
        return false;
    }

    // get the index of the site in WeightedQuickUnionUF
    private int getIndex(int row, int col) {
        return (row - 1) * size + col - 1;
    }

    private boolean connected(int p, int q) {
        return wQuickUnion.find(p) == wQuickUnion.find(q);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
