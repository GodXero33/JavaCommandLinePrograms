import java.util.Random;

class TicTacToeBoard {
	int[][] board = new int[3][3]; // 0 - Empty, 1 - X, 2 - O, 3 - X (Checked), 4 - O (Checked)
	int[] playerColor = new int[3];
	int[] programColor = new int[3];
	int[] offColor = new int[3];
	String[] symbols = {
		"   _  __",
		"  | |/_/",
		" _>  <  ",
		"/_/|_|  ",
		"  ____  ",
		" / __ \\ ",
		"/ /_/ / ",
		"\\____/  "
	};
	int mode = 1;
	Random rand;

	TicTacToeBoard () {
		playerColor[0] = 255;

		programColor[1] = 255;
		programColor[2] = 255;

		offColor[0] = 100;
		offColor[1] = 100;
		offColor[2] = 100;

		rand = new Random();
	}

	private String getSymbol (int line) {
		return this.symbols[line];
	}

	public void print (ConsoleTool console, int spacing) {
		String[] output = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };

		final String spacingStr = " ".repeat(spacing);

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				final int cell = this.board[y][x];

				for (int i = 0; i < 4; i++) {
					int line = 0;
					String color = "";

					if (cell != 0 && (cell == 2 || cell == 4)) {
						line = 4;
					}

					if (cell == 1) {
						color = console.getTextColor(this.playerColor[0], this.playerColor[1], this.playerColor[2]);
					} else if (cell == 2) {
						color = console.getTextColor(this.programColor[0], this.programColor[1], this.programColor[2]);
					} else if (cell == 3 || cell == 4) {
						color = console.getBgColor(this.offColor[0], this.offColor[1], this.offColor[2]);
					}

					String outputStr = color + (cell == 0 ? "        " : this.getSymbol(i + line)) + console.getReset();

					if (x != 2) outputStr += " | ";
					if (x == 0) outputStr = spacingStr + outputStr;

					output[y * 5 + i] += outputStr;
				}
			}

			if (y != 0) output[y * 5 - 1] = spacingStr + "------------------------------";
		}

		final int len = output.length;
		output[len - 1] = spacingStr + "         |          |";

		System.out.print("\n\n\n\n");
		
		for (int i = 0; i < len; i++) {
			System.out.println(output[i]);
		}

		System.out.print("\n\n" + spacingStr + "       - - - - - - - - -\n\n");

		System.out.println(spacingStr + "        1  |  2  |  3  ");
		System.out.println(spacingStr + "       ---------------");
		System.out.println(spacingStr + "        4  |  5  |  6  ");
		System.out.println(spacingStr + "       ---------------");
		System.out.println(spacingStr + "        7  |  8  |  9  \n\n\n");
	}

	public int checkForWin () {
		for (int y = 0; y < 3; y++) {
			boolean isRow = true;
			int val = this.board[y][0];

			if (val == 0) continue;

			for (int x = 1; x < 3; x++) {
				if (this.board[y][x] != val) {
					isRow = false;
					break;
				}
			}

			if (isRow) {
				for (int x = 0; x < 3; x++) {
					this.board[y][x] = val == 1 ? 3 : 4;
				}

				return val;
			}
		}

		for (int x = 0; x < 3; x++) {
			boolean isRow = true;
			int val = this.board[0][x];

			if (val == 0) continue;

			for (int y = 1; y < 3; y++) {
				if (this.board[y][x] != val) {
					isRow = false;
					break;
				}
			}

			if (isRow) {
				for (int y = 0; y < 3; y++) {
					this.board[y][x] = val == 1 ? 3 : 4;
				}

				return val;
			}
		}

		int diagonalVal = this.board[0][0];

		if (diagonalVal != 0) {
			boolean isDiagonal = true;

			for (int i = 1; i < 3; i++) {
				if (this.board[i][i] != diagonalVal) {
					isDiagonal = false;
					break;
				}
			}

			if (isDiagonal) {
				for (int i = 0; i < 3; i++) {
					this.board[i][i] = diagonalVal == 1 ? 3 : 4;
				}
				
				return diagonalVal;
			}
		}

		diagonalVal = this.board[0][2];

		if (diagonalVal != 0) {
			boolean isDiagonal = true;
			
			for (int i = 1; i < 3; i++) {
				if (this.board[i][2 - i] != diagonalVal) {
					isDiagonal = false;
					break;
				}
			}

			if (isDiagonal) {
				for (int i = 0; i < 3; i++) {
					this.board[i][2 - i] = diagonalVal == 1 ? 3 : 4;
				}
				
				return diagonalVal;
			}
		}

		boolean isFill = true;

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (this.board[y][x] == 0) {
					isFill = false;
					break;
				}
			}

			if (!isFill) break;
		}

		return isFill ? 3 : 0;
	}

	public int update (int cell, int player) {
		cell--;
		int y = cell / 3;
		int x = cell % 3;

		if (x < 0 || x > 2 || y < 0 || y > 2 || this.board[y][x] != 0) return -1;

		this.board[y][x] = player + 1;
		return this.checkForWin();
	}

	public void flush () {
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				this.board[y][x] = 0;
			}
		}
	}

	private int[] getEmptyCellsCleaned (int[] arr) {
		final int n = arr.length;
		int count = 0;

		for (int i = 0; i < n; i++) {
			if (arr[i] == 1) count++;
		}

		int[] returnArr = new int[count];
		int m = 0;

		for (int i = 0; i < n; i++) {
			if (arr[i] == 1) {
				returnArr[m] = i;
				m++;
			}
		}

		return returnArr;
	}

	private int[] getEmptyCells () {
		int[] emptyCells = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (this.board[y][x] == 0) {
					emptyCells[y * 3 + x] = 1;
				}
			}
		}
		
		return this.getEmptyCellsCleaned(emptyCells);
	}

	private int getRandomCell () {
		int[] emptyCells = this.getEmptyCells();
		final int n = emptyCells.length;
		int cell = emptyCells[this.rand.nextInt(n)];
		return cell + 1;
	}

	public int nextByRandom () {
		return this.update(this.getRandomCell(), 1);
	}

	private int[] getCleanedArray (int[] arr, int symbol) {
		final int n = arr.length;
		int count = 0;

		for (int i = 0; i < n; i++) {
			if (arr[i] != symbol) count++;
		}

		if (count == 0) return new int[0];

		int[] returnArr = new int[count];
		int m = 0;

		for (int i = 0; i < n; i++) {
			if (arr[i] != symbol) {
				returnArr[m] = arr[i];
				m++;
			}
		}

		return returnArr;
	}

	private int getMatchingCellsHorizontal (int match) {
		int[] blockCells = { -1, -1, -1 };
		int counter = 0;

		for (int y = 0; y < 3; y++) {
			int prev = this.board[y][0];
			int mid = this.board[y][1];
			int next = this.board[y][2];

			if (prev == 0 && mid == match && next == match) {
				blockCells[counter] = y * 3;
				counter++;
				break;
			}

			if (prev == match && mid == 0 && next == match) {
				blockCells[counter] = y * 3 + 1;
				counter++;
				break;
			}

			if (prev == match && mid == match && next == 0) {
				blockCells[counter] = y * 3 + 2;
				counter++;
				break;
			}
		}

		int[] cleanedBlockCells = this.getCleanedArray(blockCells, -1);
		int blockCell = -1;

		if (cleanedBlockCells.length != 0) {
			blockCell = cleanedBlockCells[this.rand.nextInt(cleanedBlockCells.length)];
		}

		return blockCell;
	}

	private int getMatchingCellsVerticle (int match) {
		int[] blockCells = { -1, -1, -1 };
		int counter = 0;

		for (int x = 0; x < 3; x++) {
			int prev = this.board[0][x];
			int mid = this.board[1][x];
			int next = this.board[2][x];

			if (prev == 0 && mid == match && next == match) {
				blockCells[counter] = x;
				counter++;
				break;
			}

			if (prev == match && mid == 0 && next == match) {
				blockCells[counter] = 3 + x;
				counter++;
				break;
			}

			if (prev == match && mid == match && next == 0) {
				blockCells[counter] = 6 + x;
				counter++;
				break;
			}
		}

		int[] cleanedBlockCells = this.getCleanedArray(blockCells, -1);
		int blockCell = -1;

		if (cleanedBlockCells.length != 0) {
			blockCell = cleanedBlockCells[this.rand.nextInt(cleanedBlockCells.length)];
		}

		return blockCell;
	}

	private int getMatchingCellsDiagonal (int match) {
		int[] blockCells = { -1, -1 };
		int counter = 0;

		{
			int prev = this.board[0][0];
			int mid = this.board[1][1];
			int next = this.board[2][2];

			if (prev == 0 && mid == match && next == match) {
				blockCells[counter] = 0;
				counter++;
			} else if (prev == match && mid == 0 && next == match) {
				blockCells[counter] = 4;
				counter++;
			} else if (prev == match && mid == match && next == 0) {
				blockCells[counter] = 8;
				counter++;
			}
		}

		{
			int prev = this.board[0][2];
			int mid = this.board[1][1];
			int next = this.board[2][0];

			if (prev == 0 && mid == match && next == match) {
				blockCells[counter] = 2;
				counter++;
			} else if (prev == match && mid == 0 && next == match) {
				blockCells[counter] = 4;
				counter++;
			} else if (prev == match && mid == match && next == 0) {
				blockCells[counter] = 8;
				counter++;
			}
		}

		int[] cleanedBlockCells = this.getCleanedArray(blockCells, -1);
		int blockCell = -1;

		if (cleanedBlockCells.length != 0) {
			blockCell = cleanedBlockCells[this.rand.nextInt(cleanedBlockCells.length)];
		}

		return blockCell;
	}

	public int nextByAI () {
		int placeCellHorizontal = this.getMatchingCellsHorizontal(2) + 1;
		int placeCellVerticle = this.getMatchingCellsVerticle(2) + 1;
		int placeCellDiagonal = this.getMatchingCellsDiagonal(2) + 1;

		if (placeCellHorizontal != 0) return this.update(placeCellHorizontal, 1);
		if (placeCellVerticle != 0) return this.update(placeCellVerticle, 1);
		if (placeCellDiagonal != 0) return this.update(placeCellDiagonal, 1);

		int blockCellHorizontal = this.getMatchingCellsHorizontal(1) + 1;
		int blockCellVerticle = this.getMatchingCellsVerticle(1) + 1;
		int blockCellDiagonal = this.getMatchingCellsDiagonal(1) + 1;

		if (blockCellHorizontal != 0) return this.update(blockCellHorizontal, 1);
		if (blockCellVerticle != 0) return this.update(blockCellVerticle, 1);
		if (blockCellDiagonal != 0) return this.update(blockCellDiagonal, 1);
		
		return this.update(this.getRandomCell(), 1);
	}

	public int next () {
		if (this.mode == 1) {
			return this.nextByRandom();
		} else {
			return this.nextByAI();
		}
	}
}
