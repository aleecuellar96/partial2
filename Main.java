import java.util.*;

public class Main {

	public static CrossRoad crossing;

	public static void main (String args[]) {
		File.create ("results.txt");

		crossing = new CrossRoad (40, 20);
		Road north_south = new Road ("North - South", 40, 0, 19, 99);
		north_south.setActualSize (20, 40);
		Road west_east = new Road ("West - East", 0, 40, 99, 19);
		west_east.setActualSize (40, 20);
		crossing.setRoad (0, west_east);
		crossing.setRoad (1, north_south);
		crossing.fillLane (305, 0);
		crossing.fillLane(212, 1);

		LightScheme light = new LightScheme ();
		MediumScheme medium = new MediumScheme ();
		HeavyScheme heavy = new HeavyScheme ();
		crossing.semaphore.addScheme (light);
		crossing.semaphore.addScheme (medium);
		crossing.semaphore.addScheme (heavy);
		crossing.semaphore.target = 0;
		crossing.semaphore.setTimeFromTraffic (crossing.getTotal());

		System.out.println (currentState (true));
		flow ();
		System.out.println (crossing);
	}

	public static String currentState (boolean unicodeMap) {
		CrossRoad crossing2 = (CrossRoad) crossing;
		String state = "";

		state += "\n==========================================================\n";
		state += String.format ("SEMAPHORE CURRENTLY GREEN FOR LANE %s\n", crossing2.getRoad(crossing.semaphore.activeRoad).direction);
		state += String.format ("SEMAPHORE DURATION: %f\n", crossing2.semaphore.time);
		state += String.format ("SEMAPHORE SCHEME: %s\n", crossing2.semaphore.currentScheme.level);
		state += String.format ("TOTAL CARS LEFT: %d\n", crossing2.getTotal());
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
	// stablishes how many gets should be run when the speed was slowed down.
	public static void flow () {
		CrossRoad crossing2 = (CrossRoad) crossing;
		double timeCounter = 0;
		int counter = 0;
		double totalTime = 0;
		File.write (currentState (false), "results.txt");
		while (crossing2.getTotal () > 0) {

			if (timeCounter >= crossing2.semaphore.time) {
				crossing2.semaphore.alternate (crossing2.getTotal());

				timeCounter = 0;
				counter++;
				System.out.println (currentState (true));
				File.write (currentState (false), "results.txt");
			}
			crossing2.getRoad(crossing2.semaphore.activeRoad).flow ();

			
			timeCounter += 1;
			
			totalTime += 1;
			System.out.flush();

		}
		System.out.println ("\n==========================================================");
		System.out.println ("\nAlgorithm Ended");
		System.out.println ("\n==========================================================");
		System.out.format ("It took %f seconds (%f minutes) and there are %d cars left\n", totalTime, totalTime/60, crossing2.getTotal());
		File.write (currentState (false), "results.txt");
	}

}
