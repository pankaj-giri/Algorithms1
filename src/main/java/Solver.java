
import java.util.ArrayList;
import java.util.List;

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
		Board2Ds.clear();
		solution();
		return Board2Ds.size();
	}

	private List<Board> Board2Ds = new ArrayList<Board>();

	// sequence of Board2Ds in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		if (!Board2Ds.isEmpty())
			return Board2Ds;

		if (queue.isEmpty()) {
			queue.insert(new SearchNode(0, initial, null));
		}

		while (true) {
			SearchNode node = queue.delMin();
			if (node.Board2D.isGoal()) {
				SearchNode prev = node;
				// while (!prev.Board2D.equals(initial)) {
				while (prev.previousSearchNode != null) {
					Board2Ds.add(prev.Board2D);
					prev = prev.previousSearchNode;
				}
				break;
			} else {
				for (Board Board2D : node.Board2D.neighbors()) {
					if ((node.previousSearchNode == null) || !Board2D.equals(node.previousSearchNode.Board2D)) {
						SearchNode n1 = new SearchNode(node.numOfMoves + 1, Board2D, node);
						queue.insert(n1);
					}
				}
				node = null;
			}
		}

		return Board2Ds;
	}

	private class SearchNode implements Comparable<SearchNode> {
		int numOfMoves = 0;
		public Board Board2D;
		SearchNode previousSearchNode;

		public boolean equals(Object node) {
			return Board2D.equals(((SearchNode) node).Board2D);
		}

		SearchNode(int n, Board b, SearchNode node) {
			this.numOfMoves = n;
			this.Board2D = b;
			this.previousSearchNode = node;
		}

		@Override
		public int compareTo(SearchNode o) {
			int compare = Integer.valueOf(Board2D.manhattan() + numOfMoves)
					.compareTo(Integer.valueOf(o.Board2D.manhattan() + o.numOfMoves));
			if (compare == 0) {
				return Integer.valueOf(Board2D.hamming()).compareTo(o.Board2D.hamming());
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
			for (Board Board2D : solver.solution())
				StdOut.println(Board2D);
		}
	}
}