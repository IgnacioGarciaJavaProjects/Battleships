package packkk;

import java.util.Random;
import java.util.*;

/**
 * This class represents the board of the ships game. It is a two-dimensional integer array
 * stored as a simple array in memory.
 * A number -1 is water, 0 is a sunk ship. A different number means the strength/health of that part
 * of the ship. The ships can be short or long, it will depend on various factors.
 * In this class we need a structure showing us the coordinates of the ships.
 * we don't want it to be expensive in space, so it will be in time.
 * Every time we hit a ship we can call a function that looks for the ship
 * in an array of ships, this is, a two-dimension array. Since we are going
 * to be searching blindly from the beginning of the array and every ship
 * can have different length, we are going to use a linked list of arrays
 * or ships. Also we will hold a ship variable with the most likely ship
 * to be attacked next, this is the last attacked unless it were sunk.
 * In case the ship is sunk we make the variable equal to null and remove
 * the array from the linked list.
 * We can guess most of the times we won't need to search.
 * Every time a part of a ship is sunk we can equal that index to -1. The
 * rest of the indexes of the ship contain the index of the board where that
 * ship is situated.
 
 * @author nacho
 *
 */

public class Board {
	protected Random r = new Random();
	int[] board;
	int height, width;
	//int numberOfShips; // current number of ships, at the beginning will be the total.
	ShipLinkedList sll;
	
	public Board(int height, int width) {
		System.out.println("creating board");
		this.height = height;
		this.width = width;
		this.board = new int[height*width];
		for(int i = 0; i < board.length; i ++) {
			board[i] = -1;
		}
		sll = new ShipLinkedList();
		sll.shortestAliveShip = board.length;
	}
	
	/**
	 * function to attack in a square of the board
	 * @param index the index to attack
	 * @return -1 when the attack hits water; 0 when hits an already wrecked part of a ship;
	 * the health of the part of the ship before the attack if it hits. That means, when it returns 1,
	 * that part of the ship is just wrecked.
	 */
	int attack(int index) {
		if(board[index] == -1) {
			return -1; // water!
		}
		else if(board[index] == 0) {
			return 0; // ship already wrecked
		}
		else {
			if(sll.sink(index)) {
				//numberOfShips --;
				if(sll.isEmpty()) {
					System.out.println("No enemy ships left. Congratulations, you won! :)");
				}
			}
			return -- board[index]; // ship attacked!
		}
	}
	
	void autoAttack () {
		for(int i = 0; i < board.length; i++) {
			System.out.println(attack(i));
		}
	}
	
	
	
	int maxPos(Integer[] t) {
		int max = 0;
		for(int i = 0; i < t.length; i ++) {
			if(t[i] > max) {
				max = t[i];
			}
		}
		return max;
	}
	
	int advance(int pos, int direc) {
		int row = posToRow(pos);
		int col = posToCol(pos);
		switch(direc) {
			case 0:
				return rcToPos(row-1, col);
			case 1:
				return rcToPos(row - 1, col + 1);
			case 2:
				return rcToPos(row, col + 1);
			case 3:
				return rcToPos(row + 1, col);
			case 4:
				return rcToPos(row + 1, col);
			case 5:
				return rcToPos(row + 1, col - 1);
			case 6:
				return rcToPos(row, col - 1);
			case 7:
				return rcToPos(row - 1, col - 1);
			default:
			return pos;
		}
	}
	
	
	
	boolean checkBorders(int pos) {
		int row = posToRow(pos);
		int col = posToCol(pos);
		if(pos < 0 || pos >= board.length || row < 0 || col < 0 || row >= height ||col >= width) {
			return false;
		}
		return true;
	}
	
	/*int[] removeCorners(int row, int col) {
		int i = 1;
		if(row > shortestShipAlive) {
			//deletePos(0);
			//de
		}
		while(i<8) {
			
		}
		int[] toRemove = new int[8];
		TreeSet<int> ts = new TreeSet<>();
		int i = 0;
		if(row < shortestShipAlive) {
			toRemove[i ++] = 0;
			toRemove[i ++] = 1;
			toRemove[i ++] = 7;
		}
	}*/
	
	
	
	void deletePos(int pos, int[] t, int limit) {
		while(pos < limit) {
			t[pos] = t[pos++];
		}
	}
	
	void deletePos(int pos,int quantity, int[] t, int limit) {
		while(pos < limit) {
			t[pos] = t[pos + quantity];
		}
	}
	
