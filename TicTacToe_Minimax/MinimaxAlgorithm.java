import java.util.Random;
import java.util.Scanner;

public class MinimaxAlgorithm {
	
	final static int whoGoFirst = WhoGoFirst();
	static boolean computerOnTurn;
	
	public static int WhoGoFirst() {
		Random randomNumber = new Random();
		return randomNumber.nextInt(2);
	}
	
	public static Board minimax(Board board, int depth, int alpha, int beta, boolean isMax) {
		if (depth == 0 || board.isGameOver()) {
			Board b = new Board(board.getBoard());
			return b;
		}
		
		if(isMax) {
			Board maxBoard = new Board();
			int score = Integer.MIN_VALUE;
			
			for (Board i : board.generateChildren(isMax)) {
				Board newBoard = new Board();
				newBoard = minimax(i, depth - 1, alpha, beta, false);
				System.out.print(depth);
				System.out.println();
				if(newBoard.calculateScore(depth) > score) {
					score = newBoard.calculateScore(depth);
					maxBoard = i;
				}
				
				alpha = Integer.max(alpha, newBoard.calculateScore(depth));
                if (alpha >= beta) {
                    break;
                }
			}
			return maxBoard;
		} else {
			Board minBoard = new Board();
			int score = Integer.MAX_VALUE;
			
			for (Board i : board.generateChildren(isMax)) {
				Board newBoard = new Board();
					
				newBoard = minimax(i, depth - 1, alpha, beta, true);
				System.out.print(depth);
				System.out.println();
				if(newBoard.calculateScore(depth) < score) {
					score = newBoard.calculateScore(depth);
					minBoard = i;
				}
				
				beta = Integer.min(beta, newBoard.calculateScore(depth));
                if (alpha >= beta) {
                    break;
                }
			}
			return minBoard;
		}
	}
	
	public static void run() {
		Scanner scan = new Scanner(System.in);
		if (MinimaxAlgorithm.whoGoFirst == 0) {
			computerOnTurn = false;
		} else if (MinimaxAlgorithm.whoGoFirst == 1) {
			computerOnTurn = true;
		}
		Board board = new Board();
		board.printBoard();
		
		while(!board.isGameOver()) {
			if(computerOnTurn) {
				board = minimax(board, 10, Integer.MIN_VALUE, Integer.MAX_VALUE, computerOnTurn);
				System.out.print("My turn. ");
				System.out.println();
				board.printBoard();
				/*
				 * System.out.print(board.calculateScore(depth)); System.out.println();
				 * if(board.isGameOver()) { board.whoWins(depth); }
				 */
			} else {
				System.out.print("Your turn. ");
				System.out.println();
				System.out.print("Choose position: ");
				int x = scan.nextInt();
				int y = scan.nextInt();
				
				while(board.isValidMove(x, y) == false) {
					System.out.print("Not valid move!");
					System.out.println();
					System.out.print("Choose position: ");
					x = scan.nextInt();
					y = scan.nextInt();
				}
				
				board.MakeMove(x, y, 0);
				board.printBoard();
				/*
				 * System.out.print(board.calculateScore(depth)); System.out.println();
				 * if(board.isGameOver()) { board.whoWins(depth); }
				 */
				
			}
			computerOnTurn = !computerOnTurn;
			
		}
		scan.close();
	}
}
