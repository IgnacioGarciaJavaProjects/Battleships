package packkk;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("what");
		Board b = new Board(10, 10);
		Board pb = new Board(10, 10);
		b.autoShips();
		pb.autoShips();
		System.out.println(b.toString());
		b.autoAttack();
		System.out.println(b.toString());
	}

}