	int extractPos(int pos, int[] t, int limit) {
		int temp = t[pos];
		while(pos < limit) {
			t[pos] = t[pos++];
		}
		return temp;
	}
	
	void autoShips() {
		putShip(4, 3);
		putShip(3, 3);
		putShip(3, 3);
		putShip(2, 3);
		putShip(2, 3);
		putShip(2, 3);
		sll.takeShortLong();
		//numberOfShips = 6;
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
				sll.add(resul);
				/*Integer hn = remainShips.get(health); 
				remainShips.put(health, hn == null ? 1 : hn + 1);
				if(size < shortestShipAlive) {
					shortestShipAlive = size;
				}
				if(size > longestAliveShip) {
					longestAliveShip = size;
				}*/
				return;
			}
			trials ++;
		}
	}
	
	void placeShip(int[] places, int health) {
		for(int i = 0; i < places.length; i ++) {
			board[places[i]] = health;
		}
	}
	
	/*int nextSquare(int pos, int direction) {
		int row = posToRow(pos);
		int col = posToCol(pos);
		switch(direction) {
		case 0: //upwards
			row --;
			return rcToPos(row, col);
		}
	}*/
	
	int[] checkAvail(int pos, int direction, int size) {
		int row = posToRow(pos);
		int col = posToCol(pos);
		int[] out = new int[size];
		out[0] = pos;
		switch(direction) {
		case 0: //NORTH
			for(int i = 1; i < size; i ++) {
				row --;
				if(row < 0) {
					return null;
				}
				pos = rcToPos(row, col);
				if(pos  < 0 || board[pos] != -1) {
					return null;
				}
				out[i] = pos;
			}
			return out;
			
		case 1: // NE
			for(int i = 1; i < size; i ++) {
				row --;
				col ++;
				if(row < 0 || col >= width) {
					return null;
				}
				pos = rcToPos(row, col);
				int pos1 = rcToPos(row + 1, col);
				int pos2 = rcToPos(row, col - 1);
				if(pos < 0 || board[pos] != -1 || board[pos1] != -1 || board[pos2] != -1) {
					return null;
				}
				out[i] = pos;
			}
			return out;
			
		case 2: // E
			for(int i = 1; i < size; i ++) {
				col ++;
				if(col >= width) {
					return null;
				}
				pos = rcToPos(row, col);
				if(pos >= board.length || board[pos] != -1) {
					return null;
				}
				out[i] = pos;
			}
			return out;
			
		case 3: // SE
			for(int i = 1; i < size; i ++) {
				row ++;
				col ++;
				if(row >= height || col >= width) {
					return null;
				}
				pos = rcToPos(row, col);
				int pos1 = rcToPos(row - 1, col);
				int pos2 = rcToPos(row, col - 1);
				if(pos >= board.length || board[pos] != -1 || board[pos1] != -1 || board[pos2] != -1) {
					return null;
				}
				out[i] = pos;
			}
			return out;
			
		case 4: // S
			for(int i = 1; i < size; i ++) {
				row ++;
				if(row >= height) {
					return null;
				}
				pos = rcToPos(row, col);
				if(pos >= board.length || board[pos] != -1) {
					return null;
				}
				out[i] = pos;
			}
			return out;
			
		case 5: // SW
			for(int i = 1; i < size; i ++) {
				row ++;
				col --;
				if(row >= height || col < 0) {
					return null;
				}
				pos = rcToPos(row, col);
				int pos1 = rcToPos(row - 1, col);
				int pos2 = rcToPos(row, col + 1);
				if(pos >= board.length || board[pos] != -1 || board[pos1] != -1 || board[pos2] != -1) {
					return null;
				}
				out[i] = pos;
			}
			return out;
		
		case 6: // W
			for(int i = 1; i < size; i ++) {
				col --;
				if(col < 0) {
					return null;
				}
				pos = rcToPos(row, col);
				if(pos < 0 || board[pos] != -1) {
					return null;
				}
				out[i] = pos;
			}
			return out;
			
		case 7: // NW
			for(int i = 1; i < size; i ++) {
				row --;
				col --;
				if(row < 0 || col < 0) {
					return null;
				}
				pos = rcToPos(row, col);
				int pos1 = rcToPos(row + 1, col);
				int pos2 = rcToPos(row, col + 1);
				if(pos < 0 || board[pos] != -1 || board[pos1] != -1 || board[pos2] != -1) {
					return null;
				}
				out[i] = pos;
			}
			return out;
			
		default:
			return null;
		}
	}
	
	int posToRow(int pos) {
		return pos / width;
	}
	
	int posToCol(int pos) {
		return pos % width;
	}
	
	int rcToPos(int row, int col) {
		return width * row + col;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n==========================================\n");
		int pos = 0;
		for(int i = 0; i < height; i ++) {
			for(int j = 0; j < width; j ++) {
				if(board[pos] == -1) {
					sb.append(' ');
				}
				else {
					sb.append(board[pos]);
				}
				sb.append(" ");
				pos ++;
			}
			sb.append("\n");
		}
		sb.append("==========================================\n");
		
		
		return sb.toString();
	}
}

