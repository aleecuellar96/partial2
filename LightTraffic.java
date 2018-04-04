public class LightTraffic extends Traffic{

	public LightTraffic(){
		this.level = 0;
		limits = new int[2];
		limits[0] = 74;
		limits[1] = 82;
		minCars = 0;
		maxCars = 600;
	}

	public double membership(double time){
		if(limits[0] < time && time <= limits[1]){
			return (limits[1] - time) / (limits[1] - limits[0]);
		}else if(time <= limits[0]){
			return 1;
		}else{
			return 0;
		}
	}
}
