package eight_puzzle;


import java.util.ArrayList;
import java.util.List;

public class Board {

	private final int[][] board;
	private static final int INVALID_POSITION = -1;
	private static final int INVALID_VALUE = -1;
	private static final int[][] neighbourOffset = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
	private int N;

	// construct a board from an N-by-N array of blocks
	public Board(int[][] blocks) {
		if (blocks != null) {
			if (blocks.length != blocks[0].length) {
				throw new IllegalArgumentException();
			}
		}
		N = blocks.length;
		board = blocks;
	}

	private int getGoalValueForBlock(int row, int column) {
		// Last block is assumed to be the empty one, i.e. 0
		if (row == dimension() - 1 && column == dimension() - 1) {
			return 0;

		} else {
			return (row * dimension()) + column + 1;
		}
	}

	// (where blocks[i][j] = block in row i, column j)
	public int dimension() {
		// board dimension N
		return board.length;
	}

	public int hamming() {
		int hamming = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0)
					continue;
				int res = (board[i][j] - getGoalValueForBlock(i, j));
				if (res != 0)
					hamming++;
			}
		}
		return hamming;
	}

	private int manhattan = -1;

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		if (manhattan == -1) {
			int manhattan = 0;
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (board[i][j] == 0)
						continue;
					int row = (board[i][j] - 1) / this.dimension();
					int col = (board[i][j] - 1) - (row * this.dimension());
					manhattan += Math.abs(i - row) + Math.abs(j - col);
				}
			}
			this.manhattan = manhattan;
		}
		return manhattan;
	}

	// is this board the goal board?
	public boolean isGoal() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				int res = (board[i][j] - getGoalValueForBlock(i, j));
				if (res != 0)
					return false;
			}
		}
		return true;
	}

	public Board twin() {
		// a boadr that is obtained by exchanging two adjacent blocks in the same row
		return this;
	}

	public boolean equals(Object y) {
		// does this board equal y?
		return toString().equals(y.toString());
	}

	public Iterable<Board> neighbors() {
		// all neighboring boards
		List<Board> boards = new ArrayList<Board>(2);

		int[] zeroposition = getPosition(0, board);
		// Fetch every neighbour to 0 and exchange positions with it.
		for (int[] indices : neighbourOffset) {
			int value = getValueAtPosition(zeroposition[0] + indices[0], zeroposition[1] + indices[1], board);
			if (value != INVALID_POSITION) {
				int[][] copy = copy(board);
				// swap 0 with its neighbor in copy
				copy[zeroposition[0] + indices[0]][zeroposition[1] + indices[1]] = 0;
				copy[zeroposition[0]][zeroposition[1]] = value;

				boards.add(new Board(copy));
			}
		}
		return boards;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(dimension() + " \n ");

		for (int row = 0; row < dimension(); row++) {
			for (int column = 0; column < dimension(); column++) {
				sb.append(board[row][column]);
				sb.append(" ");
			}

			sb.append("\n ");
		}

		return sb.toString();
	}

	private int[] positions = new int[2];

	private int[] getPosition(int value, int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] == value) {
					positions[0] = i;
					positions[1] = j;
					return positions;
				}
			}
		}
		positions[0] = INVALID_POSITION;
		positions[1] = INVALID_POSITION;
		return positions;
	}

	private int[][] copy(int[][] array) {
		int[][] copy = new int[array.length][array.length];

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				copy[i][j] = array[i][j];
			}
		}
		return copy;
	}

	private int getValueAtPosition(int x, int y, int[][] array) {
		if (x == INVALID_POSITION || y == INVALID_POSITION || (x > board.length - 1) || (y > board.length - 1)) {
			return INVALID_VALUE;
		}
		return array[x][y];
	}

	public static void main(String[] args) {
		// unit tests (not graded)
	}
}