/**
 * Linked list of int arrays, which will maintain them ordered by increasing length.
 * @author nacho
 *
 */
class ShipLinkedList {
	class ShipLink {
		ShipLink next, previous;
		int[] ship;
		
		ShipLink(int[] t) {
			this.ship = t;
		}
		
		int length() {
			return ship.length;
		}
	}
	
	private ShipLink head, tail;
	ShipLink lastShipHit;
	int numberOfShips;
	int shortestAliveShip;
	int longestAliveShip;
	//boolean maxminShips;
	
	public boolean isEmpty() {
		return numberOfShips == 0;
	}
	
	public void takeShortLong() {
		shortestAliveShip = head.length();
		longestAliveShip = tail.length();
	}
	
	/**
	 * Adds a ship in ascendent order of lengths. For that it uses a linear
	 * search for the correct place for the ship.
	 * @param newShip
	 */
	public void add(int[] newShip) {
		int l = newShip.length;
		if(head == null) {
			head = tail = new ShipLink(newShip);
			numberOfShips ++;
			return;
		}
		ShipLink current = head;
		while(current != null) {
			if(l <= current.length()) {
				break;
			}
			current = current.next;
		} 
		if(current != null) {
			ShipLink sl = new ShipLink(newShip);
			if(current.previous != null) {
				current.previous.next = sl;
			}
			sl.previous = current.previous;
			sl.next = current;
			current.previous = sl;
		}
		else {
			ShipLink sl = new ShipLink(newShip);
			tail.next = sl;
			sl.previous = tail;
			tail = sl;
			
		}
		numberOfShips ++;
	}
	
	public void remove(ShipLink oldShip) {
		if(oldShip.previous == null) {
			if(oldShip.next == null) {
				head = tail = null;
			}
			else {
				head = oldShip.next;
				head.previous = null;
				oldShip.next = null;
				shortestAliveShip = head.length();
			}
		}
		else if(oldShip.next == null) {
			tail = oldShip.previous;
			tail.next = null;
			oldShip.previous = null;
			longestAliveShip = tail.length();
		}
		else {
			oldShip.previous.next = oldShip.next;
			oldShip.next.previous = oldShip.previous;
			oldShip.previous = null;
			oldShip.next = null;
		}
		
		numberOfShips --;
	}
	
	public boolean sink(int pos) {
		if(lastShipHit != null) {
			int[] ship = lastShipHit.ship;
			int i;
			for(i = 0; i < ship.length; i ++) {
				if(ship[i] == pos) {
					ship[i] = -1;
					break;
				}
			}
			if(i >= ship.length) {
				lastShipHit = lookUpSinkShip(pos);
			}
		}
		else {
			lastShipHit = lookUpSinkShip(pos);
		}
		if(isSunk(lastShipHit)) {
			remove(lastShipHit);
			lastShipHit = null;
			return true;
		}
		return false;
	}
	
	ShipLink lookUpSinkShip(int pos) {
		ShipLink current = head;
		while(current != null) {
			int[] ship = current.ship;
			for(int i = 0; i < ship.length; i ++) {
				if(ship[i] == pos) {
					ship[i] = -1;
					return current;
				}
			}
			current = current.next;
		}
		return null;
	}
	
	boolean isSunk(ShipLink sp) {
		int[] ship = sp.ship;
		int total = 0;
		for(int i = 0; i < ship.length; i ++) {
			if(ship[i] == -1) {
				total ++;
			}
		}
		return total == ship.length;
	}
	
	//private ShipLink next() 
	
	
}
