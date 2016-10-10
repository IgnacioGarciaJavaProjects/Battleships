package packkk;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("what");
		Board b = new Board(10, 10);
		b.autoShips();
		System.out.println(b.toString());
	}

}
