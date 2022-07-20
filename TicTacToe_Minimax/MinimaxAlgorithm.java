import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MinimaxAlgorithm {
	
	static int whoGoFirst;
	static boolean computerOnTurn;
	
	public ScoreBoard minimax(Board board, int depth, int alpha, int beta, boolean isMax) {
		if (board.isGameOver()) {
			ScoreBoard b = new ScoreBoard(board, calculateScore(board) * depth);
			return b;
		}
		if (depth == 0) {
			ScoreBoard b = new ScoreBoard(board, calculateScore(board));
			return b;
		}
		
		if(isMax) {
			ScoreBoard maxBoard = null;
			int score = Integer.MIN_VALUE;
			
			for (Board i : generateChildren(board, isMax)) {
				ScoreBoard newBoard = minimax(i, depth - 1, alpha, beta, false);
				
				int actualScore = newBoard.getScore();
				if (actualScore > score) {
					score = actualScore;
					maxBoard = new ScoreBoard(i, actualScore);
				}
				
				alpha = Integer.max(alpha, score);
                if (alpha >= beta) {
                    break;
                }
			}
			
			return maxBoard;
		} else {
			ScoreBoard minBoard = null;
			int score = Integer.MAX_VALUE;
			
			for (Board i : generateChildren(board, isMax)) {
					
				ScoreBoard newBoard = minimax(i, depth - 1, alpha, beta, true);
				
				int actualScore = newBoard.getScore();
				if(actualScore < score) {
					score = actualScore;
					minBoard = new ScoreBoard(i, actualScore);
				}
				
				beta = Integer.min(beta, score);
                if (alpha >= beta) {
                    break;
                }
			}
			
			return minBoard;
		}
	}
	
	public Set<Board> generateChildren(Board board, boolean isMax) {
		Set<Board> children = new HashSet<>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board.getBoard()[i][j] == '_') {
					char[][] newBoard = new char[3][3];
					for (int row = 0; row < 3; row++) {
						for (int col = 0; col < 3; col++) {
							newBoard[row][col] = board.getBoard()[row][col];
						}
					}
					if(isMax) {
						newBoard[i][j] = 'x';
					} else {
						newBoard[i][j] = 'o';
					}
					children.add(new Board(newBoard));
					
				}
			}
		}
		return children;
	}
	
	public int calculateScore(Board board) { 
		// Rows
		for (int row = 0; row < 3; row++) {
			if (board.getBoard()[row][0] == 'x' && board.getBoard()[row][0] == board.getBoard()[row][1] &&
					board.getBoard()[row][1] == board.getBoard()[row][2]) {
				return 10;
			}
			if (board.getBoard()[row][0] == 'o' && board.getBoard()[row][0] == board.getBoard()[row][1] &&
					board.getBoard()[row][1] == board.getBoard()[row][2]) {
				return -10;
			}
		}
		// Columns
		for (int col = 0; col < 3; col++) {
			if (board.getBoard()[0][col] == 'x' && board.getBoard()[0][col] == board.getBoard()[1][col] &&
					board.getBoard()[1][col] == board.getBoard()[2][col]) {
				return 10;
			}
			if (board.getBoard()[0][col] == 'o' && board.getBoard()[0][col] == board.getBoard()[1][col] &&
					board.getBoard()[1][col] == board.getBoard()[2][col]) {
				return -10;
			}
		} 
		// Diagonal
		if (board.getBoard()[0][0] == 'x' && board.getBoard()[0][0] == board.getBoard()[1][1] &&
				board.getBoard()[1][1] == board.getBoard()[2][2]) {
			return 10;
		}
		if (board.getBoard()[0][0] == 'o' && board.getBoard()[0][0] == board.getBoard()[1][1] &&
				board.getBoard()[1][1] == board.getBoard()[2][2]) {
			return -10;
		} 
		// Alternative diagonal
		if (board.getBoard()[0][2] == 'x' && board.getBoard()[0][2] == board.getBoard()[1][1] &&
				board.getBoard()[1][1] == board.getBoard()[2][0]) {
			return 10;
		}
		if (board.getBoard()[0][2] == 'o' && board.getBoard()[0][2] == board.getBoard()[1][1] && 
				board.getBoard()[1][1] == board.getBoard()[2][0]) {
			return -10;
		}
		return 0;
	}
	
	public void whoWins(Board board) {
		if (calculateScore(board) > 0) {
			System.out.print("I win!");
			System.out.println();
		}
		if (calculateScore(board) < 0) {
			System.out.print("You win!");
			System.out.println();
		}
		if (calculateScore(board) == 0) {
			System.out.print("Draw!");
			System.out.println();
		}
		
	}
	
	
	public void run() {
		Scanner scan = new Scanner(System.in);
		if (MinimaxAlgorithm.whoGoFirst == 0) {
			computerOnTurn = false;
		} else if (MinimaxAlgorithm.whoGoFirst == 1) {
			computerOnTurn = true;
		}
		Board board = new Board();
		board.printBoard();

		while (!board.isGameOver()) {
			if (computerOnTurn) {
				ScoreBoard scoreBoard = minimax(board, 8, Integer.MIN_VALUE, Integer.MAX_VALUE, computerOnTurn);
				board = scoreBoard.getBoard();
				System.out.print("My turn. ");
				System.out.println();
				board.printBoard();

				if (board.isGameOver()) {
					whoWins(board);
				}

			} else {
				System.out.print("Your turn. ");
				System.out.println();
				System.out.print("Choose position: ");
				int x = scan.nextInt();
				int y = scan.nextInt();

				while (board.isValidMove(x, y) == false) {
					System.out.print("Not valid move!");
					System.out.println();
					System.out.print("Choose position: ");
					x = scan.nextInt();
					y = scan.nextInt();
				}

				board.MakeMove(x, y, 0);
				board.printBoard();

				if (board.isGameOver()) {
					whoWins(board);
				}

			}
			computerOnTurn = !computerOnTurn;

		}
		scan.close();
	}

}
