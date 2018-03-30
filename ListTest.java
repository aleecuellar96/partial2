/*
	Liga del algoritmo: http://www.growingwiththeweb.com/2012/06/a-pathfinding-algorithm.html
*/

public class ListTest{
	public static void main(String [] args){

		Cell c1 = new Cell(0,0);
			c1.f = 8.0f;
		Cell c2 = new Cell(0,1);
			c2.f = 9.0f;
		Cell c3 = new Cell(1,0);
			c3.f = 0.0f;
		Cell c4 = new Cell(1,1);
			c4.f = 1.0f;
		Cell c5 = new Cell(2,1);

		//Node n1 = new Node(null, c1);
		//Node n2 = new Node(null, c2);
		//Node n3 = new Node(null, c3);
		//Node n4 = new Node(null, c4);

		List list = new List();
		list.append(c1);
		list.append(c2);
		list.append(c3);
		list.append(c4);
		System.out.println(list);
		System.out.println(list.getSize());

		list.delete(c1);
		System.out.println(list);
		System.out.println(list.getSize());

		list.delete(c4);
		System.out.println(list);
		System.out.println(list.getSize());

		list.delete(c3);
		System.out.println(list);
		System.out.println(list.getSize());

		list.delete(c2);
		System.out.println(list);
		System.out.println(list.getSize());
		
		
		//System.out.println("h: " + c1.heuristic(c4));
	}
}
