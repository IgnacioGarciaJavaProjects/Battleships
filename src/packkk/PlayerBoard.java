package packkk;
import java.util.*;

/**
* We have a lack here: We need either the player to say a ship is sunk
* or a structure with the knowledge of the size and situation of the ships.
* Finally we are going to need the same structure as in Board class.
* Every time the shortest or longest ship is sunk we will need to look
* for the new shortest and longest, respectively.
*/
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
	
	void intelAttack() {
		if(attackingPos != -1) {
			if(board[attackingPos] > 0) {
				attack(attackingPos);
			}
			else {
				if(attackDirection > -1) {
					int newPos = advance(attackingPos, attackDirection);
					if(!checkBorders(newPos) || lengthShip >= longestAliveShip || attack(newPos) == -1) {
						attackDirection = -2;
						attackingPos = -1; 
						remainShips.put(lengthShip, remainShips.get(lengthShip) -1);
						numberOfShips --;
						if(numberOfShips == 0) {
							System.out.println("Your navy is sunk. You lost!");
						}
						lengthShip = 0;

						if(remainShips.get(lengthShip) == 0 && lengthShip == longestAliveShip) {
							//longestAliveShip = max key of the map
							longestAliveShip = maxPos((Integer[])(remainShips.keySet().toArray()));
						}
					}
					else {
						lengthShip ++;
					}

				}
				else {
					if(attackDirection == -2) {
						for(int i = 0; i < 8; i ++) {
							attackDirections[i] = i;
							nAtadir = 8;
						}
						attackDirection = -1;
					}

					attackDirection = chooseDirection(attackingPos);
					int newPos = advance(attackingPos, attackDirection);
					if(attack(newPos) == -1) {
						attackDirection = -1;
					}
					else {
						attackingPos = newPos;
						lengthShip ++;
					}
				}
			}

		}
		else {
			int squareAttacked = squaresToAttack[r.nextInt(remainingSquares)];
			if(attack(squareAttacked) != -1) {
			    attackingPos = squareAttacked;
			}
		}

	}
	
	int chooseDirection(int sq) {
		int dChosen = -1; 
		do {
		    int row = posToRow(sq);
			int col = posToCol(sq);
		    dChosen = extractPos(r.nextInt(nAtadir), attackDirections, nAtadir);
			nAtadir --;
			
			switch(dChosen) {
				case 0:
					// we need to take into account first if there are 
					// short ships left, in order to set the max distance
					// from the borders of the board where to look.
					// idea: variable shortestShipAlive.
					// so if row < 0 + shortestShipAlive-1 then we must
					// delete that direction from the attackdirections.
					// same if row > height - shortestShipAlive.
					if(row < shortestShipAlive) {			
						continue;
					}
					break;
				case 1: 
					if(row < shortestShipAlive || col > width - shortestShipAlive) {
						continue;
					}
					break;
					
				case 2: 
					if(col > width - shortestShipAlive) {
						continue;
					}
					break;
					
				case 3: 
					if(row > height - shortestShipAlive || col > width - shortestShipAlive) {
						continue;
					}
					break;
					
				case 4: 
					if(row > height - shortestShipAlive) {
						continue;
					}
					break;
					
				case 5: 
					if(row > height - shortestShipAlive || col < shortestShipAlive) {
						continue;
					}
					break;
					
				case 6: 
					if(col < shortestShipAlive) {
						continue;
					}
					break;
					
				case 7: 
					if(row < shortestShipAlive || col < shortestShipAlive) {
						continue;
					}
					break;
				
			}
			
		} while(nAtadir > 0);
		return dChosen;
	}
	
	void putShip(int size, int health) {
		int trials = 0;
		
		while(trials < 20) {
			int ndirections = 8;
			int[] dt = {0,1,2,3,4,5,6,7};
			
			int direction = r.nextInt(ndirections);
			int[] resul = null;
			// change to dowhile
			do 
			 {
				 int pos = 0;
				 do {
					 pos = r.nextInt(board.length);
				 } while(board[pos] != -1);
				 resul = checkAvail(pos, direction, size);
				 if(resul != null) {
				     break;
				 }
				 ndirections --;
				while(direction < ndirections) {
					dt[direction] = dt[++direction];
				}
				direction = dt[r.nextInt(ndirections)];
			 } while(ndirections > 0);
			if(ndirections > 0) {
				placeShip(resul, health);
				System.out.println(this.toString());
				Integer hn = remainShips.get(health); 
				remainShips.put(health, hn == null ? 1 : hn + 1);
				if(size < shortestShipAlive) {
					shortestShipAlive = size;
				}
				if(size > longestAliveShip) {
					longestAliveShip = size;
				}
				return;
			}
			trials ++;
		}
	}
	
}
