import java.util.*;

public class World {

	public int width;
	public int height;

	public int walls;
	public boolean limited;

	public Cell map[][];
	public Cell start;
	public Cell goal;

	public World (int width, int height) {
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


	public World (int width, int height, boolean limit) {
		this.width = height;
		this.height = width;
		this.limited = limit;

		int numberOfWalls = 600 + ((int) Math.floor(Math.random() * 201));

		map = new Cell[height][width];


		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Cell cell = new Cell (i, j);
				cell.valid = true;
				map[i][j] = cell;
			}
		}
		if (limit) {
			while (walls < numberOfWalls) {
				set (((int) Math.floor(Math.random() * height)), ((int) Math.floor(Math.random() * width)), randomRoom(((int) Math.floor(Math.random() * 15))), limit);
			}
		} else {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (map[i][j].valid) {
						set (i, j, randomRoom(((int) Math.floor(Math.random() * 15))), limit);
					}

				}
			}
		}


		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Cell cell = map[i][j];
				if (start == null && cell.valid && !limit) {
					start = map[i][j];
				}

				if (start == null && cell.valid && limit && j == 0) {
					start = map[i][j];
				}


				if (cell.valid && !limit) {
					goal = map[i][j];
				}

				if (cell.valid && limit && j == 49) {
					goal = map[i][j];
				}
			}
		}

	}

	public World (int template[][]) {

		this.width = template[0].length;
		this.height = template.length;
		this.limited = false;

		map = new Cell[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Cell cell = new Cell (i, j);
				cell.valid = template[i][j] == 0;
				map[i][j] = cell;
			}
		}
	}

	public void set (int x, int y, ArrayList<int[]> room, boolean limit) {
		for (int[] position : room) {
			if (limit) {
				if (x + position[0] >= 0 && x + position[0] < height && y + position[1] >= 0 && y + position[1] < width && walls < 800) {
					this.map[x][y].valid = false;
					walls++;
				}
			} else {
				if (x + position[0] >= 0 && x + position[0] < height && y + position[1] >= 0 && y + position[1] < width) {
					this.map[x][y].valid = false;
					walls++;
				}
			}

		}
	}

	public static ArrayList<int[]> randomRoom (int seed) {
		ArrayList<int[]> positions = new ArrayList<int[]> ();
		switch (seed) {
			case 0:
				positions.add (new int[] {1, 1});
				positions.add (new int[]{-1, 1});
				positions.add (new int[]{1, -1});
				positions.add (new int[]{-1, -1});
				positions.add (new int[]{0, 1});
				positions.add (new int[]{1, 0});
				positions.add (new int[]{0, -1});
				positions.add (new int[]{-1, 0});
				positions.add (new int[]{0, 0});
				break;

			case 1:
				positions.add (new int[]{1, 1});
				positions.add (new int[]{-1, 1});
				positions.add (new int[]{1, -1});
				positions.add (new int[]{-1, -1});
				break;

			case 2:
				positions.add (new int[]{0, 1});
				positions.add (new int[]{1, 0});
				positions.add (new int[]{0, -1});
				positions.add (new int[]{-1, 0});
				break;

			case 3:
				positions.add (new int[] {2, 2});
				positions.add (new int[]{-2, 2});
				positions.add (new int[]{2, -2});
				positions.add (new int[]{-2, -2});
				positions.add (new int[]{0, 3});
				positions.add (new int[]{3, 0});
				positions.add (new int[]{0, -3});
				positions.add (new int[]{-3, 0});
				break;

			case 4:
				positions.add (new int[] {0, 1});
				positions.add (new int[] {0, 3});
				positions.add (new int[] {0, 5});
				positions.add (new int[] {0, 7});
				break;

			case 5:
				positions.add (new int[] {0, -2});
				positions.add (new int[] {0, -4});
				positions.add (new int[] {0, -6});
				positions.add (new int[] {0, -8});
				break;

			case 6:
				positions.add (new int[] {1, 0});
				positions.add (new int[] {3, 0});
				positions.add (new int[] {5, 0});
				positions.add (new int[] {7, 0});
				break;

			case 7:
				positions.add (new int[] {-2, 0});
				positions.add (new int[] {-4, 0});
				positions.add (new int[] {-6, 0});
				positions.add (new int[] {-8, 0});
				break;
		}

		return positions;
	}

	@Override
	public String toString () {
		String result = String.format("Visualization of the %dx%d world.\n\n", width, height);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (map[i][j].path) {
					result += " \u001B[36m◎\u001B[0m ";
				} else if (!map[i][j].valid) {
					result += " ■ ";
				} else  if (map[i][j].visited) {
					result += " \u001B[35m□\u001B[0m ";
				} else {
					result += " □ ";
				}
			}
			result += "\n";
		}
		if (this.limited) {
			result += String.format("\nIsn't this kind of boring? Set the random generation limitations to false in the main() method for more fun! :D\n\n");
		}
		return result;
	}
}