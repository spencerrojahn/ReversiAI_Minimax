import java.util.HashSet;
import java.util.Scanner;

public class Main {
	
	// DARK(X) - 0, LIGHT(O) - 1
	public static int boardSize;
	private static int machineIdNumber;
	private static int playerIdNumber;
	private static char machineTile;
	private static char playTile;
	private static int opponentType;
 	

	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Welcome to Reversi by Spencer Rojahn!");
		
		System.out.println();
		System.out.println("Choose a board size: ");
		System.out.println("1. Small 4x4\n2. Medium 6x6\n3. Standard 8x8");
		System.out.print("Your choice? (1-3) ");
		
		while (true) {
			try {
				int boardChoice = scan.nextInt();
				if (boardChoice>0 && boardChoice<4) {
					boardSize = (boardChoice*2)+2;
					break;
				}
			} catch (Exception e) {}
			System.out.print("Enter a choice from 1-3: ");
		}
		
		System.out.println("Choose your opponent: ");
		System.out.println("1. An opponent that uses MINIMAX\n2. An opponent that uses H-MINIMAX w/ search depth cutoff 6");
		System.out.print("Your choice? (1-2) ");
		while (true) {
			try {
				opponentType = scan.nextInt();
				if (opponentType>0 && opponentType<3) {
					break;
				}
			} catch (Exception e) {}
			System.out.print("Enter a choice from 1-2: ");
		}
		
	
		
		System.out.print("Do you want to play DARK(x) or LIGHT(o)? ");
		while (true) {
			try {
				String playerTile = scan.next();
				if (playerTile.contains("x")){
					playTile = 'x';
					machineTile = 'o';
					playerIdNumber = 0;
					machineIdNumber = 1;
					break;
				} else  if (playerTile.contains("o")){
					playTile = 'o';
					machineTile = 'x';
					playerIdNumber = 1;
					machineIdNumber = 0;
					break;
				} else {
					System.out.print("Enter x for DARK(x), OR o for LIGHT(o): ");
				}
				
			} catch (Exception e) {}
			System.out.print("Enter x for DARK(x), OR o for LIGHT(o): ");
		}

		
		char[][] initBoard = new char[boardSize][boardSize];
		String xAxisLabel;
		if (boardSize == 4) {
			xAxisLabel ="  a b c d";
		} else if (boardSize == 6) {
			xAxisLabel ="  a b c d e f";
		} else {
			xAxisLabel ="  a b c d e f g h";
		}
		for (int i =0; i < boardSize; i++) {
			for (int j =0; j < boardSize; j++) {
				// Need to FIX for specific board sizes
				if ((i == boardSize/2-1 && j ==boardSize/2-1) || (i == boardSize/2 && j ==boardSize/2)) {
					initBoard[i][j] = 'o';
				} else if ((i == boardSize/2 && j ==boardSize/2-1) || (i == boardSize/2-1 && j ==boardSize/2)) {
					initBoard[i][j] = 'x';
				} else {
					initBoard[i][j] = '_';
				}
			}
		}
		
		// Initial game State
		gameState initialState = new gameState(initBoard, 0);
		gameState currentState = initialState;
		
