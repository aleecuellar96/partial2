public class LightScheme extends Scheme {

	public LightScheme (String name, int level) {
		super (name, level);
	}

	@Override
	public double membership (double time) {
		if (time <= limits[0]) {
			return 1;
		} else if (limits[0] < time && time <= limits[1]) {
			return (limits[1] - time) / (limits[1] - limits[0]);
		} else if (time > limits[1]) {
			return 0;
		}
		return 0;
	}
}
