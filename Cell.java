public class Cell implements Comparable<Cell> {

	public int x;
	public int y;
	public Cell parent;
	public float f;
	public float g;
	public float h;
	public boolean path;
	public boolean visited;
	public boolean valid;

	public Cell(int x, int y){
		this.x = x;
		this.y = y;
		parent = null;
		f = 0;
		g = 0;
		h = 0;
	}

	public float heuristic(Cell other){ 
		float d_max = Math.max(Math.abs(this.x-other.x), Math.abs(this.y-other.y));
		float d_min = Math.min(Math.abs(this.x-other.x), Math.abs(this.y-other.y));
		float c_n=1.0f;//non diagonal
		float c_d=1.414f; //diagonal
		float h= (c_d*d_min) + (c_n*(d_max-d_min));
		return (float) h;
	}

	public Cell copy(){
		Cell cell = new Cell (x, y);
		cell.f = f;
		cell.g = g;
		cell.h = h;
		cell.valid = valid;
		cell.visited = visited;

		return cell;
	}

	@Override
	public boolean equals(Object o){
		Cell other = (Cell) o;
		return this.x == other.x && this.y == other.y;
	}

	@Override
	public int compareTo(Cell other){
		if(this.f > other.f){
			return 1;
		}else if (this.f < other.f){
			return -1;
		}else{
			return 0;
		}
	}

	public String toString(){
		return "[" + x + " , " + y + "] "+ " f: " + f + " g: " + g + " h: " + h;
	}

}
