package packkk;
import java.util.*;

public class PlayerBoard extends Board
{
	TreeMap<Integer, Integer> remainShips = new TreeMap<>();
	int attackDirections[] = new int[8];
	int nAtadir;
	int shortestShipAlive;
	int attackingPos = -1;
	int lengthShip;
	int longestAliveShip;
	
	PlayerBoard(int height, int width) {
		super(height, width);
		for(int i = 0; i < board.length; i ++) {
			board[i] = -1;
			//playerBoard[i] = -1;
			remainingSquares = board.length;
			squaresToAttack[i] = i;
		}
		this.shortestShipAlive = board.length;
	}
	
	int attackDirection = -2;
	int remainingSquares;
	int[] squaresToAttack;
	
}
