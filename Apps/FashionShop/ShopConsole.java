import java.util.Scanner;

class ShopConsole {
	final private Scanner input = new Scanner(System.in);
	final private String textYellow = "\u001B[33m";
	final private String textReset = "\u001B[0m";

	public void clearAll () {
		try {
			final String os = System.getProperty("os.name");

			if (os.contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void clearLine (int lineCount) {
		System.out.printf("\033[%dA\033[0J", lineCount);
		System.out.flush();
	}

	private String getInputString () {
		System.out.print(this.textYellow);
		final String str = input.nextLine();
		System.out.print(this.textReset);

		return str;
	}

	public int getInteger (String message) {
		while (true) {
			System.out.print(message);
			final String str = this.getInputString();

			if (str.matches("^(\\d+)$")) return Integer.parseInt(str);

			this.clearLine(1);
		}
	}

	public double getDouble (String message) {
		while (true) {
			System.out.print(message);
			final String str = this.getInputString();

			if (str.matches("^(\\d+)(\\.\\d+)?$")) return Double.parseDouble(str);

			this.clearLine(1);
		}
	}

	public String getString (String message) {
		System.out.print(message);
		return this.getInputString();
	}

	public boolean getUserResponse (String message) {
		while (true) {
			System.out.print(message);
			final String str = this.getInputString();

			if (str.length() != 1) {
				this.clearLine(1);
				continue;
			}

			final char firstCh = str.charAt(0);

			if (firstCh == 'y' || firstCh == 'Y') return true;
			if (firstCh == 'n' || firstCh == 'N') return false;

			this.clearLine(1);
		}
	}

	public String getMatchedString (String message, String regex) {
		while (true) {
			System.out.print(message);
			final String str = this.getInputString();

			if (str.matches(regex)) return str;

			this.clearLine(1);
		}
	}
}
