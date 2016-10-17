package packkk;

public class Main {
	static int[] t = {0,1,2,4,5,7};
	public static void main(String[] args) {
		System.out.println("what");
		
		//Board b = new Board(10, 10);
		PlayerBoard pb = new PlayerBoard(10, 10);
		System.out.println(pb.r.nextInt(1));
		//System.out.println(pb.dicotomicSearch(t, 3, t.length));
		
		
		//b.autoShips();
		pb.autoShips();
		System.out.println(pb.toString());
		System.out.println("longest ship = " + pb.sll.longestAliveShip);
		System.out.println("shortest ship = " + pb.sll.shortestAliveShip);
		while(!pb.sll.isEmpty()) {
			//pb.intelAttack();
			pb.intelAttackDirInvert();
			System.out.println(pb.toString());
			//if(pb.loop > 8) {
				//break;
			//}
		}
		//pbb.autoAttack();
		System.out.println(pb.toString());
	}

}
