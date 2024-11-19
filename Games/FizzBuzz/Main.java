import java.util.Scanner;

class Main {
	public static void main (String[] args) {
		Scanner input = new Scanner(System.in);

		while (true) {
			System.out.print("Enter a number between 1 to 100: ");
			int number = input.nextInt();
			input.nextLine();
			fizzBuzz(number);
		}
	}

	public static void fizzBuzz (int num) {
		if (num < 1 || num > 100) {
			System.out.println("The number is not in 1 - 100.");
			return;
		}

		if (num % 3 == 0 && num % 5 == 0) {
			System.out.println("FizzBuzz");
			return;
		}

		if (num % 3 == 0) {
			System.out.println("Fizz");
			return;
		}

		if (num % 5 == 0) {
			System.out.println("Buzz");
			return;
		}
	}
}
