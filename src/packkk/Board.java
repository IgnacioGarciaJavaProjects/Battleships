package packkk;

import java.util.Random;

/**
 * This class represents the board of the ships game. It is a two-dimensional integer array
 * stored as a simple array in memory.
 * A number -1 is water, 0 is a sunk ship. A different number means the strength/health of that part
 * of the ship. The ships can be short or long, it will depend on various factors.
 * @author nacho
 *
 */

public class Board {
	int[] board;
	int height, width;
	int numberOfShips; // current number of ships, at the beginning will be the total.
	
	public Board(int height, int width) {
		System.out.println("creating board");
		this.height = height;
		this.width = width;
		this.board = new int[height*width];
		for(int i = 0; i < board.length; i ++) {
			board[i] = -1;
		}
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
			return board[index] --; // ship attacked!
		}
	}
	
	void autoShips() {
		putShip(4, 3);
		putShip(3, 3);
		putShip(3, 3);
		putShip(2, 3);
		putShip(2, 3);
		putShip(2, 3);
	}
	
	void putShip(int size, int health) {
		int trials = 0;
		Random r = new Random();
		while(trials < 20) {
			int ndirections = 8;
			int[] dt = {0,1,2,3,4,5,6,7};
			int pos = r.nextInt(board.length);
			int direction = r.nextInt(ndirections);
			int[] resul = null;
			while(ndirections > 0 && (resul = checkAvail(pos, direction, size)) == null) {
				ndirections --;
				while(direction < ndirections) {
					dt[direction] = dt[++direction];
				}
				direction = dt[r.nextInt(ndirections)];
			}
			if(ndirections > 0) {
				placeShip(resul, health);
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
				if(pos < 0 || board[pos] != -1) {
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
				if(pos >= board.length || board[pos] != -1) {
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
				if(pos >= board.length || board[pos] != -1) {
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
				if(pos < 0 || board[pos] != -1) {
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
		
		return sb.toString();
	}
}
