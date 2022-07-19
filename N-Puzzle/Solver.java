
public class Solver {

	public static int getHeuristic(int[][] board, int boardDim) {
		int manhattanSum = 0;
		for (int i = 0; i < boardDim; i++) {
			for (int j = 0; j < boardDim; j++) {
				if ((board[i][j] != GoalBoard.goalBoard[i][j]) && (board[i][j] != 0)) {
					manhattanSum += Math.abs(i - GoalBoard.getElementXCoord(board[i][j], boardDim)) +
							Math.abs(j - GoalBoard.getElementYCoord(board[i][j], boardDim));
				}
			}
		}
		return manhattanSum;
	}
	
	public static int Search(Board board, int g, int threshold) {
		
		int f = g + Solver.getHeuristic(board.getBoard(), board.getBoardDim());
		if (f > threshold) {
			return f;
		}
		if(GoalBoard.isGoal(board.getBoard(), board.getBoardDim())) {
			return -1;
		}
		int min = Integer.MAX_VALUE;
		
		for (Board nextState : board.generateAllChildren()) {
			if (nextState == null) {
				continue;
			}
			int temp = Search(nextState, g + 1, threshold);
			if (temp == -1) {
				Board.printPath(nextState);
				return -1;
			}
			if (temp < min) {
				min = temp;
			}
		}
		
		return min;
	}
	

	public static void IDAStar(Board startBoard) {
		int threshold = Solver.getHeuristic(startBoard.getBoard(), startBoard.getBoardDim());
		while(true) {
			int temp = Solver.Search(startBoard, 0, threshold);
			if (temp == -1) {
				return;
			}
			if (temp >= 100) {
				return;
			}
			threshold = temp;
		}
	}
}
