package mapdb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class WriteToFile {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {

            System.out.println(System.getProperty("user.dir"));
            File file = new File("/home/pfg/NetBeansProjects/MapDB/src/mapdb/test.txt");

            if (!file.exists()) {
		file.createNewFile();
		}
 
            PrintWriter writer = new PrintWriter(file.getAbsoluteFile(), "UTF-8");
                writer.println("The first line");
                writer.println("The second line");
                writer.println("The third line");
                writer.close();

	}
}
