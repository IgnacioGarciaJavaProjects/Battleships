package packkk;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("what");
		//Board b = new Board(10, 10);
		PlayerBoard pb = new PlayerBoard(10, 10);
		
		//b.autoShips();
		pb.autoShips();
		System.out.println(pb.toString());
		while(!pb.sll.isEmpty()) {
			//pb.intelAttack();
			pb.intelAttack();
			System.out.println(pb.toString());
		}
		//pbb.autoAttack();
		System.out.println(pb.toString());
	}

}