		int moveNum = 1;
		while (true) {
			System.out.println("\n\nMOVE #"+ moveNum);
			moveNum++;
			System.out.println("CURRENT BOARD:");
			System.out.println(xAxisLabel);
			for (int i =0; i < boardSize; i++) {
				System.out.print((i+1) + " ");
				for (int j =0; j < boardSize; j++) {
					System.out.print(currentState.getBoard()[i][j] + " ");
				}
				System.out.println();
			}
			
			HashSet<gameState> moves = Actions(currentState);
			
			
			if (moves.isEmpty()) {
				if (currentState.getNextUp() == 0) {
					System.out.println("\nDARK(x) has no valid move. Pass to LIGHT(o)");
				} else {
					System.out.println("\nLIGHT(o) has no valid move. Pass to DARK(x)");
				}
				currentState.setNextUp(Math.abs(currentState.getNextUp()-1));
				HashSet<gameState> moves2 = Actions(currentState);
				if (moves2.isEmpty()) {
					if (currentState.getNextUp() == 0) {
						System.out.println("\nDARK(x) has no valid move. GAME OVER");
					} else {
						System.out.println("\nLIGHT(o) has no valid move. GAME OVER");
					}
					int lightScore = getScore(currentState.getBoard(), 'o');
					int darkScore = getScore(currentState.getBoard(), 'x');
					System.out.println("\n*** RESULTS ***\n");
					if (lightScore > darkScore) {
						System.out.println("LIGHT wins!!");
					} else if (lightScore < darkScore){
						System.out.println("DARK wins!!");
					} else  {
						System.out.println("It's a TIE!!");
					}
					System.out.println("DARK(x) score: " + darkScore);
					System.out.println("LIGHT(o) score: " + lightScore);
					break;
				}
			}
			
		
			
			
			// next up is 'X' DARK
			if (currentState.getNextUp() == machineIdNumber) {
				if (machineTile == 'x') {
					System.out.println("\nNext to play: DARK("+machineTile+")\n");
				} else {
					System.out.println("\nNext to play: LIGHT("+machineTile+")\n");
				}
				if (opponentType == 1) {
					currentState = minimaxAlgos.miniMax(currentState);
					System.out.println("I, the MINIMAX agent, made a move.");
				} else {
					currentState = minimaxAlgos.HminiMax(currentState);
					System.out.println("I, the H-MINIMAX w/ a-b pruning agent, made a move.");
				}
			} else if (currentState.getNextUp() == playerIdNumber) {
				
				if (playTile == 'x') {
					System.out.println("\nNext to play: DARK("+playTile+")\n");
				} else {
					System.out.println("\nNext to play: LIGHT("+playTile+")\n");
				}
				System.out.print("Your Move: ");
				
				while (true) {
					try {
						String inputMove = scan.next();
						int x = getXCoordinate(inputMove);
						int y = getYCoordinate(inputMove);
						HashSet<gameState> possibleMoves = Actions(currentState);
						char[][] moveBoard = createNewBoard(currentState);
						
						boolean moveFound = false;
						for (gameState move: possibleMoves) {
							if (move.getBoard()[x][y] == playTile) {
								moveBoard = createNewBoard(move);
								currentState = new gameState(moveBoard, machineIdNumber);
								moveFound = true;
							}	
						}
						if (moveFound) {
							break;
						}
						
						System.out.print("Illegal Move. Make another move (ex \"a1\"): ");
					} catch(Exception e) {
						System.out.print("Illegal Move. Make another move (ex \"a1\"): ");
					}
				}
				
			}
				
				
			
		}

		
		
		
			
	}
	
	// Returns a set of possible states (actions) from a given state
	public static HashSet<gameState> Actions(gameState currentState) {
		
		HashSet<gameState> possibleStates = new HashSet<gameState>();
		
		
		// Store which player goes next after this player plays
		int nextNextUp;
		if (currentState.getNextUp() == 0) {
			nextNextUp = 1;
		} else {
			nextNextUp = 0;
		}
		
		// Store corresponding characters for each player
		// DARK(X) - 0, LIGHT(O) - 1
		char myTile;
		char opponentTile;
		if (currentState.getNextUp() == 0) {
			myTile = 'x';
			opponentTile = 'o';
		} else {
			myTile = 'o';
			opponentTile = 'x';
		}

		

		// Check the entire board of the state 
		for (int i = 0; i<boardSize; i++) {
			for (int j = 0; j<boardSize; j++) {
				
				// If the tile is my tile
				if (currentState.getBoard()[i][j] == '_') {
					
					char[][] moveBoard = createNewBoard(currentState);
					
					// Up
					try {
						// If it is an opponent tile, then you keep going up until you either my tile (fail) or
						// a blank space (success/move possible)
						if (currentState.getBoard()[i-1][j] == opponentTile) { 
							

							
							// Keep going up until the top is hit
							for (int k = i-2; k >= 0; k--) {
								
								if (currentState.getBoard()[k][j] == myTile) {
									for (int l = k+1; l<=i;l++) {
										moveBoard[l][j] = myTile;
									}
									break;
								} else if (currentState.getBoard()[k][j] == opponentTile) {
									continue;
								} else {
									break;
								}
								

							}
						}
					} catch (Exception e) {}
					
					
					
					// Down
					try {
						if (currentState.getBoard()[i+1][j] == opponentTile) { 
							
	
							
							// Keep going up until the top is hit
							for (int k = i+2; k < boardSize; k++) {
								if (currentState.getBoard()[k][j] == myTile) {
									for (int l = k-1; l>=i;l--) {
										moveBoard[l][j] = myTile;
									}
									break;
								} else if (currentState.getBoard()[k][j] == opponentTile) {
									continue;
								} else {
									break;
								}

							}
						}
					} catch (Exception e) {}
					
					// Left
					try {
						if (currentState.getBoard()[i][j-1] == opponentTile) { 

							
							// Keep going up until the top is hit
							for (int k = j-2; k >= 0; k--) {
								if (currentState.getBoard()[i][k] == myTile) {
									for (int l = k+1; l<=j;l++) {
										moveBoard[i][l] = myTile;
									}
									break;
								} else if (currentState.getBoard()[i][k] == opponentTile) {
									continue;
								} else {
									break;
								}
								

							}
						}
					} catch (Exception e) {}
					
					// Right
					try {
						if (currentState.getBoard()[i][j+1] == opponentTile) { 

							
							// Keep going up until the top is hit
							for (int k = j+2; k < boardSize; k++) {
								if (currentState.getBoard()[i][k] == myTile) {
									for (int l = k-1; l>=j;l--) {
										moveBoard[i][l] = myTile;
									}
									break;
								} else if (currentState.getBoard()[i][k] == opponentTile) {
									continue;
								} else {
									break;
								}

							}
						}
					} catch (Exception e) {}
					
					// Up and Left
					try {
						if (currentState.getBoard()[i-1][j-1] == opponentTile) { 
	
							
							// Keep going up until the top is hit
							int l = j-2;
							for (int k = i-2; k >= 0; k--) {
								if (currentState.getBoard()[k][l] == myTile) {
									int p = l+1;
									for (int q = k+1; q<=i;q++) {
										moveBoard[q][p] = myTile;
										p++;
									}
									break;
								} else if (currentState.getBoard()[k][l] == opponentTile) {
									l--;
									continue;
								} else {
									break;
								}
							}
								
							
						}
					} catch (Exception e) {}
					
					// Up and Right
					try {
						if (currentState.getBoard()[i-1][j+1] == opponentTile) { 
							
							
							// Keep going up until the top is hit
							int l = j+2;
							for (int k = i-2; k >= 0; k--) {
								if (currentState.getBoard()[k][l] == myTile) {
									int p = l-1;
									for (int q = k+1; q<=i;q++) {
										moveBoard[q][p] = myTile;
										p--;
									}
									break;
								} else if (currentState.getBoard()[k][l] == opponentTile) {
									l++;
									continue;
								} else {
									break;
								}
					
								
							}
						}
					} catch (Exception e) {}
					
					// Down and Left
					try {
						if (currentState.getBoard()[i+1][j-1] == opponentTile) { 
							
							// Keep going up until the top is hit
							int l = j-2;
							for (int k = i+2; k < boardSize; k++) {
								if (currentState.getBoard()[k][l] == myTile) {
									int p = l+1;
									for (int q = k-1; q>=i;q--) {
										moveBoard[q][p] = myTile;
										p++;
									}
									break;
								} else if (currentState.getBoard()[k][l] == opponentTile) {
									l--;
									continue;
								} else {
									break;
								}
					
							}
						}
					} catch (Exception e) {}
					
					// Down and Right
					try {
						if (currentState.getBoard()[i+1][j+1] == opponentTile) { 
						
							
							// Keep going up until the top is hit
							int l = j+2;
							for (int k = i+2; k < boardSize; k++) {
								if (currentState.getBoard()[k][l] == myTile) {
									int p = l-1;
									for (int q = k-1; q>=i;q--) {
										moveBoard[q][p] = myTile;
										p--;
									}
								} else if (currentState.getBoard()[k][l] == opponentTile) {
									l++;
									continue;
								} else {
									break;
								}
							}
						}
					} catch (Exception e) {}
					
					if (!compareBoards(moveBoard, currentState.getBoard())) {
						gameState move = new gameState(moveBoard, nextNextUp);		
						possibleStates.add(move);
					}
				}
				
			
			}
		}
		

		return possibleStates;
		
		
		
	}
	


	public static char[][] createNewBoard(gameState currentState) {
		char[][] moveBoard = new char[boardSize][boardSize];
		for (int f = 0; f<boardSize; f++) {
			for (int d = 0; d<boardSize; d++) {
				moveBoard[f][d] = currentState.getBoard()[f][d];
			}
		}
		return moveBoard;
	}
	
	
	public static int getYCoordinate(String inputMove) {
		
		
		
		if (inputMove.contains("a")) {
			return 0;
		} else if (inputMove.contains("b")) {
			return 1;
		} else if (inputMove.contains("c")) {
			return 2;
		} else if (inputMove.contains("d")) {
			return 3;
		} else if (inputMove.contains("e")) {
			return 4;
		} else if (inputMove.contains("f")) {
			return 5;
		} else if (inputMove.contains("g")) {
			return 6;
		} else {
			return 7;
		}
		
	}
	
	
	
	public static int getXCoordinate(String inputMove) {
		if (inputMove.contains("1")) {
			return 0;
		} else if (inputMove.contains("2")) {
			return 1;
		} else if (inputMove.contains("3")) {
			return 2;
		} else if (inputMove.contains("4")) {
			return 3;
		} else if (inputMove.contains("5")) {
			return 4;
		} else if (inputMove.contains("6")) {
			return 5;
		} else if (inputMove.contains("7")) {
			return 6;
		} else {
			return 7;
		} 
		
	}
	

	
	public static int getScore(char[][] board, int myTile2) {
		
		int score = 0;
		
		for (int i = 0; i<Main.boardSize; i++) {
			for (int j = 0; j<Main.boardSize; j++) { 
				if (board[i][j] == myTile2) {
					score++;
				}
			}
		}
		return score;
	}
	


	
	public static boolean compareBoards(char[][] board1, char[][] board2) {
		for (int i =0; i<boardSize; i++) {
			for (int j =0; j<boardSize; j++) {
				if (board1[i][j] != board2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	

	
	
	
}
