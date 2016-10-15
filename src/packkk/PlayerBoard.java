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
	//TreeMap<Integer, Integer> remainShips = new TreeMap<>();
	int attackDirections[] = new int[8];
	int nAtadir;
	//int shortestShipAlive;
	int attackingPos = -1;
	int lengthShip;
	//int longestAliveShip;

	int attackDirection = -2;
	int remainingSquares;
	int[] squaresToAttack;
	int inipos;
	
	
	PlayerBoard(int height, int width) {
		super(height, width);
		squaresToAttack = new int[board.length];
		for(int i = 0; i < board.length; i ++) {
			board[i] = -1;
			//playerBoard[i] = -1;
			remainingSquares = board.length;
			squaresToAttack[i] = i;
		}
		//this.shortestShipAlive = board.length;
	}
	
	
	void intelAttack() {
		if(attackingPos != -1) {
			if(board[attackingPos] > 0) {
				System.out.println("not 0");
				if(attack(attackingPos) == 0) {
					deletePos(dicotomicSearch(squaresToAttack, attackingPos), squaresToAttack, remainingSquares);
					remainingSquares --;
					System.out.println("not sunk");
					int i = 0;
					while(i < 100) {
						System.out.println("not sunk");
						i++;
					}
					if(sunk) {
						
						while(i < 10000) {
							System.out.println("sunk");
							i++;
						}
						attackDirection = -2;
						attackingPos = -1; 
						if(sll.isEmpty()) {
							System.out.println("Your navy is sunk. You lost!");
						}
						sunk = false;
					}
				}
			}
			else {
				System.out.println("yes 0");
				if(attackDirection > -1) {
					int newPos = advance(attackingPos, attackDirection);
					
					if(!checkBorders(newPos) || lengthShip >= sll.longestAliveShip || attack(newPos) == -1) {
						//
						attackingPos = inipos;
						attackDirection = invertDirection(attackDirection);
						//remainShips.put(lengthShip, remainShips.get(lengthShip) -1);
						//numberOfShips --;
						//if(numberOfShips == 0) {
							//System.out.println("Your navy is sunk. You lost!");
						//}
						//lengthShip = 0;

						/*if(remainShips.get(lengthShip) == 0 && lengthShip == longestAliveShip) {
							//longestAliveShip = max key of the map
							longestAliveShip = maxPos((Integer[])(remainShips.keySet().toArray()));
						}*/
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
				inipos = squareAttacked;
			    attackingPos = squareAttacked;
			}
			else {
				deletePos(squareAttacked, squaresToAttack, remainingSquares);
				remainingSquares --;
			}
		}

	}
	
	int invertDirection(int d) {
		if(d == 7) {
			return 3;
		}
		else {
			return d + 4;
		}
	}
	
	int chooseDirection(int sq) {
		int dChosen = -1; 
		int row = posToRow(sq);
		int col = posToCol(sq);
		dChosen = extractPos(r.nextInt(nAtadir), attackDirections, nAtadir);
		nAtadir --;
		while(nAtadir > 0) {
			printArray(attackDirections);
			System.out.println("Shortestship = " + sll.shortestAliveShip);
			System.out.println("row = " + row + " sq = " + sq + " natadir = " + nAtadir + " direc chosen: " + dChosen);
			switch(dChosen) {
				case 0:
					// we need to take into account first if there are 
					// short ships left, in order to set the max distance
					// from the borders of the board where to look.
					// idea: variable shortestShipAlive.
					// so if row < 0 + shortestShipAlive-1 then we must
					// delete that direction from the attackdirections.
					// same if row > height - shortestShipAlive.
					if(row >= sll.shortestAliveShip) {			
						return dChosen;
					}
					break;
				case 1: 
					if(row >= sll.shortestAliveShip && col <= width - sll.shortestAliveShip) {
						return dChosen;
					}
					break;
					
				case 2: 
					if(col <= width - sll.shortestAliveShip) {
						return dChosen;
					}
					break;
					
				case 3: 
					if(row <= height - sll.shortestAliveShip && col <= width - sll.shortestAliveShip) {
						return dChosen;
					}
					break;
					
				case 4: 
					if(row <= height - sll.shortestAliveShip) {
						return dChosen;
					}
					break;
					
				case 5: 
					if(row <= height - sll.shortestAliveShip && col >= sll.shortestAliveShip) {
						return dChosen;
					}
					break;
					
				case 6: 
					if(col >= sll.shortestAliveShip) {
						return dChosen;
					}
					break;
					
				case 7: 
					if(row >= sll.shortestAliveShip && col >= sll.shortestAliveShip) {
						return dChosen;
					}
					break;
			}
			//return dChosen;
			dChosen = extractPos(r.nextInt(nAtadir), attackDirections, nAtadir);
			nAtadir --;
		} 
		return -1;
		//return dChosen;
	}
	
	/*void putShip(int size, int health) {
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
	}*/
	
	void placeShip(int[] places, int health) {
		int size = places.length;
		for(int i = 0; i < places.length; i ++) {
			board[places[i]] = health;
		}
	}
	
}
