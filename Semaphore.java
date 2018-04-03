import java.util.*;
import javax.swing.JOptionPane;

public class Semaphore {

	public ArrayList<Scheme> schemes;

	public double time;

	public Scheme currentScheme;

	public int target;


	public int activeRoad;

	public Semaphore () {
		schemes = new ArrayList<Scheme> ();
	}

	public void addScheme (Scheme scheme) {
		schemes.add (scheme);
	}

	public void setTime (double time) {
		this.time = time;
	}

	public Scheme getTrafficMode(int traffic) {
		for (Scheme scheme : schemes) {
			if (scheme.handles (traffic)) {
				return scheme;
			}
		}
		return null;
	}

	public void setTimeFromTraffic (int traffic) {

		currentScheme= getTrafficMode(traffic);
		double timeFromTraffic = Math.floor((0.078125*traffic)+35);

		double scores[] = new double[schemes.size ()];

		double noChange = -1;

		for (int i = 0; i < schemes.size (); i++) {
			scores[i] = schemes.get(i).membership (timeFromTraffic);
			if (i == currentScheme.level) {
				noChange = scores[i];
			}
		}

		if (currentScheme.level < target) {
			currentScheme = schemes.get (schemes.size () - 1);
		} else if (currentScheme.level > target) {
			currentScheme = getTrafficMode(traffic);
			this.time = (timeFromTraffic + currentScheme.limits[currentScheme.limits.length - 1])/2;
		} else {
			this.time = (timeFromTraffic + currentScheme.limits[currentScheme.limits.length - 1])/2;
		}
	}

	public void alternate (int traffic) {
		setTimeFromTraffic (traffic);
		if (activeRoad == 0) {
			activeRoad = 1;
		} else {
			activeRoad = 0;
		}
	}

}