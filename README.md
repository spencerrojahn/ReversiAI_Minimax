# ReversiAI_Minimax
Description: This project allows the user to play a game of Reversi (Othello) against a AI agent that plays a strategy that incorporates the minimax algorithm. 

The "Main.java" file contains the Actions(state) function that return a HashSet of possible state actions given the current state. It also contains the main driver method for the entire game, along with other functions needed to help the program (such as compareBoards() - for comparing boards, getScore() - used with Utility(state) function, etc.). 

The "gameState.java" file contains the gameState class. A gameState class consists of a board arrangement and who is next to play. 

The "minimaxAlgos.java" file contains the minimax functions that are used to play the game against the user.


HOW TO RUN MY PROGRAM:

Command: "javac Main.java && java Main".

I suggest you use the agent with the minimax cutoff when playing gameboard size 6x6 or 8x8 and use the agent with no cutoff for gameboard size 4x4. 

When the program starts, it will prompt you for which game board size you would like to play, there are 3 options. Then, it will prompt you for your preferred opponent, there are 2 options here. Then, you will get to choose which tile you would like to be, either DARK(x) or LIGHT(o). After all that, the game will begin, DARK going first of course. You will alternate turns, making only legal moves until neither player can make any valid moves. The player with the most tiles on the board at the end of the game wins. Good luck.

An entered move looks like this, "a1" or this, "b4"

