import java.lang.Math;
import java.util.Scanner;


public class Main {
	
	public static void main ( String[] args ) {
		
		Scanner scan = new Scanner(System.in);
		
		int N = scan.nextInt();
		
		int l = scan.nextInt();
		
		int boardDim = (int) Math.sqrt(N + 1);
		
		int[][] gameBoard = new int[boardDim][boardDim];
		
		for (int i = 0; i < boardDim; i++) {
			for (int j = 0; j < boardDim; j++) {
				gameBoard[i][j] = scan.nextInt();
			}
		}
 		
		GoalBoard.GenerateGoalBoard(N, boardDim, l);
		
		Board start = new Board(gameBoard, boardDim);
		
		Solver.IDAStar(start);
		
		System.out.print(Board.Steps.size());
		System.out.println();
		
		while(!Board.Steps.empty()) {
			System.out.print(Board.Steps.pop());
			System.out.println();
		}
			
	}
}
