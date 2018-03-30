public class MediumScheme extends Scheme {

	public MediumScheme (String name, int level) {
		super (name, level);
	}

	@Override
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
