// Class for each state (node)
public class gameState {
	
	// Each state needs a board configuration
	// DARK(X) - 0, LIGHT(O) - 1
	private char[][] board = new char[n][n];
	private int nextUp;
	
	public gameState(char[][] board, int nextUp) {
		this.board = board;
		this.nextUp = nextUp;
		
	}

	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	public int getNextUp() {
		return nextUp;
	}

	public void setNextUp(int nextUp) {
		this.nextUp = nextUp;
	}




}
