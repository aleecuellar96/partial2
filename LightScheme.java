public class LightScheme extends Scheme {

	public LightScheme () {
		this.level = 0;
		limits = new int[2];
		limits[0] = 55;
		limits[1] = 80;
		minTime = 35;
		maxTime = 80;
		minCars = 0;
		maxCars = 853;
	}

	public double membership (double time) {
		if(limits[0] < time && time <= limits[1]){
			return (limits[1] - time) / (limits[1] - limits[0]);
		}else if(time <= limits[0]){
			return 1;
		}else{
			return 0;
		}
	}
}
