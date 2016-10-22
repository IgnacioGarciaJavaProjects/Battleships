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
	
	
	boolean dirinverted;
	void intelAttack() {
		//System.out.println("loop = " + loop);
		if(attackingPos != -1) { // if we were attacking a square
			if(board[attackingPos] > 0) { // if the square is not sunk yet
				System.out.println("square is not 0 yet");
				if(attack(attackingPos) == 0) { // if we just sank it
				
					System.out.println("square is 0 now. looking for attackingPos " + attackingPos);
					deletePos(dicotomicSearch(squaresToAttack, attackingPos, remainingSquares), squaresToAttack, remainingSquares);
					// we delete that position from the squares to attack
					remainingSquares --;
					//System.out.println("not sunk");
					//int i = 0;
					//while(i < 100) {
						//System.out.println("not sunk");
						//i++;
					//}
					if(sunk) {
						
						//while(i < 10000) {
							System.out.println("sunk");
						//	i++;
						lengthShip = 0;
						//}
						attackDirection = -2;
						attackingPos = -1; 
						dirinverted = false;
						if(sll.isEmpty()) {
							System.out.println("Your navy is sunk. You lost!");
						}
						sunk = false;
					}
					else {
						System.out.println("not sunk");
					}
				}
			}
			else {
				//loop ++;
				//if(loop > 8) {
					//loop = 0;
					//return;
				//}
				System.out.println("square is 0");
				if(attackDirection > -1) {
					
					int newPos = advance(attackingPos, attackDirection);
					System.out.println("advancing to square " + newPos);
					if(!checkBorders(newPos) || lengthShip > sll.longestAliveShip || attack(newPos) < 1) {
						//
						System.out.println("its not good. taking inipos: " + inipos);
						//loop ++;
						//if(loop > 3) {
							//return;
						//}
						attackingPos = inipos;
						attackDirection = -1;
						//if(!dirinverted) {
							//attackDirection = invertDirection(attackDirection);
						//}
						//else {
							//attackDirection = chooseDirection(attackingPos);
						//}
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
						System.out.println("its good");
						
						attackingPos = newPos;
						//lengthShip ++;
					}

				}
				else {
					if(attackDirection == -2) {
						System.out.println("we reset directions");
						for(int i = 0; i < 8; i ++) {
							attackDirections[i] = i;
							nAtadir = 8;
						}
						attackDirection = -1;
					}
					System.out.println("choose a new direction");
					//loop ++;
					//if(loop > 8) {
						//loop = 0;
						//return;
					//}

					attackDirection = chooseDirection(attackingPos);
					int newPos = advance(attackingPos, attackDirection);
					if(!checkBorders(newPos) || attack(newPos) == -1) {
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
			int pos = r.nextInt(remainingSquares);
			int squareAttacked = squaresToAttack[pos];
			if(attack(squareAttacked) != -1) {
				inipos = squareAttacked;
			    attackingPos = squareAttacked;
				//loop = 0;
			}
			else {
				deletePos(pos, squaresToAttack, remainingSquares);
				remainingSquares --;
			}
		}

	}
	
	// version 2
	
	void intelAttackDirInvert() {
		//System.out.println("loop = " + loop);
		if(attackingPos != -1) { // if we were attacking a square
			if(board[attackingPos] > 0) { // if the square is not sunk yet
				System.out.println("square is not 0 yet");
				if(attack(attackingPos) == 0) { // if we just sank it

					System.out.println("square is 0 now. looking for attackingPos " + attackingPos);
					deletePos(dicotomicSearch(squaresToAttack, attackingPos, remainingSquares), squaresToAttack, remainingSquares);
					// we delete that position from the squares to attack
					remainingSquares --;
					//System.out.println("not sunk");
					//int i = 0;
					//while(i < 100) {
					//System.out.println("not sunk");
					//i++;
					//}
					if(sunk) {

						//while(i < 10000) {
						System.out.println("sunk");
						//	i++;
						lengthShip = 0;
						//}
						attackDirection = -2;
						attackingPos = -1; 
						dirinverted = false;
						if(sll.isEmpty()) {
							System.out.println("Your navy is sunk. You lost!");
						}
						sunk = false;
					}
					else {
						System.out.println("not sunk");
					}
				}
			}
			else {
				//loop ++;
				//if(loop > 8) {
				//loop = 0;
				//return;
				//}
				System.out.println("square is 0");
				if(attackDirection > -1) {

					int newPos = advance(attackingPos, attackDirection);
					System.out.println("advancing to square " + newPos);
					if(!checkBorders(newPos) || lengthShip > sll.longestAliveShip || attack(newPos) < 1) {
						//
						System.out.println("its not good. taking inipos: " + inipos);
						//loop ++;
						//if(loop > 3) {
						//return;
						//}
						//
						attackingPos = inipos;
						if(!dirinverted) {
							attackDirection = invertDirection(attackDirection);
						}
						else {	
							attackDirection = -1;
						}
						//else {
						//attackDirection = chooseDirection(attackingPos);
						//}
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
						System.out.println("its good");

						attackingPos = newPos;
						//lengthShip ++;
					}

				}
				else {
					if(attackDirection == -2) {
						System.out.println("we reset directions");
						for(int i = 0; i < 8; i ++) {
							attackDirections[i] = i;
							nAtadir = 8;
						}
						attackDirection = -1;
					}
					System.out.println("choose a new direction");
					
					//loop ++;
					//if(loop > 8) {
					//loop = 0;
					//return;
					//}

					attackDirection = chooseDirection(attackingPos);
					int newPos = advance(attackingPos, attackDirection);
					if(!checkBorders(newPos) || attack(newPos) == -1) {
						attackDirection = -1;
					}
					else {
						attackingPos = newPos;
						lengthShip ++;
						dirinverted = false;
					}
				}
			}

		}
		else {
			int pos = r.nextInt(remainingSquares);
			int squareAttacked = squaresToAttack[pos];
			if(attack(squareAttacked) != -1) {
				inipos = squareAttacked;
			    attackingPos = squareAttacked;
				//loop = 0;
			}
			else {
				deletePos(pos, squaresToAttack, remainingSquares);
				remainingSquares --;
			}
		}

	}
	
	/**
	* Other version of intelAttack, in this case we will call 
	* recursively to the function every time we find a <1 element
	* of the board and cannot solve it by inverting the direction.
	* This means that we have some positions in the board that are 0,
	* but do not give a sunk. So: They could belong to another ship,
	* different from the original one we found first, or else they
	* could be part of that ship. But in such case it would be solved
	* just by inverting the attack direction. That leaves only
	* the possibility of they belonging to other ship, what means that
	* the moment we found them we were in other directions different 
	* from the one of the original ship, or also the ships could be 
	* consecutive, in which case the second ship would be sunk instead of
	* the original one, and the current algorithm wouldnt realize that,
	* even ourselves will have much trouble to guess in certain cases,
	* since there is no information about which ship was sunk. Unless
	* we used a spy algorithm which seems rather unfair.
	* we call then the function on the new position. Actually there is
	* no need for recursion since we can just store the positions in
	* a vector. If we invert direction and have no success then we
	* extract the first position from the vector if its not empty. 
	* From it we 
	* choose a direction. But every time we must remember to delete the
	* inverse of the direction where we came from. This means storing
	* a new value of such direction somewhere. Every time we sink a ship
	* we will look for the positions in the same direction on this vector.
	* this is, we remove the positions of the sunk ship from the vector
	* if some of them were there.
	*/
	void intelAttackRecursive() {
		
	}
	
	int[] zeroes;
	int zerocount;
	
	void keepAttack() {
		System.out.println("square is not 0 yet");
		if(attack(attackingPos) == 0) { // if we just sank it
			//zeroes[zerocount ++] = attackingPos;
			System.out.println("square is 0 now. looking for attackingPos " + attackingPos);
			deletePos(dicotomicSearch(squaresToAttack, attackingPos, remainingSquares), squaresToAttack, remainingSquares);
			// we delete that position from the squares to attack
			remainingSquares --;
			//System.out.println("not sunk");
			//int i = 0;
			//while(i < 100) {
			//System.out.println("not sunk");
			//i++;
			//}
			if(sunk) {

				//while(i < 10000) {
				System.out.println("sunk");
				if(sll.isEmpty()) {
					System.out.println("Your navy is sunk. You lost!");
					//return;
				}
				//deleteShipFromZeroes();
				if(zerocount > 0) {
					attackingPos = extractPos(zerocount - 1, zeroes, zerocount);
					zerocount --;
				}
				else {
					attackingPos = -1; 
				}
				//	i++;
				lengthShip = 0;
				//}
				attackDirection = -2;
				
				dirinverted = false;
				
				sunk = false;
			}
			else {
				System.out.println("not sunk");
			}
		}
	}
	
	void intelAttackStoreZeros() {
		if(attackingPos != -1) { // if we were attacking a square
			if(board[attackingPos] > 0) { // if the square is not sunk yet
				keepAttack();
				/*System.out.println("square is not 0 yet");
				if(attack(attackingPos) == 0) { // if we just sank it
					zeroes[zerocount ++] = attackingPos;
					System.out.println("square is 0 now. looking for attackingPos " + attackingPos);
					deletePos(dicotomicSearch(squaresToAttack, attackingPos, remainingSquares), squaresToAttack, remainingSquares);
					// we delete that position from the squares to attack
					remainingSquares --;
					//System.out.println("not sunk");
					//int i = 0;
					//while(i < 100) {
					//System.out.println("not sunk");
					//i++;
					//}
					if(sunk) {

						//while(i < 10000) {
						System.out.println("sunk");
						deleteShipFromZeroes();
						//	i++;
						lengthShip = 0;
						//}
						attackDirection = -2;
						attackingPos = -1; 
						dirinverted = false;
						if(sll.isEmpty()) {
							System.out.println("Your navy is sunk. You lost!");
						}
						sunk = false;
					}
					else {
						System.out.println("not sunk");
					}
				}*/
			}
			else {
				//loop ++;
				//if(loop > 8) {
				//loop = 0;
				//return;
				//}
				System.out.println("square is 0"); // problem with ships of strength 1
				if(attackDirection > -1) {

					int newPos = advance(attackingPos, attackDirection); // we advance in the current direction
					System.out.println("advancing to square " + newPos);
					if(checkBorders(newPos)) {
						if(board[newPos] < 1) {
							attack(newPos);
							attackingPos = inipos;
							if(!dirinverted) {
								attackDirection = invertDirection(attackDirection);
							}
							else {	
								attackDirection = -1;
							}
						}
						else {
							System.out.println("its good");

							attackingPos = newPos;
							keepAttack();
							//lengthShip ++;
						}
					} 
					else {
						attackingPos = inipos;
						if(!dirinverted) {
							attackDirection = invertDirection(attackDirection);
						}
						else {	
							attackDirection = -1;
						}
					}
							
				}
				else { // we dont have an attack direction, its < 0
					if(attackDirection == -2) {
						System.out.println("we reset directions");
						for(int i = 0; i < 8; i ++) {
							attackDirections[i] = i;
							nAtadir = 8;
						}
						attackDirection = -1;
					}
					System.out.println("choose a new direction");

					//loop ++;
					//if(loop > 8) {
					//loop = 0;
					//return;
					//}

					attackDirection = chooseDirection(attackingPos);
					int newPos = advance(attackingPos, attackDirection);
					if(!checkBorders(newPos)) {
						attackDirection = -1;
					}
					else {
						if(board[newPos] < 1) {
							attack(newPos);
							attackDirection = -1;
						}
						else {
							attackingPos = newPos;
							dirinverted = false;
							keepAttack();
						}
						
						//intelAttackStoreZeros();
						//lengthShip ++;
						
					}
				}
			}

		}
		else { // we are not attacking a square, we look for one
			
			int pos = r.nextInt(remainingSquares);
			int squareAttacked = squaresToAttack[pos];
			if(board[squareAttacked] != -1) {
				inipos = squareAttacked;
			    attackingPos = squareAttacked;
				keepAttack();
				//loop = 0;
			}
			else {
				attack(squareAttacked);
				deletePos(pos, squaresToAttack, remainingSquares);
				remainingSquares --;
			}
		}
	}
	/*
	if(board[newPos] == 0) { // if we find 0
		int poszero = dicotomicSearch(zeroes, newPos, zerocount);
		if(poszero > -1) {
			// this will cause an infinite loop unless
		}
	}
	else {
		//if(!attacked(newPos))
		attack(newPos);
	}*/
	
	void deleteShipFromZeroes(int currPos) {
		// I just realised this is not feasible so easily. The last attack direction could not
		// be the direction of the ship. This will happen only certain times, but those times
		// we will have so much error.
		
		
	}
	
	int invertDirection(int d) {
		System.out.print("inverting direction from " + d);
		dirinverted = true;
		if(d < 4) {
			d += 4;
		}
		else {
			d -= 4;
		}
		System.out.println( " to " + d);
		int pos = dicotomicSearch(attackDirections, d, nAtadir);
		if(pos > -1) {
			int res = extractPos(pos, attackDirections, nAtadir);
			nAtadir --;
			return res;
		}
		else {
			return -1;
		}
	}
	
	int chooseDirection(int sq) {
		int dChosen = -1; 
		int row = posToRow(sq);
		int col = posToCol(sq);
		int limit = sll.shortestAliveShip - 1;
		
		while(nAtadir > 0) {
			dChosen = extractPos(r.nextInt(nAtadir), attackDirections, nAtadir);
			nAtadir --;
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
					if(row >= limit) {			
						return dChosen;
					}
					break;
				case 1: 
					if(row >= limit && col <= width - limit) {
						return dChosen;
					}
					break;
					
				case 2: 
					if(col <= width - limit) {
						return dChosen;
					}
					break;
					
				case 3: 
					if(row <= height - limit && col <= width - limit) {
						return dChosen;
					}
					break;
					
				case 4: 
					if(row <= height - limit) {
						return dChosen;
					}
					break;
					
				case 5: 
					if(row <= height - limit && col >= limit) {
						return dChosen;
					}
					break;
					
				case 6: 
					if(col >= limit) {
						return dChosen;
					}
					break;
					
				case 7: 
					if(row >= limit && col >= limit) {
						return dChosen;
					}
					break;
			}
			//return dChosen;
			//dChosen = extractPos(r.nextInt(nAtadir), attackDirections, nAtadir);
			//nAtadir --;
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
