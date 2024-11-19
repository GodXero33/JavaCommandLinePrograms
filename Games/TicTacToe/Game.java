import java.util.Scanner;

class Game {
	final static ConsoleTool console = new ConsoleTool();
	final static Scanner input = new Scanner(System.in);
	final static TicTacToeBoard board = new TicTacToeBoard();

	public static void main (String[] args) {
		takeInitParams();
		updateScreen();
	}

	public static void takeInitParams () {
		console.clear();
		board.mode = getDifficulty();

		System.out.print("\n          Do you want to play first? (Y/N) : ");

		char response = input.nextLine().charAt(0);

		if (response != 'y' && response != 'Y') {
			board.next();
		}
	}

	public static int getDifficulty () {
		System.out.print("\n          Enter the difficulty (1 - Random Behaviour/2 - Hard): ");

		int mode = input.nextInt();
		input.nextLine();

		if (mode == 1 || mode == 2) return mode;

		return getDifficulty();
	}

	static void printHeader () {
		console.textColor(0, 250, 250);

		final String spacing = "                   ";

		System.out.println(spacing + " _______       ______           ______");
		System.out.println(spacing + "/_  __(_)___  /_  __/__ _____  /_  __/__  ___");
		System.out.println(spacing + " / / / / __/   / / / _ `/ __/   / / / _ \\/ -_)");
		System.out.println(spacing + "/_/ /_/\\__/   /_/  \\_,_/\\__/   /_/  \\___/\\__/ ");
		console.reset();
	}

	static void mainScreen () {
		console.clear();
		console.textColor(100, 100, 200);
		System.out.println("'Tic-Tac-Toe' from \"Sathish Shan\" :)\n\n");
		console.reset();
		printHeader();
		board.print(console, 25);
	}

	static void updateScreen () {
		mainScreen();

		console.textColor(100, 220, 255);
		System.out.print("           Enter the cell no (Your Symbol is 'X'. Enter -1 to End the game.): ");
		console.reset();

		int cell = input.nextInt();
		input.nextLine();

		if (cell == -1) {
			endPage();
			return;
		}

		if (cell < 1 || cell > 9) {
			updateScreen();
			return;
		}

		int changeState = board.update(cell, 0);

		if (changeState == 0) {
			changeState = board.next();
		}

		if (changeState == 1) {
			winState(1);
			return;
		}

		if (changeState == 2) {
			winState(2);
			return;
		}

		if (changeState == 3) {
			winState(0);
			return;
		}

		// changeState = -1. Do nothing. Give chance to the player gaian because the given cell is invalid or already marked.
		updateScreen();
	}

	public static void winState (int winner) {
		mainScreen();

		console.textColor(255, 180, 170);

		if (winner == 0) {
			System.out.println("           Game is draw!");
		} else {
			System.out.println("           " + (winner == 1 ? "You have" : "Computer has") + " won the game!");
		}

		console.reset();

		console.textColor(100, 220, 255);
		System.out.print("           Do you want to play again? (Y/N): ");
		console.reset();

		char response = input.nextLine().charAt(0);

		if (response == 'y' || response == 'Y') {
			board.flush();
			takeInitParams();
			updateScreen();
			return;
		}

		endPage();
	}

	public static void endPage () {
		console.clear();
		printHeader();
		console.textColor(255, 100, 200);

		final String spacing = "                               ";

		System.out.print("\n\n");
		System.out.println(spacing + "   __");
		System.out.println(spacing + "  / /  __ _____");
		System.out.println(spacing + " / _ \\/ // / -_)");
		System.out.println(spacing + "/_.__/\\_, /\\__/");
		System.out.println(spacing + "     /___/");
		System.out.print("\n\n\n\n");

		console.reset();
	}
}
