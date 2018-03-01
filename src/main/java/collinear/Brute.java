package collinear;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/*************************************************************************
 * Name: Pankaj Giri
 * Email: giri.pankaj@gmail.com
 *
 * Compilation:  javac Brute.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: examines all permutations of 4 points at a time and 
 * checks whether they all lie on the same line segment
 *
 *************************************************************************/
public class Brute {
	private Point[] points;
	private int N;

	private void populatePoints(String fileName) {
		In in = new In(fileName);
		N = in.readInt();
		points = new Point[N];
		for ( int i = 0; i < N; i++ ) {
			int x = in.readInt();
			int y = in.readInt();
			Point p = new Point(x, y);
			p.draw();
			points[i] = p;
		}

		// display to screen all at once
		StdDraw.show(0);

		// reset the pen radius
		StdDraw.setPenRadius();
	}

	private void plotPoints() {
		for ( int i = 0; i < (N - 3); i++ ) {
			for ( int j = i + 1; j < (N - 2); j++ ) {
				for ( int k = j + 1; k < (N - 1); k++ ) {
					for ( int l = k + 1; l < (N); l++ ) {
						double slope1 = points[i].slopeTo(points[j]);
						double slope2 = points[i].slopeTo(points[k]);
						double slope3 = points[i].slopeTo(points[l]);
						if (Double.compare(slope1, slope2) == 0 && Double.compare(slope1, slope3) == 0) {
							Point[] tmpA = new Point[4];
							tmpA[0] = points[i];
							tmpA[1] = points[j];
							tmpA[2] = points[k];
							tmpA[3] = points[l];
							Arrays.sort(tmpA);
							
							//Output to standard out and draw
							StdOut.print(tmpA[0].toString());
							StdOut.print(" -> ");
							StdOut.print(tmpA[1].toString());
							StdOut.print(" -> ");
							StdOut.print(tmpA[2].toString());
							StdOut.print(" -> ");
							StdOut.print(tmpA[3].toString());
							StdOut.println();
							
							tmpA[0].drawTo(tmpA[3]);
						}
					}
				}
			}
		}
		StdDraw.show(0);

		// reset the pen radius
		StdDraw.setPenRadius();
	}

	public static void main(String[] args) {
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		// StdDraw.show(0);
		StdDraw.setPenRadius(0.01); // make the points a bit larger
		// rescale coordinates and turn on animation mode
		Brute brute = new Brute();
		brute.populatePoints(args[0]);
		brute.plotPoints();
		// display to screen all at once
		StdDraw.show(0);

		// reset the pen radius
		StdDraw.setPenRadius();
	}
}