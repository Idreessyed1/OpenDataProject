import org.omg.PortableInterceptor.INACTIVE;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Elections2018 extends DataTemplate {

    private final String fileName = "Elections2018";
    private final String workBookName = "Worksheet";
    private final String description = "";

    //private ArrayList<String> candidateNames;
    private ArrayList<String> locationNames;

    private ArrayList<TempTitle> tempTitles;

    public Elections2018(){
        super("Elections2018", "Worksheet");
        //candidateNames = new ArrayList<String>();
        locationNames = new ArrayList<String>();
        tempTitles = new ArrayList<TempTitle>();
    }

    public void getWardInformation(){

    }

/*    public void getCandidates(){
        candidateNames.add(dataValues.get("Candidate Name").get(0));
        for(String candidateName : dataValues.get("Candidate Name")) {
            if (!candidateNames.contains(candidateName)){
                candidateNames.add(candidateName);
            }
        }
        //System.out.println(candidateNames);
    }*/

    public void getLocations(){
        System.out.println(dataValues.keySet());
        locationNames.add(dataValues.get("Poll Name").get(0));
        for(String locationName : dataValues.get("Poll Name")) {
            if (!locationNames.contains(locationName)){
                locationNames.add(locationName);
            }
        }
        //System.out.println(locationNames);
    }

    public void wardInfo(String wardNum){
        TempTitle currentTempTitle;
        String testCell = "";
        for(int i=0; i<dataValues.get("Ward").size(); i++) {
            if(dataValues.get("Ward").get(i).equals(wardNum) && !dataValues.get("Contest Title").get(i).equals(testCell)) {
                testCell = dataValues.get("Contest Title").get(i);
                System.out.println(dataValues.get("Contest Title").get(i));
                currentTempTitle = new TempTitle(testCell);
                tempTitles.add(currentTempTitle);
                getCandidates(currentTempTitle, testCell, wardNum);
            }
        }
    }

    public void getCandidates(TempTitle currentTempTitle, String typeTitle, String wardNum){
        ArrayList<String> candidateNames = new ArrayList<String>();
        String candidateName;
        for(int i=0; i<dataValues.get("Candidate Name").size(); i++) {
            candidateName = dataValues.get("Candidate Name").get(i);
            if(!candidateNames.contains(candidateName) && dataValues.get("Ward").get(i).equals(wardNum) && dataValues.get("Contest Title").get(i).equals(typeTitle)){
                currentTempTitle.addCandidate(candidateName);
                candidateNames.add(candidateName);
                System.out.println(candidateName);
                getPollResults(currentTempTitle, candidateName, wardNum);
            }
        }
    }

    public void getPollResults(TempTitle currentTempTitle, String candidateName, String wardNum){
        for(int i=0; i<dataValues.get("Candidate Name").size(); i++) {
            if(dataValues.get("Ward").get(i).equals(wardNum) && dataValues.get("Candidate Name").get(i).equals(candidateName)){
                currentTempTitle.addResults(candidateName, Integer.parseInt(dataValues.get("Total").get(i)));
            }
        }
        printResults();
    }

    public void printResults(){
        for(TempTitle tempTitle: tempTitles){
            System.out.println(tempTitle.getName() + ": ");
            tempTitle.getHashMap().forEach((key, value) -> System.out.println("\t" +key + ": " + value));
            System.out.println();
        }
    }

    @Override
    public void showOptions() {
        Scanner input = new Scanner(System.in);

        System.out.println("Here are the options to choose from: \n" +
                "1. Get data based on ward\n" +
                "2. Get data on contest titles\n" +
                "3. Search for candidate\n" +
                "4. Get data based on polling location\n" +
                "5. Overall election results");

        System.out.print("Which database would you like to see (or press 4 to exit): ");

        int choice = input.nextInt();

        switch(choice){
            case 1:
                wardInfo("1");

            case 4:
                //getLocations();
        }

    }
}

