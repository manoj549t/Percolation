/**
 * @author - Manoj Raghunathan
 * @created - 09/22/2017
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

	private double mean 	= 0;
	private double stddev	= 0;
	private double[] probability;
	
	private static final int LOW 	= 0;
	private static final int HIGH 	= 1;
	
		public PercolationStats(int n, int trials) {
			probability = new double[2]; // Save the high & low range of confidence
			if(trials <= 0) {
				throw new java.lang.IllegalArgumentException("Value for Trials: " + trials + " is invalid. It cannot be less than or equal to 0.");
			}
			if(n <= 0) {
				throw new java.lang.IllegalArgumentException("The grid size: " + trials + " is invalid. It cannot be less than or equal to 0.");
			}
			percolationTest(n, trials); // Run the trial tests with random #
		}
		
		public double mean() {
			return mean;
		}
		
		public double stddev() {
			return stddev;
		}
		
		public double confidenceLo() {
			return probability[LOW];
		}
		
		public double confidenceHi() {
			return probability[HIGH];
		}
		
		private void percolationTest(int n, int trials) {
			
			double[] trialResults = new double[trials];
			int row = 0;
			int col = 0;
			double confidenceRatio = 1.96 / Math.sqrt(trials);
			
			for(int i=0; i < trials; i++) {
				Percolation pGrid = new Percolation(n);
				
				while (!pGrid.percolates()) {
					row = StdRandom.uniform(n) + 1;
					col = StdRandom.uniform(n) + 1;
					
					pGrid.open(row, col);
				}

				trialResults[i] = (double)pGrid.numberOfOpenSites()/(n * n);
			}
			
			this.mean = StdStats.mean(trialResults);
			this.stddev = StdStats.stddev(trialResults);
			
			this.probability[LOW] = this.mean - confidenceRatio * this.stddev;
			this.probability[HIGH] = this.mean + confidenceRatio * this.stddev;
		}

		public static void main(String[] args) {
			
			Stopwatch runtime = new Stopwatch();	
			
			PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			//PercolationStats percolationStats = new PercolationStats(200, 100);
			System.out.println("Elapsed Time: " + runtime.elapsedTime());			
			System.out.println("mean			=  " + percolationStats.mean());
			System.out.println("stddev			=  " + percolationStats.stddev());
			System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() +"," + percolationStats.confidenceHi() + "]");
		}       // test client (described below)

}

