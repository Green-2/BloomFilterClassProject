package bloomfilter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVWriter {
    /**
     * Should not be called.
     */
    private CSVWriter(){}
    /**
     * Creates a file with the given name and data at the root of the project.
     * CAUTION — This WILL delete any file with the given name. There is no security nor rollback.
     * @param data The data of the csv file
     * @param filename The name which the csv will have
     */
    public static void makeCSV(ArrayList<String> data, String filename) throws IOException {

        if(!filename.endsWith(".csv")){
            System.err.println("This function creates csv files.");
            return;
        }

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

        //Adding to it all data given as arguments
        f.createNewFile();
        for(String csvline : data){
            fw.append(csvline);
            fw.append("\n");
        }

        fw.flush();
        fw.close();
    }
}
