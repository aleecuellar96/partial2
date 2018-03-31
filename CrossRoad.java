import java.util.*;

public class CrossRoad extends World {

	public int[] roadSize;

	public Semaphore semaphore;
	public Road[] roads;


	public CrossRoad (int columns, int rows) {
		// Create a world with size enough for the roads and their continuations
		super (columns * 2 + rows, columns * 2 + rows);

		roadSize = new int[2];
		roadSize[0] = columns;
		roadSize[1] = rows;

		roads = new Road[2];
		semaphore = new Semaphore ();

		// Set which road has the green light first
		semaphore.activeRoad = 1;
		semaphore.inactiveRoad = 0;
	}

	// Trace a Road in the current map
	private void traceRoad (Road road) {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				if (j >= road.initialPoint[0] && j <= road.initialPoint[0] + road.width && i >= road.initialPoint[1] && i <= road.initialPoint[1] + road.height) {
					map[i][j].valid = true;
				}
			}
		}
	}

	public void spawn (int road, int x, int y) {
		if (road < roads.length) {
			roads[road].spawn (x, y);
		}
	}

	// Spawn n number of cars in a given Road by it's ID
	public void spawn (int n, int road) {
		if (road < roads.length) {
			for (int i = 0; i < n; i++) {
				roads[road].spawn ();
			}
		}
	}

	// Add a new road to the crossroad with a given ID
	public void setRoad (int index, Road road) {
		if (index >= 0 && index < 2) {
			roads[index] = road;
			traceRoad (road);
		}
	}

	// Return a road by it's id
	public Road getRoad (int index) {
		if (index < roads.length) {
			return roads[index];
		}
		return null;
	}

	// Count the total number of cars in the crossroad
	public int count () {
		int count = 0;
		for (int i = 0; i < roads.length; i++) {
			count += roads[i].cars.size ();
		}
		return count;
	}

	// Check if any of the roads has a car at the given (x, y) point.
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
		String result = ""; //"There are currently " + count () + "Cars in the crossroad\n";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int road = carAt (i, j);
				/*if (road == -1) {
					road = carAt (j, i);
				}*/
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