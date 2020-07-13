import java.util.*;

//Temporary class to test ExcelExtractor and print the HashMap
public class Main {

    public static void main(String[]args){
        //FileExtractor throws IOException if file is not found
        try {
            Extractor testExtractor = new Extractor("FireStations", "Worksheet");
            printInfo(testExtractor.extractInfo());
        } catch (Exception e){
            System.out.println("File not found!");
        }
    }

    //Prints the HashMap
    public static void printInfo(HashMap<String, ArrayList> excelInfo){
        for (String name: excelInfo.keySet()){
            String key = name;
            String value = excelInfo.get(name).toString();
            System.out.println(key + " " + value);
        }
    }
}
