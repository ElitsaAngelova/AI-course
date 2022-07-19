import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
	
	private int boardDim;
	private int[] queens;
	private int[] rows;
	private int[] diagonal1;
	private int[] diagonal2;
	private List<Integer> worstQueen = new ArrayList<Integer>();
	private List<Integer> bestPlace = new ArrayList<Integer>();
	
	Board(int N) {
		this.setBoardDim(N);

		this.queens = new int[this.boardDim];

		for (int i = 0; i < this.boardDim; i++) {
			this.queens[i] = i;
		}
		for (int i = 0; i < this.boardDim; i++) {
			Random randomNumber = new Random();
			int j = randomNumber.nextInt(this.boardDim);
			int temp = this.queens[i];
			this.queens[i] = this.queens[j];
			this.queens[j] = temp;
		}

		this.getQueens();
	}
	
	public int getBoardDim() {
		return boardDim;
	}

	public void setBoardDim(int boardDim) {
		this.boardDim = boardDim;
	}
	
	public void getQueens() {
		rows = new int[boardDim];
		diagonal1 = new int[2 * boardDim - 1];
		diagonal2 = new int[2 * boardDim - 1];

		for (int i = 0; i < boardDim; i++) {
			rows[queens[i]]++;
			diagonal1[i - queens[i] + boardDim - 1]++;
			diagonal2[queens[i] + i]++;
		}
	}
	
	public int countQueenConflicts(int row, int col) {
		int count = 0;
		count = rows[row] + diagonal1[col - row + boardDim - 1] + diagonal2[row + col] - 3;
		return count;
	}

	public int countCellConflicts(int row, int col) {
		int count = 0;
		count = rows[row] + diagonal1[col - row + boardDim - 1] + diagonal2[row + col];
		return count;
	}
	
	public int getColWithMaxCoflicts() {
		int ColWithMaxConflict;
		int maxConflict = 0;
		int numberOfConflicts = 0;
		
		worstQueen.clear();
		
		for (int i = 0; i < boardDim; i++) {
			
			numberOfConflicts = countQueenConflicts(queens[i], i);
			
			if (numberOfConflicts == maxConflict) {
				worstQueen.add(i);
			} else if (numberOfConflicts > maxConflict) {
				worstQueen.clear();
				maxConflict = numberOfConflicts;
				worstQueen.add(i);
			}
		}
		
		if (maxConflict == 0) {
			return -1;
		}
		
		Random randomNumber = new Random(); 
		int index = randomNumber.nextInt(worstQueen.size()); 
		ColWithMaxConflict = worstQueen.get(index);
		
		return ColWithMaxConflict;
	}
	
	public int getRowWithMinConflict(int col) {
		int RowWithMinConflict; 
		int minConflict = boardDim;
		int numberOfConflicts = 0;
		
		bestPlace.clear();
		
		for (int i = 0; i < boardDim; i++) {

			if (i == queens[col]) {
				continue;
			}

			numberOfConflicts = countCellConflicts(i, col);

			if (numberOfConflicts == minConflict) {
				bestPlace.add(i);
			} else if (numberOfConflicts < minConflict) {
				bestPlace.clear();
				minConflict = numberOfConflicts;
				bestPlace.add(i);
			}
		}
		
		 Random randomNumber = new Random(); 
		 int index = randomNumber.nextInt(bestPlace.size()); 
		 RowWithMinConflict = bestPlace.get(index);
		  
		 return RowWithMinConflict;
	}
	
	public void minConflictsAlgoritm() {
		final int MAX_ITER = boardDim * 3;

		while (true) {
			for (int iteration = 0; iteration < MAX_ITER; iteration++) {

				int ColWithMaxConflict = this.getColWithMaxCoflicts();

				if (ColWithMaxConflict == -1) {
					System.out.print("Solved in ");
					return;
				}

				int RowWithMinConflict = this.getRowWithMinConflict(ColWithMaxConflict);
				int oldRow = queens[ColWithMaxConflict];
				queens[ColWithMaxConflict] = RowWithMinConflict;
				
				// update
				rows[RowWithMinConflict]++;
				rows[oldRow]--;

				diagonal1[ColWithMaxConflict - oldRow + boardDim - 1]--;
				diagonal1[ColWithMaxConflict - RowWithMinConflict + boardDim - 1]++;

				diagonal2[ColWithMaxConflict + oldRow]--;
				diagonal2[ColWithMaxConflict + RowWithMinConflict]++;

			}
		}
	}
	
	
	public void printBoard() {
		for (int row = 0; row < boardDim; row++) {
			for (int col = 0; col < boardDim; col++) {
				if (this.queens[col] == row) {
					System.out.print(" * ");
				}
				else {
					System.out.print(" _ ");
				}
			}
			System.out.println();
		}
	}
	
	public void printBoardRows() {
		for (int i = 0; i < boardDim; i++) {
			System.out.print(this.queens[i]);
		}
	}
}
