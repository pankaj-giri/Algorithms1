package collinear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class Fast {
//	private Map<Double, List<Point>> dataMap = new HashMap<Double, List<Point>>();

	public static void main(String[] args) {
		StdDraw.setCanvasSize(1024, 768);
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		// StdDraw.show(0);
		StdDraw.setPenRadius(0.01); // make the points a bit larger

		Fast fast = new Fast();
		fast.populatePoints(args[0]);
		fast.plotPoints();
		// display to screen all at once
		StdDraw.show(0);

		// reset the pen radius
		StdDraw.setPenRadius();
	}

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
		StdDraw.setPenRadius();
	}

	private void plotPoints() {
		Point[] tmpArray = new Point[N];
		System.arraycopy(points, 0, tmpArray, 0, N);
		for ( int i = 0; i < N-1; i++ ) {
			Arrays.sort(tmpArray, points[i].SLOPE_ORDER);
			Map<Double, List<Point>> dataMap = new HashMap<Double, List<Point>>();
			for ( int j = i+1; j < N; j++ ) {
				double slope = points[i].slopeTo(tmpArray[j]);
				if(dataMap.containsKey(slope)) {
					dataMap.get(slope).add(tmpArray[j]);
				} else {
					List<Point> arrayList = new ArrayList<Point>();
					arrayList.add(points[i]);
					arrayList.add(tmpArray[j]);
					dataMap.put(slope, arrayList);
				}
			}
			
			for(double key : dataMap.keySet()) {
				if((dataMap.get(key).size()) < 4) continue;
				List<Point> aPoints = dataMap.get(key);
				Collections.sort(aPoints);
				for(int x=0; x<aPoints.size(); x++) {
					System.out.print(aPoints.get(x));
					if(x < aPoints.size()-1) 
						System.out.print(" -> ");
				}
				System.out.println("");
				aPoints.get(0).drawTo(aPoints.get(aPoints.size()-1));
//				// display to screen all at once
				StdDraw.show(0);

				// reset the pen radius
				StdDraw.setPenRadius();
			}
		}
	}
}