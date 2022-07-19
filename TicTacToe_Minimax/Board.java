
import java.util.LinkedHashSet;
import java.util.Set;

public class Board {

	char[][] board = new char[3][3];
	int score;

	public Board() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = '_';
			}
		}
		score = calculateScore(10);
	}

	public Board(char[][] board) {
		this.board = board;
	}
	
	public Board(char[][] board, int score) {
		this.board = board;
		this.score = score;
	}

	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}
	
	public boolean isValidMove(int x, int y) {
		if ((x > 3) || (x < 0) || (y > 3) || (y < 0)) {
			return false;
		}
		if (board[x][y] != '_') {
			return false;
		}
		return true;
	}

	public void MakeMove(int x, int y, int whoIsOnTurn) {
		if (whoIsOnTurn == 0) {
			board[x][y] = 'o';
		}
		if (whoIsOnTurn == 1) {
			board[x][y] = 'x';
		}
	}

	public boolean isMovesLeft() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == '_') {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isGameOver() {
		for (int row = 0; row < 3; row++) {
			if (board[row][0] != '_' && board[row][0] == board[row][1] && board[row][0] == board[row][2]) {
				return true;
			}
		}
		for (int col = 0; col < 3; col++) {
			if (board[0][col] != '_' && board[0][col] == board[1][col] && board[0][col] == board[2][col]) {
				return true;
			}
		}
		if (board[0][0] != '_' && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
			return true;
		}
		if (board[0][2] != '_' && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
			return true;
		}
		if (isMovesLeft() == false) {
			return true;
		}
		return false;
	}

	public void printBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(" " + board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public Set<Board> generateChildren(boolean isMax) {
		Set<Board> children = new LinkedHashSet<>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == '_') {
					char[][] newBoard = new char[3][3];
					for (int row = 0; row < 3; row++) {
						for (int col = 0; col < 3; col++) {
							newBoard[row][col] = board[row][col];
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

	
	public int calculateScore(int depth) { 
		// Rows
		for (int row = 0; row < 3; row++) {
			if (board[row][0] == 'x' && board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
				return 1 * depth;
			}
			if (board[row][0] == 'o' && board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
				return -1 * depth;
			}
		}
		// Columns
		for ( int col = 0; col < 3; col++) {
			if (board[0][col] == 'x' && board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
				return 1 * depth;
			}
			if (board[0][col] == 'o' && board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
				return -1 * depth;
			}
		} 
		// Diagonal
		if (board[0][0] == 'x' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			return 1 * depth;
		}
		if (board[0][0] == 'o' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			return -1 * depth;
		} 
		// Alternative diagonal
		if (board[0][2] == 'x' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
			return 1 * depth;
		}
		if (board[0][2] == 'o' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
			return -1 * depth;
		}
		return 0;
	}
	 
	
	
	/*
	 * public int calculateScore() { for (int row = 0; row < 3; row++) { if
	 * (board[row][0] == 'x' && board[row][0] == board[row][1] && board[row][1] ==
	 * board[row][2]) { return 100; } if (board[row][0] == 'o' && board[row][0] ==
	 * board[row][1] && board[row][1] == board[row][2]) { return -100; } } for (int
	 * col = 0; col < 3; col++) { if (board[0][col] == 'x' && board[0][col] ==
	 * board[1][col] && board[1][col] == board[2][col]) { return 100; } if
	 * (board[0][col] == 'o' && board[0][col] == board[1][col] && board[1][col] ==
	 * board[2][col]) { return -100; } } if (board[0][0] == 'x' && board[0][0] ==
	 * board[1][1] && board[1][1] == board[2][2]) { return 100; } if (board[0][0] ==
	 * 'o' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) { return
	 * -100; } if (board[0][2] == 'x' && board[0][2] == board[1][1] && board[1][1]
	 * == board[2][0]) { return 100; } if (board[0][2] == 'o' && board[0][2] ==
	 * board[1][1] && board[1][1] == board[2][0]) { return -100; } for (int row = 0;
	 * row < 3; row++) { if (((board[row][0] == 'x' && board[row][0] ==
	 * board[row][1]) && board[row][2] == '_') || ((board[row][0] == 'x' &&
	 * board[row][0] == board[row][2]) && board[row][1] == '_') || ((board[row][1]
	 * == 'x' && board[row][1] == board[row][2]) && board[row][0] == '_')) { return
	 * 10; } if (((board[row][0] == 'o' && board[row][0] == board[row][1]) &&
	 * board[row][2] == '_') || ((board[row][0] == 'o' && board[row][0] ==
	 * board[row][2]) && board[row][1] == '_') || ((board[row][1] == 'o' &&
	 * board[row][1] == board[row][2]) && board[row][0] == '_')) { return -10; } }
	 * for (int col = 0; col < 3; col++) { if (((board[0][col] == 'x' &&
	 * board[0][col] == board[1][col]) && board[2][col] == '_') || ((board[0][col]
	 * == 'x' && board[0][col] == board[2][col]) && board[1][col] == '_') || ((
	 * board[1][col] == 'x' && board[1][col] == board[2][col]) && board[0][col] ==
	 * '_')) { return 10; } if (((board[0][col] == 'o' && board[0][col] ==
	 * board[1][col]) && board[2][col] == '_') || ((board[0][col] == 'o' &&
	 * board[0][col] == board[2][col]) && board[1][col] == '_') || ((board[1][col]
	 * == 'o' && board[1][col] == board[2][col]) && board[0][col] == '_')) { return
	 * -10; } } if (((board[0][0] == 'x' && board[0][0] == board[1][1]) &&
	 * board[2][2] == '_') || ((board[0][0] == 'x' && board[0][0] == board[2][2]) &&
	 * board[1][1] == '_') || ((board[1][1] == 'x' && board[1][1] == board[2][2]) &&
	 * board[0][0] == '_')) { return 10; } if (((board[0][0] == 'o' && board[0][0]
	 * == board[1][1]) && board[2][2] == '_') || ((board[0][0] == 'o' && board[0][0]
	 * == board[2][2]) && board[1][1] == '_') || ((board[1][1] == 'o' && board[1][1]
	 * == board[2][2]) && board[0][0] == '_')) { return -10; } if (((board[0][2] ==
	 * 'x' && board[0][2] == board[1][1]) && board[2][0] == '_') || ((board[0][2] ==
	 * 'x' && board[0][2] == board[2][0]) && board[1][1] == '_') || ((board[1][1] ==
	 * 'x' && board[1][1] == board[2][0]) && board[0][2] == '_')) { return 10; } if
	 * (((board[0][2] == 'o' && board[0][2] == board[1][1]) && board[2][0] == '_')
	 * || ((board[0][2] == 'o' && board[0][2] == board[2][0]) && board[1][1] == '_')
	 * || ((board[1][1] == 'o' && board[1][1] == board[2][0]) && board[0][2] ==
	 * '_')) { return -10; } for (int row = 0; row < 3; row++) { if ((board[row][0]
	 * == 'x' && board[row][1] == '_' && board[row][2] == '_') || (board[row][1] ==
	 * 'x' && board[row][0] == '_' && board[row][2] == '_') || (board[row][2] == 'x'
	 * && board[row][1] == '_' && board[row][0] == '_')) { return 1; } if
	 * ((board[row][0] == 'o' && board[row][1] == '_' && board[row][2] == '_') ||
	 * (board[row][1] == 'o' && board[row][0] == '_' && board[row][2] == '_') ||
	 * (board[row][2] == 'o' && board[row][1] == '_' && board[row][0] == '_')) {
	 * return -1; } } for (int col = 0; col < 3; col++) { if ((board[0][col] == 'x'
	 * && board[1][col] == '_' && board[2][col] == '_') || (board[1][col] == 'x' &&
	 * board[0][col] == '_' && board[2][col] == '_') || (board[2][col] == 'x' &&
	 * board[1][col] == '_' && board[0][col] == '_')) { return 1; } if
	 * ((board[0][col] == 'o' && board[1][col] == '_' && board[2][col] == '_') ||
	 * (board[1][col] == 'o' && board[0][col] == '_' && board[2][col] == '_') ||
	 * (board[2][col] == 'o' && board[1][col] == '_' && board[0][col] == '_')) {
	 * return -1; } } if ((board[0][0] == 'x' && board[1][1] == '_' && board[2][2]
	 * == '_') || (board[1][1] == 'x' && board[0][0] == '_' && board[2][2] == '_')
	 * || (board[2][2] == 'x' && board[1][1] == '_' && board[0][0] == '_')) { return
	 * 1; } if ((board[0][0] == 'o' && board[1][1] == '_' && board[2][2] == '_') ||
	 * (board[1][1] == 'o' && board[0][0] == '_' && board[2][2] == '_') ||
	 * (board[2][2] == 'o' && board[1][1] == '_' && board[0][0] == '_')) { return
	 * -1; } if ((board[0][2] == 'x' && board[1][1] == '_' && board[2][0] == '_') ||
	 * (board[1][1] == 'x' && board[0][2] == '_' && board[2][0] == '_') ||
	 * (board[2][0] == 'x' && board[1][1] == '_' && board[0][2] == '_')) { return 1;
	 * } if ((board[0][2] == 'o' && board[1][1] == '_' && board[2][0] == '_') ||
	 * (board[1][1] == 'o' && board[0][2] == '_' && board[2][0] == '_') ||
	 * (board[2][0] == 'o' && board[1][1] == '_' && board[0][2] == '_')) { return
	 * -1; } return 0; }
	 */
	 
	
	public void whoWins(int depth) {
		if (this.calculateScore(depth) > 0) {
			System.out.print("I win!");
			System.out.println();
		}
		if (this.calculateScore(depth) < 0) {
			System.out.print("You win!");
			System.out.println();
		}
		if (this.calculateScore(depth) == 0) {
			System.out.print("Draw!");
			System.out.println();
		}
		
	}
}
