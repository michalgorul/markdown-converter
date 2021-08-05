package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadingFromFile {

    public static String getFileInString(String path) throws IOException {
        String returnString = null;
        try{
            returnString = new String(Files.readAllBytes(Paths.get(path)));
        } catch (Exception e){
            e.printStackTrace();
        }
        return returnString;
    }
}
