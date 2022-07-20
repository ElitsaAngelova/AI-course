
public class Board {

	private char[][] board = new char[3][3];

	public Board() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = '_';
			}
		}
	}

	public Board(char[][] board) {
		this.board = board;
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
	
	public int movesLeftCount() {
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == '_') {
					count++;
				}
			}
		}
		return count;
	}

	public boolean isGameOver() {
		boolean isGameOver = false;
		for (int row = 0; row < 3; row++) {
			if (board[row][0] != '_' && board[row][0] == board[row][1] && board[row][0] == board[row][2]) {
				isGameOver = true;
			}
		}
		for (int col = 0; col < 3; col++) {
			if (board[0][col] != '_' && board[0][col] == board[1][col] && board[0][col] == board[2][col]) {
				isGameOver = true;
			}
		}
		if (board[0][0] != '_' && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
			isGameOver = true;
		}
		if (board[0][2] != '_' && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
			isGameOver = true;
		}
		if (movesLeftCount() == 0) {
			return true;
		}
		return isGameOver;
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
	 
}
