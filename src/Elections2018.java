import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Elections2018 extends DataTemplate {

    private Scanner input;

    private ArrayList<String> candidateNames;
    private ArrayList<String> locationNames;
    private ArrayList<String> wards;

    private ArrayList<TempTitle> tempTitles;

    public Elections2018(){
        super("Elections2018", "Worksheet");
        input = new Scanner(System.in);

        candidateNames = new ArrayList<String>();
        locationNames = new ArrayList<String>();
        wards = new ArrayList<String>();
        tempTitles = new ArrayList<TempTitle>();

        getCandidateNames();
        getWards();
        getLocations();
    }

    public void getCandidateNames(){
        candidateNames.add(dataValues.get("Candidate Name").get(0));
        for(String candidateName : dataValues.get("Candidate Name")) {
            if (!candidateNames.contains(candidateName)){
                candidateNames.add(candidateName);
            }
        }
    }

    public void getWards(){
        wards.add(dataValues.get("Ward").get(0));
        for(String ward : dataValues.get("Ward")) {
            if(ward.equals("")) ward = "N/A";
            if (!wards.contains(ward)) wards.add(ward);
        }
    }

    public void getLocations(){
        locationNames.add(dataValues.get("Poll Name").get(0));
        for(String locationName : dataValues.get("Poll Name")) {
            if (!locationNames.contains(locationName)){
                locationNames.add(locationName);
            }
        }
    }

    public void candidateInfo(){
        int totalVotes=0;
        System.out.println("Please select the candidate you'd like to view [1-" + candidateNames.size() + "]: ");
        for(int i=1; i<=candidateNames.size(); i++){
            System.out.println("[" + i + "]: " + candidateNames.get(i-1));
        }
        int userCandidate = Integer.parseInt(input.nextLine())-1;
        for(int i=0; i<dataValues.get("Total").size(); i++) {
            if(dataValues.get("Candidate Name").get(i).equals(candidateNames.get(userCandidate))){
                totalVotes += Integer.parseInt(dataValues.get("Total").get(i));
            }
        }
        System.out.println("Total votes of " + candidateNames.get(userCandidate) + " is " + totalVotes);
    }

    public void wardInfo(){
        System.out.println("Please enter the ward you'd like to view [1-10, AP or N/A]: ");
        String userWard = input.nextLine().toUpperCase();
        while(!wards.contains(userWard)){
            System.out.println("Invalid ward!");
            System.out.println("Please enter the ward you'd like to view [1-10, AP or N/A]: ");
            userWard = input.nextLine().toUpperCase();
        }
        if(userWard.equals("N/A")) userWard = "";
        parseData("Ward", userWard);
        System.out.println("In ward " + userWard + " the results were: ");
        printResults();
    }

    public void pollLocationInfo(){
        System.out.println("Please select the polling location you'd like to view [1-" + locationNames.size() + "]: ");
        for(int i=1; i<=locationNames.size(); i++){
            System.out.println("[" + i + "]: " + locationNames.get(i-1));
        }
        int userLocation = Integer.parseInt(input.nextLine());
        parseData("Poll Name", locationNames.get(userLocation));
        System.out.println("At location " + locationNames.get(userLocation) + " the results were: ");
        printResults();
    }

    public void parseData(String searchType, String searchTerm){
        TempTitle currentTempTitle;
        String testCell = "";
        for(int i=0; i<dataValues.get(searchType).size(); i++) {
            if(dataValues.get(searchType).get(i).equals(searchTerm) && !dataValues.get("Contest Title").get(i).equals(testCell)) {
                testCell = dataValues.get("Contest Title").get(i);
                currentTempTitle = new TempTitle(testCell);
                tempTitles.add(currentTempTitle);
                getCandidatesOnQuery(currentTempTitle, testCell, searchType, searchTerm);
            }
        }
    }

    public void getCandidatesOnQuery(TempTitle currentTempTitle, String typeTitle, String searchType, String searchTerm){
        ArrayList<String> candidateNames = new ArrayList<String>();
        String candidateName;
        for(int i=0; i<dataValues.get("Candidate Name").size(); i++) {
            candidateName = dataValues.get("Candidate Name").get(i);
            if(!candidateNames.contains(candidateName) && dataValues.get(searchType).get(i).equals(searchTerm) && dataValues.get("Contest Title").get(i).equals(typeTitle)){
                currentTempTitle.addCandidate(candidateName);
                candidateNames.add(candidateName);
                getPollResultsOnQuery(currentTempTitle, candidateName, searchType, searchTerm);
            }
        }
    }

    public void getPollResultsOnQuery(TempTitle currentTempTitle, String candidateName, String searchType, String searchTerm){
        for(int i=0; i<dataValues.get("Candidate Name").size(); i++) {
            if(dataValues.get(searchType).get(i).equals(searchTerm) && dataValues.get("Candidate Name").get(i).equals(candidateName)){
                currentTempTitle.addResults(candidateName, Integer.parseInt(dataValues.get("Total").get(i)));
            }
        }
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
        System.out.print("Which option would you like to select: ");

        int choice = input.nextInt();
        switch(choice){
            case 1:
                wardInfo();
                break;
            case 3:
                candidateInfo();
                break;
            case 4:
                pollLocationInfo();
                break;
        }
    }
}

