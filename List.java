public class List {

	Node root;

	public List(){
		
	}

	public void append(Cell n){
		Node newNode = new Node(n);
		if(root !=null){
			Node current = root;
			while(current.next != null){
				current = current.next;
			}
			current.next = newNode;
		}else{
			root = newNode;
		}
	}

	public int getSize(){
		int count = 1; 
		Node current = root;
		if(root != null){
			while(current.next != null){
			current = current.next;
			count++;
			}
		}else{
			count = 0;
		}
		
		return count;
	}

	public Cell deleteFirst(){
		if (root != null) {
			Node first = root;
			root = root.next;
			return (Cell) first.payload;
		}
		return null;
	}

	public Cell delete(Cell n){
		Node current = root;
		Node prev= current;
		if(current.payload.equals(n)){
			root = current.next;
		}else{
			if(getSize() != 1){
				while (current != null) {
				if (current.payload.equals(n)) {
					prev.next = current.next;
					return (Cell) current.payload;
				}
				prev = current;
				current = current.next;
				}
				prev.next = current.next;
			}else{
				root = null;
			}
		}
		
		return null;
	}

	public Cell min(){
		float min = root.payload.f;
		Node minNode = root;
		Node current = root;
		while(current != null){
			if(current.payload.f < min){
				min = current.payload.f;
				minNode = current;
			}
			current = current.next;
		}
		return (Cell) minNode.payload;
	}

	@Override
	public String toString(){
		String result = "";
		Node current = root;
		result += current.toString () + "\n";
		while (current.next != null) {
			current = current.next;
			result += current.toString () + "\n";
		}
		return result;
	}

	public Cell find(Cell n){
		Node current = root;
		while (current != null) {
			if (current.payload.equals(n)) {
				return (Cell) current.payload;
			}
			current = current.next;
		}
		return null;
	}

	public boolean contains(Cell n){
		Node current = root;
		while (current != null) {
			if (current.payload.equals(n)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}
}
