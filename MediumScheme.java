public class MediumScheme extends Scheme {

	public MediumScheme () {
		this.level = 1;
		limits = new int[4];
		limits[0] = 74;
		limits[1] = 82;
		limits[2] = 113;
		limits[3] = 121;
		minCars = 500;
		maxCars = 1100;
	}

	public double membership (double time) {
		if (time > limits[0] && time <= limits[1]) {
			return (time - limits[0]) / (limits[1] - limits[0]);
		} else if (time > limits[1]  && time < limits[2]) {
			return 1;
		} else if (time > limits[1] && time < limits[3]) {
			return (limits[3] - time) / (limits[3] - limits[2]);
		}else{
			return 0;
		}
	}
}
