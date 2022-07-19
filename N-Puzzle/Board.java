import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Board {
	
	int[][] board;
	int boardDim;
	Board parent = null;
	String direction = new String();
	static Stack<String> Steps = new Stack<>();

	Board(int[][] board, int boardDim) {
		this.setBoard(board);
		this.setBoardDim(boardDim);
	}
	
	public int getZeroCoordX (){
		for (int i = 0; i < this.boardDim; i++) {
			for (int j = 0; j < this.boardDim; j++) {
				if(this.board[i][j] == 0) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public int getZeroCoordY (){
		for (int i = 0; i < this.boardDim; i++) {
			for (int j = 0; j < this.boardDim; j++) {
				if(this.board[i][j] == 0) {
					return j;
				}
			}
		}
		return -1;
	}
	
	public Board generateLeftChildren() {
		int zeroCurrX = this.getZeroCoordX();
		
		int zeroCurrY = this.getZeroCoordY();

		int[][] temp = new int[this.boardDim][this.boardDim];

		for (int i = 0; i < this.boardDim; i++) {
			for (int j = 0; j < this.boardDim; j++) {
				temp[i][j] = this.board[i][j];
			}
		}

		int tempVar = 0;

		// left
		if (zeroCurrY + 1 < this.boardDim) {
			tempVar = temp[zeroCurrX][zeroCurrY];
			temp[zeroCurrX][zeroCurrY] = temp[zeroCurrX][zeroCurrY + 1];
			temp[zeroCurrX][zeroCurrY + 1] = tempVar;

			Board b = new Board(temp, this.boardDim);
			b.setParent(this);
			b.setDirection("left");
			
			return b;
		}
		return null;
	}

	public Board generateRightChildren() {
		int zeroCurrX = getZeroCoordX();
		int zeroCurrY = getZeroCoordY();

		int[][] temp = new int[this.boardDim][this.boardDim];

		for (int i = 0; i < this.boardDim; i++) {
			for (int j = 0; j < this.boardDim; j++) {
				temp[i][j] = this.board[i][j];
			}
		}

		int tempVar = 0;

		// right
		if ((zeroCurrY - 1 < this.boardDim) && (zeroCurrY - 1 >= 0)) {
			tempVar = temp[zeroCurrX][zeroCurrY];
			temp[zeroCurrX][zeroCurrY] = temp[zeroCurrX][zeroCurrY - 1];
			temp[zeroCurrX][zeroCurrY - 1] = tempVar;

			Board b = new Board(temp, this.boardDim);
			b.setParent(this);
			b.setDirection("right");
			
			return b;
		}
		return null;
	}

	public Board generateTopChildren() {
		int zeroCurrX = this.getZeroCoordX();
		int zeroCurrY = this.getZeroCoordY();

		int[][] temp = new int[this.boardDim][this.boardDim];

		for (int i = 0; i < this.boardDim; i++) {
			for (int j = 0; j < this.boardDim; j++) {
				temp[i][j] = this.board[i][j];
			}
		}

		int tempVar = 0;

		// top
		if (zeroCurrX + 1 < this.boardDim) {
			tempVar = temp[zeroCurrX][zeroCurrY];
			temp[zeroCurrX][zeroCurrY] = temp[zeroCurrX + 1][zeroCurrY];
			temp[zeroCurrX + 1][zeroCurrY] = tempVar;

			Board b = new Board(temp, this.boardDim);
			b.setParent(this);
			b.setDirection("top");
			
			return b;
		}
		return null;
	}

	public Board generateBottomChildren() {
		int zeroCurrX = this.getZeroCoordX();
		int zeroCurrY = this.getZeroCoordY();

		int[][] temp = new int[this.boardDim][this.boardDim];

		for (int i = 0; i < this.boardDim; i++) {
			for (int j = 0; j < this.boardDim; j++) {
				temp[i][j] = this.board[i][j];
			}
		}

		int tempVar = 0;

		// bottom
		if ((zeroCurrX - 1 < this.boardDim) && (zeroCurrX - 1 >= 0)) {
			tempVar = temp[zeroCurrX][zeroCurrY];
			temp[zeroCurrX][zeroCurrY] = temp[zeroCurrX - 1][zeroCurrY];
			temp[zeroCurrX - 1][zeroCurrY] = tempVar;

			Board b = new Board(temp, this.boardDim);
			b.setParent(this);
			b.setDirection("bottom");
			
			return b;
		}
		return null;
	}
	
	public List<Board> generateAllChildren() {
		List<Board> Children = new LinkedList<>();
		
		Children.add(this.generateLeftChildren());
		Children.add(this.generateRightChildren());
		Children.add(this.generateTopChildren());
		Children.add(this.generateBottomChildren());
		
		return Children;
	}
	public void printBoard() {
		for (int i = 0; i < this.boardDim; i++) {
			for (int j = 0; j < this.boardDim; j++) {
				System.out.print(this.board[i][j]+" ");
			}
            System.out.println();
		}
		System.out.println();
	}
	
	public void setBoardDim(int boardDim) {
		this.boardDim = boardDim;
	}
	
	public int getBoardDim() {
		return boardDim;
	}

	public void setBoard(int[][] startBoard) {
		this.board = startBoard;
	}
	
	public int[][] getBoard() {
		return board;
	}

	public Board getParent() {
		return parent;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public void setParent(Board parent) {
		this.parent = parent;
	}
	
	public static void printPath(Board resolution) {
		Steps.push(resolution.getDirection());
	}

}
