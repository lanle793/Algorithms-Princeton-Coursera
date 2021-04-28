/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;

    private final double[] thresEsts;
    private final int t;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.t = trials;
        thresEsts = new double[trials];
        int j = 0;

        while (trials > 0) {
            Percolation perc = new Percolation(n);

            while (!perc.percolates()) {
                int openIndex = StdRandom.uniform(n * n);
                int row = openIndex / n;
                int col = openIndex % n;
                if (!perc.isOpen(row + 1, col + 1)) {
                    perc.open(row + 1, col + 1);
                }
            }
            thresEsts[j] = (double) perc.numberOfOpenSites() / (n * n);
            j++;
            trials--;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresEsts);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresEsts);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(t);
    }

    // test client
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stat = new PercolationStats(n, trials);

        StdOut.println("mean = " + stat.mean());
        StdOut.println("stddev = " + stat.stddev());
        StdOut.println(
                "95% confidence interval = [" + stat.confidenceLo() + ", " + stat.confidenceHi()
                        + "]");
    }
}
