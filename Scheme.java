public class Scheme{

	public String name; //podria quitar esta
	public int level;

	public double minTime;
	public double maxTime;

	public int minCars;
	public int maxCars;


	public int[] limits;

	public Scheme () {
	}

	public boolean handles (int traffic) {
		return (traffic >= this.minCars && traffic <= this.maxCars);
	}

	public double membership (double time) {
		return 0;
	}

	public double centerOfMass (int lowerLimit) {
		int upperLimit = limits[limits.length - 1];
		double sum = 0;
		for (int i = lowerLimit; i <= upperLimit; i++) {
			sum += membership (i);
		}
		return lowerLimit + sum/2;
	}

	public double centerOfMass (int lowerLimit, double ylimit) {
	int upperLimit = limits[limits.length - 1];
		double sum = 0;
		for (int i = lowerLimit; i <= upperLimit; i++) {
			double score = membership (i);
			if (score <= ylimit) {
				sum += score;
			} else {
				sum += ylimit;
			}

		}
		return lowerLimit + sum/2;
	}

}
