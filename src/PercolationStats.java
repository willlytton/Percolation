import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;

public class PercolationStats {
    private int n;
    private double mean; // initializes threshold for mean
    private double stddev; // initializes threshold for standard deviation
    private double[] results; // gathers results for mean and standard deviation
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n); // imports percolation
            double openSites = 0.0;
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    openSites++;
                }
            }
            results[i] = openSites / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {

        return mean = StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev = StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confidenceInterval();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confidenceInterval();
    }

    private double confidenceInterval() {
        return 1.96 * stddev() / Math.sqrt(trials);
    }

//    public void runMonteCarlo(int n, int trials) {
//        Percolation percolation = new Percolation(n); // imports percolation
//        while (!percolation.percolates()) {
//            for (int i = 0; i < trials; i++) {
//                results = new double[trials];
//                int row = StdRandom.uniformInt(1, n);
//                int col = StdRandom.uniformInt(1, n);
//                if (!percolation.isOpen(row, col)) {
//                    percolation.open(row, col);
//                    percolation.numberOfOpenSites();
//                }
//            }
//        }
//    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);

        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = " + " [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "] ");

    }

}

