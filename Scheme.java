public class Scheme{

	public int level;

	//public double minTime;
	//public double maxTime;

	public int minCars;
	public int maxCars;


	public int[] limits;

	public Scheme () {
	}

	public boolean handles (int traffic) {
		if(traffic <= this.maxCars && traffic >= this.minCars){
			return true;
		}else{
			return false;
		}
	}

	public double membership (double time) {
		return 0;
	}

	public int getSchemeIndex(){
		return level;
	}

}
