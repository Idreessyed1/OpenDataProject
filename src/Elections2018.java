import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Elections2018 extends DataTemplate {

    private Scanner input;

    private ArrayList<String> candidateNames;
    private ArrayList<String> contestTitles;
    private ArrayList<String> locationNames;
    private ArrayList<String> wards;

    private ArrayList<TempTitle> tempTitles;

    public Elections2018(){
        super("Elections2018", "Worksheet");
        setDataDesc("Description");
        input = new Scanner(System.in);
        candidateNames = new ArrayList<String>();
        contestTitles = new ArrayList<String>();
        locationNames = new ArrayList<String>();
        wards = new ArrayList<String>();
        tempTitles = new ArrayList<TempTitle>();
        getCandidateNames();
        getContestTitles();
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

    public void getContestTitles(){
        contestTitles.add(dataValues.get("Contest Title").get(0));
        for(String contestTitle: dataValues.get("Contest Title")){
            if(!contestTitles.contains(contestTitle)){
                contestTitles.add(contestTitle);
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
        int totalVotes=0, userCandidate = selectChoice(candidateNames, "candidate")-1;
        for(int i=0; i<dataValues.get("Total").size(); i++) {
            if(dataValues.get("Candidate Name").get(i).equals(candidateNames.get(userCandidate))){
                totalVotes += Integer.parseInt(dataValues.get("Total").get(i));
            }
        }
        System.out.println("Total votes of " + candidateNames.get(userCandidate) + " is " + totalVotes);
    }

    public void contestTitleInfo(){
        int userContestTitle = selectChoice(contestTitles, "contest title");
        System.out.println("Results for " + contestTitles.get(userContestTitle) + ": ");
        for(String candidateName : candidateNames) {
            int totalVotes=0;
            for (int i = 0; i < dataValues.get("Total").size(); i++) {
                if (dataValues.get("Contest Title").get(i).equals(contestTitles.get(userContestTitle)) && dataValues.get("Candidate Name").get(i).equals(candidateName)) {
                    totalVotes += Integer.parseInt(dataValues.get("Total").get(i));
                }
            }
            if(dataValues.get("Contest Title").get(dataValues.get("Candidate Name").indexOf(candidateName)).equals(contestTitles.get(userContestTitle))){
                System.out.println(candidateName + ": " + totalVotes);
            }
        }
    }

    public void pollLocationInfo(){
        int userLocation = selectChoice(locationNames, "polling location");
        parseData("Poll Name", locationNames.get(userLocation));
        System.out.println("At location " + locationNames.get(userLocation) + " the results were: ");
        printResults();
    }

    public void wardInfo(){
        String userWard = wards.get(selectChoice(wards, "wards")-1);
        if(userWard.equals("N/A")) {
            userWard = "";
        }
        parseData("Ward", userWard);
        System.out.println("In ward " + userWard + " the results were: ");
        printResults();
    }

    public void overallInfo(){

    }

    public int selectChoice(ArrayList<String> data, String type){
        System.out.println("Please select the " + type +" you'd like to view [1-" + data.size() + "]: ");
        for(int i=1; i<=data.size(); i++){
            System.out.println("[" + i + "]: " + data.get(i-1));
        }
        return Integer.parseInt(input.nextLine());
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
        System.out.println("Here are the options to choose from: \n" +
                "1. Results for a specific candidate\n" +
                "2. Results for a specific contest titles\n" +
                "3. Results from a specific polling location\n" +
                "4. Results from a specific on ward\n" +
                "5. Overall results of the 2018 election");
        System.out.print("Which option would you like to select: ");
        int choice = input.nextInt();
        switch(choice){
            case 1:
                candidateInfo();
                break;
            case 2:
                contestTitleInfo();
                break;
            case 3:
                pollLocationInfo();
                break;
            case 4:
                wardInfo();
                break;
            case 5:
                overallInfo();
                break;
        }
    }
}

