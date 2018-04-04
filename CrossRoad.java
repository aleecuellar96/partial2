import java.util.*;

public class CrossRoad{
	public Semaphore semaphore;
	public Lane[] lanes;
	public int width;
	public int height;
	public Cell map[][];
	public int index = 0;

	public CrossRoad(){
		createWorld(40 * 2 + 20, 40 * 2 + 20);
		lanes = new Lane[2];
		semaphore = new Semaphore ();
		semaphore.activeLane = 0;
	}

	public void createWorld(int width, int height){
		this.width = height;
		this.height = width;
		map = new Cell[this.height][this.width];
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				Cell cell = new Cell(i, j);
				map[i][j] = cell;
			}
		}
	}

	public void fillLane(int index, Lane lane, int n){
		if(index == 0){
			lanes[index] = lane;
			for(int i = 0; i < this.height; i++){
				for(int j = 0; j < this.width; j++){
					if(j >= lane.startFill[0] && j <= lane.startFill[0] + lane.width && i >= lane.startFill[1] && i <= lane.startFill[1] + lane.height){
						map[i][j].valid = true;
					}
				}
			}
			for(int x = 0; x < n; x++){
				lanes[index].fill();
			}
			index++;
		}else{
			lanes[index] = lane;
			for(int i = 0; i < this.height; i++){
				for(int j = 0; j < this.width; j++){
					if(j >= lane.startFill[0] && j <= lane.startFill[0] + lane.width && i >= lane.startFill[1] && i <= lane.startFill[1] + lane.height){
						map[i][j].valid = true;
					}
				}
			}
			for(int x = 0; x < n; x++){
				lanes[index].fill();
			}
		}
	}

	public Lane getLaneIndex(int index){
		if(index < lanes.length){
			return lanes[index];
		}
		return null;
	}

	public int getTotal(){
		int count = 0;
		for(int i = 0; i < lanes.length; i++){
			count += lanes[i].cars.size();
		}
		return count;
	}

	public int carPosition(int x, int y){
		for(int i = 0; i < lanes.length; i++){
			for(Car car : lanes[i].cars){
				if(car.current.x == x && car.current.y == y){
					return i;
				}
			}
		}
		return -1;
	}

	public String write(){
		String str = "";
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int road = carPosition(i, j);
				if(!map[i][j].valid && road == -1){
					str += " .";
				}else if (road == 0){
					str += " >";
				}else if (road == 1){
					str += " v";
				}else  if (map[i][j].visited){
					str += "  ";
				} else{
					str += "  ";
				}
			}
			str += "\n";
		}
		return str;
	}

}