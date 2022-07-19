import java.util.Scanner;

public class Main {
public static void main(String[] args) {
	

		Scanner scan = new Scanner(System.in);
		
		int N = scan.nextInt();
		
		Board board = new Board(N);
		
		long start = System.currentTimeMillis();
		
		board.minConflictsAlgoritm();
		
		long end = System.currentTimeMillis();
		
		System.out.println(((double) (end - start)) / 1000 + " seconds");
		System.out.println();
		
		if (N < 1000) {
			board.printBoard();
		}
		
		

	}
}
