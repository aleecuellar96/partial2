import java.util.*;

public class Car {

	public double speed;

	private List open_list;
	private List closed_list;
	public Cell current;
	private Cell start;
	private Cell goal;

	public Car () {
		open_list = new List ();
		closed_list = new List ();
	}

	public void setStart (int x, int y) {
		open_list = new List ();
		start = new Cell (x, y);
		start.g = 0;
		open_list.append (start);
		current = start;

	}

	public Car copy () {
		Car clone = new Car ();
		clone.setStart (this.current.x, this.current.y);
		clone.setGoal (this.goal.x, this.goal.y);
		return clone;
	}

	public void setGoal (int x, int y) {
		goal = new Cell (x, y);
		start.f = start.g + start.heuristic (goal);
	}

	public int aStar (boolean repeat) {

		if (open_list.getSize () != 0) {

			current = (Cell) open_list.min ();

			if (current == null || !Main.crossing.map[current.x][current.y].valid) {
				return -1;
			}

			if (current.equals (goal)) {
				return 1;
			}

			open_list.delete (current);
			closed_list.append (current);

			ArrayList<Cell> neighbors = neighbors (current);
			for (Cell neighbor : neighbors) {
				if (!closed_list.contains (neighbor)) {
					neighbor.f = neighbor.g + neighbor.heuristic (goal);
					if (!open_list.contains (neighbor)) {
						Main.crossing.map[neighbor.x][neighbor.y].visited = true;
						open_list.append (neighbor);
					} else {
						Cell openNeighbor = ((Cell) open_list.find (neighbor));
						if (neighbor.g < openNeighbor.g) {
							openNeighbor.g =  neighbor.g;
							openNeighbor.parent =  neighbor.parent;
						}
					}
				}
			}

			if (current == null || open_list.getSize () == 0) {
				return -1;
			} else {
				return 0;
			}
		}
		return -1;
	}

	public static ArrayList<Cell> neighbors(Cell c){
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for (int i = 0; i < Main.crossing.height; i++){
			for (int j = 0; j < Main.crossing.height; j++){
				Cell cell = Main.crossing.map[i][j].copy();
				if (cell.valid){
					if (diagonal (c, i, j)){
						cell.g = c.g + 1.414f;
						cell.parent = c;
					cells.add (cell);
					}else if (notDiagonal (c, i, j)){
						cell.g = c.g + 1;
						cell.parent = c;
						cells.add (cell);
					}

				}
			}
		}
		return cells;
	}

	public static ArrayList<Cell> construct_path(Cell c){
		ArrayList<Cell> path = new ArrayList <Cell>();
		while (c.parent != null ){
			System.out.println (c);
			path.add (c);
			Main.crossing.map[c.x][c.y].path = true;
			c = c.parent;
		}
		return path;
	}

	public static boolean notDiagonal(Cell cell, int x, int y){
		if((cell.x + 1 == x && cell.y == y) || (cell.x == x && cell.y + 1 ==y)
		|| (cell.x - 1 == x && cell.y ==y) || (cell.x == x && cell.y - 1 ==y)){
			return true;
		}else{
			return false;
		}
	}

	public static boolean diagonal(Cell cell, int x, int y){
		if((cell.x + 1 == x && cell.y + 1 == y) || (cell.x + 1 == x && cell.y - 1 ==y)
		|| (cell.x - 1 == x && cell.y + 1 ==y) || (cell.x - 1 == x && cell.y - 1 ==y)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean equals (Object o) {
		Car other = (Car) o;
		return (this.current.equals(other.current));
	}


	@Override
	public String toString () {
		return String.format("[" + this.current.y + "," + this.current.x + "] GOAL: " + this.goal);
	}
}