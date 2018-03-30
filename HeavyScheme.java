public class HeavyScheme extends Scheme {

	public HeavyScheme (String name, int level) {
		super (name, level);
	}

	@Override
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
