import java.util.*;

public class Semaphore {

	public double startTime;
	public ArrayList<Scheme> schemes;

	public double time;

	public Scheme currentScheme;

	public int target;


	public int activeRoad;
	public int inactiveRoad;

	public Semaphore () {
		schemes = new ArrayList<Scheme> ();
	}

	public void addScheme (Scheme scheme) { //change name
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
		double temp = Math.floor((0.078125*traffic)+35);
		if (temp < startTime) {
			temp = startTime;
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
	}

	public void alternate (int traffic) {
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