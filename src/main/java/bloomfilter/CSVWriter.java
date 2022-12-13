package bloomfilter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVWriter {
    public static void makeCSV(ArrayList<String> elements, String filename) throws IOException {

        //Creating our empty .csv
        File f = new File(filename);
        if (f.exists()){
            f.delete();
        }

        //Creating the filewriter
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename, true);
        } catch(IOException e){
            System.err.println("IOException thrown by makeCSV : " + e);
        }
        if(fw == null){
            return;
        }

        //Adding to it all elements given as arguments
        f.createNewFile();
        for(String csvline : elements){
            fw.append(csvline);
            fw.append("\n");
        }

        fw.flush();
        fw.close();
    }
}
