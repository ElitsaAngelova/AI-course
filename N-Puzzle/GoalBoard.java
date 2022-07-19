public class GoalBoard {
	
	static int[][] goalBoard;
	static int zeroX;
	static int zeroY;
	
	public static void GenerateGoalBoard(int N, int boardDim, int l) {
		goalBoard = new int[boardDim][boardDim];
		int number = 1;
		int count = 0; 
		for (int i = 0; i < boardDim; i++) {
			for (int j = 0; j < boardDim; j++) {
				if (count == l) {
					goalBoard[i][j] = 0;
					zeroX = i;
					zeroY = j;
					count++;
				}
				else {
					goalBoard[i][j] = number;
					number++;
					count++;
				}
			}
		}
		if (l == -1) {
			goalBoard[boardDim - 1][boardDim - 1] = 0;
			zeroX = zeroY = boardDim - 1;
		}
	}
	
	public static int getElementXCoord(int element, int boardDim) {
		for (int i = 0; i < boardDim; i++) {
			for (int j = 0; j < boardDim; j++) {
				if (GoalBoard.goalBoard[i][j] == element) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public static int getElementYCoord(int element, int boardDim) {
		for (int i = 0; i < boardDim; i++) {
			for (int j = 0; j < boardDim; j++) {
				if (GoalBoard.goalBoard[i][j] == element) {
					return j;
				}
			}
		}
		return -1;
	}
	
	public static boolean isGoal(int[][] board, int boardDim) {
		for (int i = 0; i < boardDim; i++) {
			for (int j = 0; j < boardDim; j++) {
				if (board[i][j] != goalBoard[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
}