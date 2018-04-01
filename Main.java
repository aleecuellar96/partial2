import java.util.*;

public class Main {

	// Keep only a copy of the whole world
	public static CrossRoad crossing;

	public static void main (String args[]) {
		File.create ("results.txt");
		//initialize world
		crossing = new CrossRoad (40, 20);
		Road north_south = new Road ("North - South", 40, 0, 19, 99);
		north_south.setActualSize (20, 40);
		Road west_east = new Road ("West - East", 0, 40, 99, 19);
		west_east.setActualSize (40, 20);
		crossing.setRoad (0, west_east);
		crossing.setRoad (1, north_south);
		crossing.spawn (517, 0);
		crossing.spawn (202, 1);

		//semaphore
		LightScheme light = new LightScheme ();
		MediumScheme medium = new MediumScheme ();
		HeavyScheme heavy = new HeavyScheme ();
		crossing.semaphore.addScheme (light);
		crossing.semaphore.addScheme (medium);
		crossing.semaphore.addScheme (heavy);
		crossing.semaphore.startTime = 35;
		crossing.semaphore.target = 0;
		crossing.semaphore.setTimeFromTraffic (crossing.count ());

		// Print the world conditions at the start
		System.out.println (currentState (true));

		// Make the cars move
		flow ();

		// Show a step by step
		//flow (true, 550);

		// Print the world conditions after car flow
		System.out.println (crossing);
	}

	/*public static void initializeWorld () {

		// Create a new crossing world with Streets of 40x20 places.
		CrossRoad crossing = new CrossRoad (40, 20);

		// Create a new Road that goes from (40, 0) to (60, 99)
		// Those two coordinates form a rectangle, the first coordinate
		// being the left-top corner and the second one the right-bottom one.
		Road north_south = new Road ("North - South", 40, 0, 19, 99);
		north_south.setActualSize (20, 40);

		// Create a new Road that goes from (0, 40) to (99, 60)
		Road west_east = new Road ("West - East", 0, 40, 99, 19);
		west_east.setActualSize (40, 20);

		// Add each road to the crossroad with an specific ID
		crossing.setRoad (0, west_east);
		crossing.setRoad (1, north_south);

		// Add cars to the West-East Road
		crossing.spawn (517, 0);

		// Add cars to the North-South Road
		crossing.spawn (202, 1);

		world = crossing;
	}*/

	/*public static void semaphoreSettings () {
		CrossRoad crossing = (CrossRoad) world;

		// Create the Light Scheme that the Semaphore will use and set all of
		// it's limits. From the function limits (limits) to the time and cars
		// it handles
		LightScheme light = new LightScheme ();

		// Create the Medium Scheme that the Semaphore will use and set all of
		// it's limits. From the function limits (limits) to the time and cars
		// it handles
		MediumScheme medium = new MediumScheme ();

		// Create the Heavy Scheme that the Semaphore will use and set all of
		// it's limits. From the function limits (limits) to the time and cars
		// it handles
		HeavyScheme heavy = new HeavyScheme ();

		// Add the Schemes to the Semaphore of the crossroad
		crossing.semaphore.addScheme (light);
		crossing.semaphore.addScheme (medium);
		crossing.semaphore.addScheme (heavy);
		crossing.semaphore.startTime = 35;

		crossing.semaphore.target = 0;

		// Get how much time the semaphore is using based on how many cars there
		// are right now
		crossing.semaphore.setTimeFromTraffic (crossing.count ());

		//crossing.semaphore.time = 10;
		world = crossing;
	}*/

	public static String currentState (boolean unicodeMap) {
		CrossRoad crossing2 = (CrossRoad) crossing;
		String state = "";

		state += "\n==========================================================\n";
		state += String.format ("SEMAPHORE CURRENTLY GREEN FOR LANE %s\n", crossing2.getRoad(crossing.semaphore.activeRoad).direction);
		state += String.format ("SEMAPHORE DURATION: %f\n", crossing2.semaphore.time);
		state += String.format ("SEMAPHORE SCHEME: %s\n", crossing2.semaphore.currentScheme.name);
		state += String.format ("TOTAL CARS LEFT: %d\n", crossing2.count());
		for (Road road : crossing2.roads) {
			state += String.format ("CARS IN ROAD %s:  %d\n", road.direction, road.cars.size ());
		}
		if (unicodeMap) {
			state += crossing2;
		} else {
			state += crossing2.toFileString ();
		}
		state += "\n==========================================================\n";
		return state;
	}

	// Make the cars flow, the first boolean parameter stablishes if it should
	// be slowed down (true) or if it should run at full speed (false).
	// For debugging purposes it is best to use false. The second parameter
	// stablishes how many steps should be run when the speed was slowed down.
	public static void flow (boolean byStep, int step) {
		CrossRoad crossing2 = (CrossRoad) crossing;
		double timeCounter = 0;
		int counter = 0;
		double totalTime = 0;
		while (crossing2.count () > 0) {

			if (timeCounter >= crossing2.semaphore.time) {
				crossing2.semaphore.swap (crossing2.count ());

				timeCounter = 0;
				counter++;
				System.out.println (currentState (true));
				File.write (currentState (false), "results.txt");
			}
			crossing2.getRoad(crossing2.semaphore.activeRoad).flow ();

			if (byStep) {
				timeCounter += crossing2.semaphore.time;
				if (counter == step) {
					break;
				}
			} else {
				timeCounter += 1;
			}
			totalTime += 1;
			System.out.flush();

		}
		System.out.println ("\n==========================================================");
		System.out.println ("\nAlgorithm Ended");
		System.out.println ("\n==========================================================");
		System.out.format ("It took %f seconds (%f minutes) and there are %d cars left\n", totalTime, totalTime/60, crossing2.count ());
	}

	public static void flow () {
		flow (false, 0);
	}
}
