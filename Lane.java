import java.util.*;

public class Lane{

	public String direction;
	public int[] size;
	public int[] startFill;
	public int width;
	public int height;
	public ArrayList<Car> cars;
	public boolean goal1;
	public int goal;

	public Lane(String direction, int width, int height, int x, int y){
		this.direction = direction;
		cars = new ArrayList<Car>();
		this.startFill = new int[2];
		this.startFill[0] = x;
		this.startFill[1] = y;

		if(width > height){
			goal = width;
		}else{
			goal = height;
		}

		this.width = width;
		this.height = height;
		this.size = new int[2];

		if(x == 40){
			this.size[0] = 20;
			this.size[1] = 40;
			if (goal1) {
				goal = height;
			}
		}else{
			this.size[0] = 40;
			this.size[1] = 20;
			if (goal1) {
				goal = width;	
			}
		}
	}

	public void move(){
		ArrayList<Car> left = new ArrayList<Car>();
		for (int i = 0; i < cars.size(); i++) {
			Car car = cars.get(i);
			Car copy = car.copy();
			Main.crossing.map[car.current.x][car.current.y].valid = true;
			int status = car.aStar();

			if(status == -1){
				left.add(copy);
				Main.crossing.map[copy.current.x][copy.current.y].valid = false;
			}else if(status == 0){
				left.add (car);
				Main.crossing.map[car.current.x][car.current.y].valid = false;	
			}
		}
		cars = left;
	}

	public boolean fill(){
		if (width > height){
			for(int j = startFill[0] + size[0] -1; j >= startFill[0]; j--){
				for(int i = startFill[1] + size[1] -1; i >= startFill[1]; i--){
					Car car = new Car();
					car.setStart(i, j);
					car.setGoal(i, goal);
					if(cars.indexOf (car) < 0){
						cars.add(car);
						return true;
					}
				}
			}
		} else {
			for(int i = startFill[1] + size[1] - 1; i >= startFill[1]; i--){
				for(int j = startFill[0] + size[0] - 1; j >= startFill[0]; j--){
					Car car = new Car();
					car.setStart(i, j);
					car.setGoal(goal, j);
					if (cars.indexOf(car) < 0) {
						cars.add(car);
						return true;
					}
				}
			}
		}
		return false;
	}

}