import java.util.HashSet;
import java.util.HashMap;

public class minimaxAlgos {
	
	//private static stateTree tree;
	private static char myTile;
	private static char opponentTile;
	
	// DARK(X) - 0, LIGHT(O) - 1
	public static gameState miniMax(gameState currentState) {
		
		if (currentState.getNextUp() == 0) {
			myTile = 'x';
			opponentTile = 'o';
		} else {
			myTile = 'o';
			opponentTile = 'x';
		}
		
		HashSet<gameState> possibleActions = Main.Actions(currentState);
		HashMap<Integer, gameState> scoreToAction = new HashMap<Integer, gameState>();
		
		for (gameState action: possibleActions) {
			int utility = minValue(action);
			scoreToAction.put(utility, action);
			
		}
		
		int v = -999;
		for (int util: scoreToAction.keySet()) {
			v = Math.max(v, util);
		}
		
		return scoreToAction.get(v);	
	}
	
	//  With 6 depth as heuristic
	public static gameState HminiMax(gameState currentState) {
		int depth = 0;
		if (currentState.getNextUp() == 0) {
			myTile = 'x';
			opponentTile = 'o';
		} else {
			myTile = 'o';
			opponentTile = 'x';
		}
		
		HashSet<gameState> possibleActions = Main.Actions(currentState);
		HashMap<Integer, gameState> scoreToAction = new HashMap<Integer, gameState>();
		
		for (gameState action: possibleActions) {
			int utility = HminValue(action, depth);
			scoreToAction.put(utility, action);
			
		}
		
		int v = -999;
		for (int util: scoreToAction.keySet()) {
			v = Math.max(v, util);
		}
		
		return scoreToAction.get(v);	
	}
	
	public static int maxValue(gameState currentState ) {
		
		HashSet<gameState> possibleActions = Main.Actions(currentState);
		
		if (possibleActions.isEmpty()) {
			int utility = getUtility(currentState.getBoard(), myTile);
			return utility;
		} 
		
		int v = -999;
		for (gameState action: possibleActions) {
			v =  Math.max(v, minValue(action));
		}
		return v;
		
	
		
	}
	
	public static int minValue(gameState currentState) {
		HashSet<gameState> possibleActions = Main.Actions(currentState);
		
		if (possibleActions.isEmpty()) {
			int utility = getUtility(currentState.getBoard(), myTile);
			return utility;
		} 
		
		int v = 999;
		for (gameState action: possibleActions) {
			v =  Math.min(v, maxValue(action));
		}
		return v;
	
	}
	
	public static int getUtility(char[][] board, char myTile) {
		
		int myScore = 0;
		int opponentScore = 0;
		
		for (int i = 0; i<Main.boardSize; i++) {
			for (int j = 0; j<Main.boardSize; j++) { 
				if (board[i][j] == myTile) {
					myScore++;
				} else if (board[i][j] == opponentTile) {
					opponentScore++;
				}
			}
		}
		return myScore -opponentScore;
	}
	
	public static int HmaxValue(gameState currentState, int depth) {
		
		HashSet<gameState> possibleActions = Main.Actions(currentState);
		depth++;
		if (possibleActions.isEmpty() || depth ==6) {
			int utility = getUtility(currentState.getBoard(), myTile);
			return utility;
		} 
		
		int v = -999;
		for (gameState action: possibleActions) {
			v =  Math.max(v, HminValue(action, depth));
		}
		return v;
		
	
		
	}
	
	public static int HminValue(gameState currentState, int depth) {
		HashSet<gameState> possibleActions = Main.Actions(currentState);
		depth++;
		if (possibleActions.isEmpty() || depth ==6) {
			int utility = getUtility(currentState.getBoard(), myTile);
			return utility;
		} 
		
		int v = 999;
		for (gameState action: possibleActions) {
			v =  Math.min(v, HmaxValue(action, depth));
		}
		return v;
	
	}
	 

}
