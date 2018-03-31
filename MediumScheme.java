public class MediumScheme extends Scheme {

	public MediumScheme () {
		this.name = "MediumScheme";
		this.level = 1;
		limits = new int[4];
		limits[0] = 55;
		limits[1] = 80;
		limits[2] = 105;
		limits[3] = 130;
		minTime = 55;
		maxTime = 130;
		minCars = 586;
		maxCars = 1386;
	}

	public double membership (double time) {
		if (time <= limits[0] || time >= limits[3]) {
			return 0;
		} else if (time > limits[0] && time <= limits[1]) {
			return (time - limits[0]) / (limits[1] - limits[0]);
		} else if (time > limits[1]  && time < limits[2]) {
			return 1;
		} else if (time > limits[1] && time < limits[3]) {
			return (limits[3] - time) / (limits[3] - limits[2]);
		}
		return 0;
	}
}
