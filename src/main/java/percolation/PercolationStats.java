package percolation;

import edu.princeton.cs.algs4.StdRandom;

/**
 * 
 * @author pankaj
 *
 */
public class PercolationStats {
	private double[] thresholdList;
	private int N;
	private int T;

	public PercolationStats(int N, int T) {
		checkArguments(N, T);
		thresholdList = new double[T];

		this.N = N;
		this.T = T;
		// perform T independent experiments on an N-by-N grid
		// repeat this T times and capture the ratio of open sites to all sites
		// for every run and calculate the average.
		for ( int i = 0; i < T; i++ ) {
			Percolation percolation = new Percolation(N);
			while (!percolation.percolates()) {
				StdRandom.uniform(1, N);
				// PercolationVisualizer.draw(percolation, N);
				int x = StdRandom.uniform(1, N + 1);
				int y = StdRandom.uniform(1, N + 1);

				if (!percolation.isOpen(x, y)) {
					percolation.open(x, y);
				}
			}
			// PercolationVisualizer.draw(percolation, N);
			thresholdList[i] = getOpenSites(percolation) / (N * N * 1.0d);
		}
	}

	private void checkArguments(int n2, int t2) {
		if ((n2 <= 0) || (t2 <= 0)) {
			throw new IllegalArgumentException();
		}
	}

	public double mean() {
		double sum = 0.0;
		for ( double d : thresholdList ) {
			sum += d;
		}
		return sum / T;
	}

	public double stddev() {
		// sample standard deviation of percolation threshold
		double mean = mean();
		double stdDev = 0.0;
		for ( double d : thresholdList ) {
			double diff = d - mean;
			stdDev = stdDev + (diff * diff);
		}
		return Math.sqrt(stdDev / (T - 1));
	}

	public double confidenceLo() {
		// low endpoint of 95% confidence interval
		return mean() - ((1.96 * stddev()) / Math.sqrt(T));
	}

	public double confidenceHi() {
		// high endpoint of 95% confidence interval
		return mean() + ((1.96 * stddev()) / Math.sqrt(T));
	}

	private int getOpenSites(Percolation percolation) {
		int counter = 0;
		for ( int i = 1; i <= N; i++ ) {
			for ( int j = 1; j <= N; j++ ) {
				if (percolation.isOpen(i, j)) {
					counter++ ;
				}
			}
		}
		return counter;
	}

	public static void main(String[] args) {
		if (args.length != 2)
			throw new IllegalArgumentException();

		PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		System.out.println("mean 						= " + stats.mean());
		System.out.println("stddev						= " + stats.stddev());
		System.out.println("95% confidence interval		= " + stats.confidenceLo() + "," + stats.confidenceHi());

	}
}