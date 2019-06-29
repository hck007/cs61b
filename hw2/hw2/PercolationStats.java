package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] allTrials;
    private int numTrials;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        allTrials = new double[T];
        numTrials = T;
        int size = N * N;
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            allTrials[i] = p.numberOfOpenSites() / (N * N);
        }

    }
    // sample mean of percolation threshold
    public double mean() {
        double ans = StdStats.mean(allTrials);
        return ans;
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        double ans = StdStats.stddev(allTrials);
        return ans;
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double ans = mean() - ((1.96 * stddev()) / Math.sqrt(numTrials));
        return ans;
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double ans = mean() + ((1.96 * stddev()) / Math.sqrt(numTrials));
        return ans;
    }
}
