import java.util.*;

public class Car {

	public double speed;

	private List openList;
	private List closedList;

	private Cell start;
	private Cell goal;
	public Cell current;

	public Car () {
		openList = new List ();
		closedList = new List ();
	}

	public void setStart (int x, int y) {
		openList = new List ();
		start = new Cell (x, y);
		start.g = 0;
		openList.append (start);
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


	// Modified version of the A* Algorithm
	// Returns -1 when no possible route was found
	// Returns 1 when it has reached it's destination
	// Returns 0 when it has moved and there are still moves left
	public int move (boolean repeat) {

		if (openList.getSize () != 0) {

			current = (Cell) openList.min ();

			if (current == null) {
				return -1;
			}

			if (!Main.world.map[current.x][current.y].valid) {
				return -1;
			}

			if (current.equals (goal)) {
				return 1;
			}

			openList.delete (current);
			closedList.append (current);

			ArrayList<Cell> neighbors = neighbors (current);
			for (Cell neighbor : neighbors) {

				if (!closedList.contains (neighbor)) {
					neighbor.f = neighbor.g + neighbor.heuristic (goal);
					if (!openList.contains (neighbor)) {
						Main.world.map[neighbor.x][neighbor.y].visited = true;
						openList.append (neighbor);
					} else {

						Cell openNeighbor = ((Cell) openList.find (neighbor));

						if (neighbor.g < openNeighbor.g) {
							openNeighbor.g =  neighbor.g;
							openNeighbor.parent =  neighbor.parent;
						}
					}
				}
			}

			if (current == null || openList.getSize () == 0) {
				return -1;
			} else {
				return 0;
			}

		}
		return -1;
	}



	public static ArrayList<Cell> neighbors (Cell root) {
		ArrayList<Cell> cells = new ArrayList<Cell> ();

		for (int i = 0; i < Main.world.height; i++) {
			for (int j = 0; j < Main.world.width; j++) {
				Cell cell = Main.world.map[i][j].copy ();
				if (cell.valid && !cells.contains (cell)) {
					if (diagonal (root,i, j)) {
						cell.g = root.g + 1.414f;
						cell.parent = root;
						cells.add (cell);
					} else if (notDiagonal(root,i, j)) {
						cell.g = root.g + 1.0f;
						cell.parent = root;
						cells.add (cell);
					}
				}
			}
		}
		return cells;
	}

	public static ArrayList<Cell> constructPath (Cell cell) {
		ArrayList<Cell> path = new
		ArrayList <Cell> ();
		while (cell.parent != null ) {
			System.out.println (cell);
			path.add (cell);
			Main.world.map[cell.x][cell.y].path = true;
			cell = cell.parent;
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
		return String.format("[%d, %d] Goal: %s", this.current.y, this.current.x, this.goal);
	}
}