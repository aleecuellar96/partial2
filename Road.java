import java.util.*;

public class Road {

	public String direction;

	public boolean semaphoreAsGoal;

	public int[] initialPoint;
	public int[] actualSize;

	public int width;
	public int height;

	public boolean horizontal;


	public ArrayList<Car> cars;


	public int goal;

	public Road (String direction, int x, int y, int width, int height) {

		this.direction = direction;
		cars = new ArrayList<Car> ();
		this.initialPoint = new int[2];
		this.initialPoint[0] = x;
		this.initialPoint[1] = y;

		if (width > height) {
			//width -= 1;
			goal = width;
		} else {
			//height -= 1;
			goal = height;
		}

		this.width = width;
		this.height = height;

		this.actualSize = new int[2];

	}

	public Road (String direction, int x, int y, int width, int height, boolean semaphoreAsGoal) {
		this (direction, x, y, width, height);
		this.semaphoreAsGoal = semaphoreAsGoal;
	}

	// Utility function used to tell which portion of the road is actually the
	// one before the semaphore since only there cars can be added.
	public void setActualSize (int width, int height) {
		this.actualSize[0] = width;
		this.actualSize[1] = height;
		if (semaphoreAsGoal) {
			if (width < height) {
			goal = height;
		} else {
			goal = width;
		}
		}

	}

	public void flow () {

		ArrayList<Car> left = new ArrayList<Car> ();
		for (int i = 0; i < cars.size (); i++) {
			Car car = cars.get (i);

			Car copy = car.copy ();

			System.out.print ("\rMoving Car from [" + car.current.y + ", " + car.current.x + "] (#" + i + " of "  + cars.size () + ") in the " + this.direction + " road");
			Main.world.map[car.current.x][car.current.y].valid = true;
			int status = car.aStar(true);



			System.out.flush();

			if (status == -1) {
				left.add (copy);
				Main.world.map[copy.current.x][copy.current.y].valid = false;
				System.out.print ("\rMoved Car to [" + copy.current.y + ", " + copy.current.x + "] (#" + i + " of "  + cars.size () + ") in the " + this.direction + " road");
			} else if (status == 0) {
				left.add (car);
				Main.world.map[car.current.x][car.current.y].valid = false;
				System.out.print ("\rMoved Car to [" + car.current.y + ", " + car.current.x + "] (#" + i + " of "  + cars.size () + ") in the " + this.direction + " road");
			} else {

			}
		}
		cars = left;
	}

	public void flowFree () {
		/*ArrayList<Car> left = new ArrayList<Car> ();
		int i = 1;
		for (Car car : cars) {
			if (width < height) {
				if (car.current.x > initialPoint[0] + actualSize[0] + 1) {
					boolean ended = car.move (true);
					if (!ended) {
						left.add (car);
					}
				}
			} else {
				/*if (car.current.x > initialPoint[0] + actualSize[0] + 1) {
					boolean ended = car.move (true);
					if (!ended) {
						left.add (car);
					}
				}
			}

		}
		cars = left;*/
	}

	public void spawn (int x, int y) {
		Car car = new Car ();
		car.setStart (x, y);

		if (width < height) {
			car.setGoal (goal, y);
		} else {
			car.setGoal (x, goal);
		}
		cars.add (car);
	}

	// Spawn a car in the road
	public boolean spawn () {

		// Check if the car still fits in the road, this will start filling
		// the road from the first column/row following the size and direction
		// of the road.
		if (cars.size () < actualSize[0] * actualSize[1]) {

			// Check if the road is in vertical or horizontal position since
			// coordinates change from one to other.
			if (width < height) {
				for (int i = initialPoint[1] + actualSize[1] - 1; i >= initialPoint[1]; i--) {
					for (int j = initialPoint[0] + actualSize[0] - 1; j >= initialPoint[0]; j--) {
						Car car = new Car ();
						car.setStart (i, j);

						// Add a goal for the car in the same lane but in the
						// other side of the road
						car.setGoal (goal, j);

						// Check if a car in this position wasn't already added
						if (cars.indexOf (car) < 0) {
							cars.add (car);
							return true;
						}
					}
				}
			} else {

					for (int j = initialPoint[0] + actualSize[0] -1; j >= initialPoint[0]; j--) {
					for (int i = initialPoint[1] + actualSize[1] -1; i >= initialPoint[1]; i--) {
						Car car = new Car ();
						car.setStart (i, j);
						car.setGoal (i, goal);
						if (cars.indexOf (car) < 0) {
							cars.add (car);
							return true;
						}
					}
				}
			}


		}
		return false;
	}

	@Override
	public boolean equals (Object o) {
		Road other = (Road) o;
		return (this.direction.equals(other.direction));
	}

}