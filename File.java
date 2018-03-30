import java.util.*;
import java.io.*;

public class File {

    public static ArrayList<String> getAllLines(String file){
        ArrayList<String> lines = new ArrayList<String>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        }catch(Exception e){
            System.out.println("There was an error while reading the file\n" + e);
        }
        return lines;
    }

	public static void create (String file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.close();
        }catch(Exception e){
            System.out.println("There was an error while creating the file\n" + e);
        }
    }

    public static void write(String contents, String file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(contents);
            writer.close();
        }catch(Exception e){
            System.out.println("There was an error while writing the file\n" + e);
        }
    }


}