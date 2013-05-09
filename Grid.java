public class Grid {

	public static final int PLAY = 0;
	public static final int OVER = 1;
	public static final int WIN = 2;
	public static final int LOSS = 3;

	public static final int MINE = -1;
	public static final int SHOWN = -2;
	public static final int HIDDEN = -3;
	public static final int QUESTION = -4;
	public static final int DANGER = -5;

	protected int status;
	protected int[][] grid;
	protected int[][] markedGrid;
	protected int mines;

	public Grid(int rows, int columns, int mines) {
		this.grid = new int[rows][columns];
		this.markedGrid = new int[rows][columns];
		this.mines = mines;

		this.status = PLAY;
		initGrid();
	}

	private void initGrid() { int rows = rows(), cols = columns();
		int cells = rows * cols;
		int i;

		int[] pos = new int[cells];

		// Init Coordinate Sample
		for (i = 0; i < cells; i++) {
			pos[i] = i;
		}

		// Place Mines
		for (i = 0; i < mines; i++) {
			int rand = (int)(Math.random() * (cells - i));
			int swap = cells - i - 1;
			int row = rand / cols;
			int col = rand % cols;

			grid[row][col] = MINE;

			// Swap Positions
			pos[rand] = pos[rand] ^ pos[swap];
			pos[swap] = pos[rand] ^ pos[swap];
			pos[rand] = pos[rand] ^ pos[swap];
		}

		// Go back to mines and add up surroudning cells
		for (i = 0; i < mines; i++) {
			int position = pos[cells - i - 1];
			int row = position / cols;
			int col = position % cols;

			for (int r = -1; r <= 1; r++) {
				for (int c = -1; c <= 1; c++) {
					if (valid(row + r, col + c) && grid[row + r][col + c] != MINE) {
						grid[row + r][col + c]++;
					}
				}
			}
		}
	}

	public boolean valid(int r, int c) {
		return r >= 0 && r < rows() && c >= 0 && c < columns();
	}

	public int rows() {
		return grid.length;
	}

	public int columns() {
		return grid[0].length;
	}

	public int mines() {
		return mines;
	}

	public void click(int r, int c) {
		if (!valid(r, c) || markedGrid[r][c] == SHOWN) {
			return;
		} else if (grid[r][c] == 0) {
			markedGrid[r][c] = SHOWN;
			click(r + 1, c);
			click(r - 1, c);
			click(r, c + 1);
			click(r, c - 1);
		} else if (grid[r][c] == MINE) {
			status = LOSS;
			
			// Reveal Mines
			int rows = rows(), cols = columns();
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (grid[i][j] == MINE) {
						markedGrid[i][j] = SHOWN;
					}
				}
			}
		} else {
			markedGrid[r][c] = SHOWN;
		}
	}

	public void mark(int r, int c) {
		if (markedGrid[r][c] == SHOWN) {
			return;
		} else if (markedGrid[r][c] == HIDDEN) {
			markedGrid[r][c] = DANGER;
		} else {
			markedGrid[r][c]++;
		}
	}

	public boolean inPlay() {
		return status == PLAY;
	}

	public boolean isOver() {
		return status == WIN || status == LOSS;
	}

	public boolean isWon() {
		return status == WIN;
	}

	public boolean isLost() {
		return status == LOSS;
	}

	public String toString() {
		StringBuilder out = new StringBuilder();
		int rows = rows(), cols = columns();

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (grid[r][c] == MINE) {
					out.append(" * ");
				} else {
					out.append(" " + grid[r][c] + " ");
				}
			}
			out.append("\n");
		}

		return out.toString();
	}	

	public static void main(String args[]) {
		System.out.println(new Grid(10, 10, 15));
	}
}
