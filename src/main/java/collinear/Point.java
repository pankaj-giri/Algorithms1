package collinear;

/*************************************************************************
 * Name: Pankaj Giri
 * Email: giri.pankaj@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

	// compare points by slope
	public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {

		@Override
		public int compare(Point o1, Point o2) {
			return -1*Double.compare(Point.this.slopeTo(o2), Point.this.slopeTo(o1));
		}
	};

	private final int x; // x coordinate
	private final int y; // y coordinate

	// create the point (x, y)
	public Point(int x, int y) {
		/* DO NOT MODIFY */
		this.x = x;
		this.y = y;
	}

	// plot this point to standard drawing
	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	// draw line between this point and that point to standard drawing
	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	// slope between this point and that point
	public double slopeTo(Point that) {
//		int deltaX = that.x - this.x;
//		int deltaY = that.y - this.y;
//		if (deltaX == 0) {
//			return deltaY < 0 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
//		} else if (deltaY == 0) {
//			return 0.0;
//		}
//		return ((deltaY) * 1.0 / (deltaX) * 1.0);
    	if (this.x == that.x && this.y != that.y) {
    		return Double.POSITIVE_INFINITY;
    	} else if (this.x == that.x && this.y == that.y) {
    		return Double.NEGATIVE_INFINITY;
    	} else if (that.y - this.y == 0) {
    		return 0.0;
    	}
    	
        return ((double)(that.y - this.y) / (double)(that.x - this.x));
	}

	// is this point lexicographically smaller than that one?
	// comparing y-coordinates and breaking ties by x-coordinates
	public int compareTo(Point that) {
		/* YOUR CODE HERE */
//		if ((this.y < that.y) || (this.y == that.y && this.x > that.x))
//			return 1;
//		else if (that.y == y && that.x == x)
//			return 0;
//		else
//			return -1;
    	if (this.y < that.y) {
        	return -1;
        } else if (this.y > that.y) {
        	return 1;
        } else {
        	if (this.x < that.x) {
        		return -1;
        	} else if (this.x > that.x) {
        		return 1;
        	} else {
        		return 0;
        	}
        }
	}

	// return string representation of this point
	public String toString() {
		/* DO NOT MODIFY */
		return "(" + x + ", " + y + ")";
	}

	// unit test
	public static void main(String[] args) {
		/* YOUR CODE HERE */
	}
}