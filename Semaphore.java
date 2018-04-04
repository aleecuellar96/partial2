import java.util.*;

public class Semaphore{

	public ArrayList<Traffic> modes;
	public double time;
	public Traffic currentScheme;
	public int target;
	public int activeLane;

	public Semaphore (){
		modes = new ArrayList<Traffic> ();
	}

	public void addMode(Traffic mode){
		modes.add (mode);
	}

	public void setTime(double time){
		this.time = time;
	}

	public Traffic getTrafficMode(int traffic){
		for (Traffic scheme : modes){
			if (scheme.fit(traffic)){
				return scheme;
			}
		}
		return null;
	}

	public void timeTraffic(int traffic){
		currentScheme= getTrafficMode(traffic);
		double timeFromTraffic = Math.floor((0.078125*traffic)+35);

		double scores[] = new double[modes.size ()];

		double noChange = -1;

		for(int i = 0; i < modes.size (); i++){
			scores[i] = modes.get(i).membership (timeFromTraffic);
			if(i == currentScheme.level){
				noChange = scores[i];
			}
		}
		if(currentScheme.level < target){
			currentScheme = modes.get (modes.size () - 1);
			this.time = (timeFromTraffic + currentScheme.limits[currentScheme.limits.length - 1])/2;
		}else if(currentScheme.level > target){
			currentScheme = getTrafficMode(traffic);
			this.time = (timeFromTraffic + currentScheme.limits[currentScheme.limits.length - 1])/2;
		}else{
			this.time = (timeFromTraffic + currentScheme.limits[currentScheme.limits.length - 1])/2;
		}
	}

	public void alternate(int traffic){
		timeTraffic(traffic);
		if(activeLane == 1){
			activeLane = 0;
		}else{
			activeLane = 1;
		}
	}

}