import java.util.*;

public class CrossRoad{

	public int[] roadSize;

	public Semaphore semaphore;
	public Road[] roads;

	public int width;
	public int height;

	public Cell map[][];
	public int index = 0;

	public CrossRoad (int columns, int rows) {
		createWorld(columns * 2 + rows, columns * 2 + rows);
		roadSize = new int[2];
		roadSize[0] = columns;
		roadSize[1] = rows;
		roads = new Road[2];
		semaphore = new Semaphore ();
		semaphore.activeRoad = 1;
	}

	public void createWorld (int width, int height) {
		this.width = height;
		this.height = width;
		map = new Cell[this.height][this.width];
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				Cell cell = new Cell (i, j);
				map[i][j] = cell;
			}
		}
	}

	public void fillLane (int n, int road) {
		for (int x = 0; x < n; x++) {
			roads[road].fill();
		}
	}

	public void setRoad (int index, Road road) {
		if(index == 0){
			roads[index] = road;
			for (int i = 0; i < this.height; i++) {
				for (int j = 0; j < this.width; j++) {
					if (j >= road.initialPoint[0] && j <= road.initialPoint[0] + road.width && i >= road.initialPoint[1] && i <= road.initialPoint[1] + road.height) {
						map[i][j].valid = true;
					}
				}
			}
			index++;
		}else{
			roads[index] = road;
			for (int i = 0; i < this.height; i++) {
				for (int j = 0; j < this.width; j++) {
					if (j >= road.initialPoint[0] && j <= road.initialPoint[0] + road.width && i >= road.initialPoint[1] && i <= road.initialPoint[1] + road.height) {
						map[i][j].valid = true;
					}
				}
			}
		}
	}

	public Road getRoad (int index) {
		if (index < roads.length) {
			return roads[index];
		}
		return null;
	}

	public int getTotal () {
		int count = 0;
		for (int i = 0; i < roads.length; i++) {
			count += roads[i].cars.size ();
		}
		return count;
	}

	public int carAt (int x, int y) {
		for (int i = 0; i < roads.length; i++) {
			if (roads[i] != null) {
				for (Car car : roads[i].cars) {
					if (car.current.x == x && car.current.y == y) {
						return i;
					}
				}
			}

		}
		return -1;
	}

	public String toFileString () {
		String result = "";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int road = carAt (i, j);
				if (road == 1) {
					result += "v";
				} else if (road == 0) {
					result += ">";
				}else if (!map[i][j].valid && road == -1) {
					result += " ";
				} else if (!map[i][j].valid) {
					result += " ";
				}else  if (map[i][j].visited) {
					result += ".";
				} else {

					result += ".";
				}
			}
			result += "\n";
		}
		return result;
	}


	public String toString () {
		String result = ""; 
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int road = carAt (i, j);

				if (map[i][j].path) {
					result += " \u001B[36m◎\u001B[0m ";
				} else if (road == 1) {
					result += " ▼ ";
				} else if (road == 0) {
					result += " ▶ ";
				}else if (!map[i][j].valid && road == -1) {
					result += "   ";
				} else if (!map[i][j].valid) {
					result += "   ";
				}else  if (map[i][j].visited) {
					result += " \u001B[35m□\u001B[0m ";
				} else {

					result += " □ ";
				}
			}
			result += "\n";
		}
		return result;
	}

}