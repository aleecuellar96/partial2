//ya
public class Node{

	public Node next;

	public Cell payload;

	public Node (Cell payload) {
		this.payload = payload;
	}

	@Override
	public boolean equals(Object o){
		Node other = (Node) o;
		return this.payload == other.payload;
	}

	public String toString(){
		return "N " + payload.toString() + "\n";
	}

}