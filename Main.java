import java.util.*;
import java.io.*;

public class Main {

	public static CrossRoad crossing;

	public static void main (String args[]) {
		crossing = new CrossRoad();
		Lane north_south = new Lane("North-South", 19, 99, 40, 0);
		Lane west_east = new Lane("West-East", 99, 19, 0, 40);
		crossing.fillLane(0, west_east, 305);
		crossing.fillLane(1, north_south, 412);
		LightTraffic light = new LightTraffic();
		MediumTraffic medium = new MediumTraffic();
		HeavyTraffic heavy = new HeavyTraffic();
		crossing.semaphore.addMode(light);
		crossing.semaphore.addMode(medium);
		crossing.semaphore.addMode(heavy);
		crossing.semaphore.target = 0;
		crossing.semaphore.timeTraffic(crossing.getTotal());

		double time = 0;
		double totalTime = 0;
		int counter = 0;
		write(getCurrent(),"fuzzy.txt");
		while(crossing.getTotal() > 0){
			if(time >= crossing.semaphore.time){
				crossing.semaphore.alternate(crossing.getTotal());
				time = 0;
				counter++;
				write(getCurrent(), "fuzzy.txt");
			}
			crossing.getLaneIndex(crossing.semaphore.activeLane).move();
			time += 1;
			totalTime += 1;
		}
		System.out.println("The result is in the file: fuzzy.txt");
		write(getCurrent(), "fuzzy.txt");
	}

	public static String getCurrent(){
		String str = "";
		str += "\n";
		str += "For Lane " + crossing.getLaneIndex(crossing.semaphore.activeLane).direction +" the time is: " + crossing.semaphore.time + "\n";
		str += "Semaphore mode: " + crossing.semaphore.currentScheme.level + "\n";
		str += "Total cars: " + crossing.getTotal() + "\n";
		for (Lane lane: crossing.lanes) {
			str += "Cars in " + lane.direction + " : " + lane.cars.size() + "\n";
		}
		str += "\n";
		str += crossing.write();
		return str;
	}

    public static void write(String data, String file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(data);
            writer.close();
        }catch(Exception e){
            System.out.println("error" + e);
        }
    }

}
