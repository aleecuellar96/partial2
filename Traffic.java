public class Traffic{

	public int level;
	public int minCars;
	public int maxCars;
	public int[] limits;

	public Traffic(){
	}

	public boolean fit(int traffic){
		if(traffic <= this.maxCars && traffic >= this.minCars){
			return true;
		}else{
			return false;
		}
	}

	public double membership(double time){
		return 0;
	}

	public int getTrafficIndex(){
		return level;
	}

}
