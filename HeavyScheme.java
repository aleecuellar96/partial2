public class HeavyScheme extends Scheme {

	public HeavyScheme () {
		this.name = "HeavyScheme";
		this.level = 2;
		limits = new int[2];
		limits[0] = 105;
		limits[1] = 130;
		minTime = 105;
		maxTime = 150;
		minCars = 1120;
		maxCars = 1600;
	}

	public double membership (double time) {
		if (time <= limits[0]) {
			return 0;
		} else if (limits[0] < time && time <= limits[1]) {
			return (time - limits[0])/(limits[1] - limits[0]);
		} else if (time > limits[1]) {
			return 1;
		}
		return 0;
	}
}
