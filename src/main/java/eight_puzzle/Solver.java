package eight_puzzle;

import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

	private MinPQ<SearchNode> queue = new MinPQ<>();
	private Board initial;

	// find a solution to the initial Board2D (using the A* algorithm)
	public Solver(Board initial) {
		this.initial = initial;
	}

	// is the initial Board2D solvable?
	public boolean isSolvable() {
		return true;
	}

	// min number of moves to solve initial Board2D; -1 if unsolvable
	public int moves() {
		board2Ds.clear();
		solution();
		return board2Ds.size();
	}

	//LIFO based - more natural fit to the problem
	private Stack<Board> board2Ds = new Stack<Board>();

	// sequence of Board2Ds in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		if (!board2Ds.isEmpty())
			return board2Ds;

		if (queue.isEmpty()) {
			queue.insert(new SearchNode(0, initial, null));
		}

		while (true) {
			SearchNode node = queue.delMin();
			if (node.board2d.isGoal()) {
				SearchNode prev = node;
				while (prev.previousSearchNode != null) {
					board2Ds.add(prev.board2d);
					prev = prev.previousSearchNode;
				}
				break;
			} else {
				for (Board board2D : node.board2d.neighbors()) {
					if ((node.previousSearchNode == null) || !board2D.equals(node.previousSearchNode.board2d)) {
						SearchNode n1 = new SearchNode(node.numOfMoves + 1, board2D, node);
						queue.insert(n1);
					}
				}
				node = null;
			}
		}

		return board2Ds;
	}
	
	public void printSolution() {
		if(board2Ds.isEmpty()) {
			System.out.println("Nothing to print");
			return;
		}
		while(!board2Ds.isEmpty()) {
			StdOut.println(board2Ds.pop());
		}
	}

	private class SearchNode implements Comparable<SearchNode> {
		int numOfMoves = 0;
		private Board board2d;
		SearchNode previousSearchNode;

		public boolean equals(Object node) {
			return board2d.equals(((SearchNode) node).board2d);
		}

		SearchNode(int n, Board b, SearchNode node) {
			this.numOfMoves = n;
			this.board2d = b;
			this.previousSearchNode = node;
		}

		@Override
		public int compareTo(SearchNode o) {
			int compare = Integer.valueOf(board2d.manhattan() + numOfMoves)
					.compareTo(Integer.valueOf(o.board2d.manhattan() + o.numOfMoves));
			if (compare == 0) {
				return Integer.valueOf(board2d.hamming()).compareTo(o.board2d.hamming());
			}
			return compare;
		}
	}

	public static void main(String[] args) {
		// create initial Board2D from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			solver.printSolution();
		}
	}
}