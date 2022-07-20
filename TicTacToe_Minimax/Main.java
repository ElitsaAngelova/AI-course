import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("Do you want to start? yes/no ");
		Scanner scan = new Scanner(System.in);
		String choose = scan.nextLine();
		if (choose.equals("yes")) {
			MinimaxAlgorithm.whoGoFirst = 0;
		} else if (choose.equals("no")) {
			MinimaxAlgorithm.whoGoFirst = 1;
		}
		if (MinimaxAlgorithm.whoGoFirst == 0) {
			MinimaxAlgorithm.computerOnTurn = false;
		} else if (MinimaxAlgorithm.whoGoFirst == 1) {
			MinimaxAlgorithm.computerOnTurn = true;
		}
		MinimaxAlgorithm M = new MinimaxAlgorithm();
		M.run();
		
		scan.close();

	}

}
