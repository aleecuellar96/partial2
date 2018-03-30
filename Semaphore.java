import java.util.*;

public class Semaphore {

	public double minTime;
	public ArrayList<Scheme> schemes;

	public double time;
	public double speed;

	public Scheme currentScheme;

	public int target;


	public int activeRoad;
	public int inactiveRoad;

	public Semaphore () {
		schemes = new ArrayList<Scheme> ();
	}

	public void addScheme (Scheme scheme) {
		schemes.add (scheme);
	}

	public void setTime (double time) {
		this.time = time;
	}

	public Scheme schemeFor (int traffic) {
		for (Scheme scheme : schemes) {
			if (scheme.handles (traffic)) {
				return scheme;
			}
		}
		return null;
	}

	// Function to map any given amount of traffic to time
	public double mapTrafficToTime (int traffic) {
		Scheme scheme = schemeFor (traffic);
		double temp = Math.floor(traffic * (scheme.maxTime / scheme.maxCars));
		if (temp < minTime) {
			temp = minTime;
		}
		return temp;
	}

	// Function to set the time of the semaphore based on a given amount of
	// traffic
	public void setTimeFromTraffic (int traffic) {

		// If there is no current scheme, pick the first one from the list
		if (currentScheme == null) {
			currentScheme = schemeFor (traffic);
		}

		// Translate traffic to time
		double timeFromTraffic = mapTrafficToTime(traffic);


		double scores[] = new double[schemes.size ()];

		double noChange = -1;

		for (int i = 0; i < schemes.size (); i++) {
			scores[i] = schemes.get(i).membership (timeFromTraffic);
			if (i == currentScheme.level) {
				noChange = scores[i];
			}
		}

		// Check what rules apply
		if (currentScheme.level < target) {
			currentScheme = schemes.get (schemes.size () - 1);
		} else if (currentScheme.level > target) {
			currentScheme = schemeFor (traffic);
		} else {
			this.time = currentScheme.centerOfMass((int)timeFromTraffic);
		}

		this.speed =  (currentScheme.maxTime / currentScheme.maxCars);


		/*for (int i = 0; i < scores.length; i++) {
			if (scores[i] > 0 && schemes.get(i).level != currentScheme.level) {
				System.out.println (schemes.get(i).centerOfMass ((int) timeFromTraffic, scores[i]));
			} else if (scores[i] == 1 && schemes.get(i).level == currentScheme.level) {
					System.out.println (schemes.get(i).centerOfMass ((int) timeFromTraffic, scores[i]));
			}
		}

		// Check what scheme the given traffic belongs to and change if necessary
		Scheme higher = currentScheme;
		double score = higher.membership (timeFromTraffic);

	for (Scheme scheme : schemes) {
			double tempScore = scheme.membership (timeFromTraffic);
			if (tempScore > score) {
				higher = scheme;
				score = tempScore;
			}
		}


		// If the scheme with the highest belonging score is not the same
		// as the current scheme, change them.
		if  (!currentScheme.name.equals (higher.name)) {
			currentScheme = higher;
			this.speed =  (higher.maxTime / higher.maxCars);
		}
		this.time = currentScheme.centerOfMass((int)timeFromTraffic);*/
	}

	// Swap from one lane to the other.
	public void swap (int traffic) {
		setTimeFromTraffic (traffic);
		if (activeRoad == 0) {
			activeRoad = 1;
			inactiveRoad = 0;
		} else {
			activeRoad = 0;
			inactiveRoad = 1;
		}
	}

}