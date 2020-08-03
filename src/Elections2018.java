import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.*;

public class Elections2018 extends DataTemplate {

    private JSONContructor jsonContructor;
    private Scanner input; //For user input
    private ArrayList<String> candidateNames;
    private ArrayList<String> contestTitles;
    private ArrayList<String> locationNames;
    private ArrayList<String> wards;
    private ArrayList<TempTitle> tempTitles;

    public Elections2018(){
        super("Elections2018", "Worksheet"); //calling the constructor of data values
        setDataDesc("Description");
        input = new Scanner(System.in);
        candidateNames = new ArrayList<String>();
        contestTitles = new ArrayList<String>();
        locationNames = new ArrayList<String>();
        wards = new ArrayList<String>();
        tempTitles = new ArrayList<TempTitle>();
        jsonContructor = new JSONContructor();
        //calling the methods to populate the arrays
        getCandidateNames();
        getContestTitles();
        getWards();
        getLocations();
    }

    //This method extracts the candidate names form dataValues and stores it in candidateNames array
    public void getCandidateNames(){
        candidateNames.add(dataValues.get("Candidate Name").get(0));
        for(String candidateName : dataValues.get("Candidate Name")) {
            if (!candidateNames.contains(candidateName)){
                candidateNames.add(candidateName);
            }
        }
    }

    //This method extracts the contest titles form dataValues and stores it in contestTitle array
    public void getContestTitles(){
        contestTitles.add(dataValues.get("Contest Title").get(0));
        for(String contestTitle: dataValues.get("Contest Title")){
            if(!contestTitles.contains(contestTitle)){
                contestTitles.add(contestTitle);
            }
        }
    }

    //This method extracts the ward form dataValues and stores it in ward array
    public void getWards(){
        wards.add(dataValues.get("Ward").get(0));
        for(String ward : dataValues.get("Ward")) {
            if(ward.equals("")) ward = "N/A";
            if (!wards.contains(ward)) wards.add(ward);
        }
    }

    //This method extracts the locations form dataValues and stores it in locationNames
    public void getLocations(){
        locationNames.add(dataValues.get("Poll Name").get(0));
        for(String locationName : dataValues.get("Poll Name")) {
            if (!locationNames.contains(locationName)){
                locationNames.add(locationName);
            }
        }
    }

    //Extracts the votes of candidate selected
    public void candidateInfo(){
        int totalVotes=0;
        int userCandidate = selectChoice(candidateNames, "candidate");

        for(int i=0; i<dataValues.get("Total").size(); i++) {//Looping through the total array in dataValues
            if(dataValues.get("Candidate Name").get(i).equals(candidateNames.get(userCandidate))){
                totalVotes += Integer.parseInt(dataValues.get("Total").get(i)); //Adding the total votes of the candidate
            }
        }
        //Prints the total to the user
        System.out.println("Total votes of " + candidateNames.get(userCandidate) + " is " + totalVotes);
        /*jsonContructor.addJSONObject("Candidate Name", candidateNames.get(userCandidate));
        jsonContructor.addJSONObject("Votes", Integer.toString(totalVotes));
        jsonContructor.writeToFile("Candidate Info");*/
    }

    //Extracts the results of each contest title
    public void contestTitleInfo(){
        int userContestTitle = selectChoice(contestTitles, "contest title");
        System.out.println("Results for candidate title: " + contestTitles.get(userContestTitle) + ": ");
        JSONObject mainJsonObject = jsonContructor.createJSONObject();
        JSONArray jsonArray = jsonContructor.createJSONArray();
        jsonContructor.putJSONObject(mainJsonObject, jsonArray, contestTitles.get(userContestTitle));
        for(String candidateName : candidateNames) {//Going through each candidate name
            int totalVotes=0;
            for (int i = 0; i < dataValues.get("Total").size(); i++) { //Looping through the results of each candidate
                //if the contest title is the same as the one the user selected and the candidate name is the same
                if (dataValues.get("Contest Title").get(i).equals(contestTitles.get(userContestTitle)) && dataValues.get("Candidate Name").get(i).equals(candidateName)) {
                    totalVotes += Integer.parseInt(dataValues.get("Total").get(i)); //Adding their votes us
                }
            }
            //Print the total votes
            if(dataValues.get("Contest Title").get(dataValues.get("Candidate Name").indexOf(candidateName)).equals(contestTitles.get(userContestTitle))){
                System.out.println(candidateName + ": " + totalVotes);
                JSONObject jsonObject = jsonContructor.createJSONObject();
                jsonContructor.putJSONObject(jsonObject,"Candidate Name", candidateName);
                jsonContructor.putJSONObject(jsonObject, "Votes", Integer.toString(totalVotes));
                jsonContructor.addToJSONArray(jsonArray, jsonObject);
            }
        }
        jsonContructor.writeToFile(mainJsonObject,"Contest Title Info");
    }

    //Extracts information based on the poll location
    public void pollLocationInfo(){
        int userLocation = selectChoice(locationNames, "polling location");
        parseData("Poll Name", locationNames.get(userLocation));
        System.out.println("At location " + locationNames.get(userLocation) + " the results were: ");
        printResults();
    }

    //Extracts the ward info
    public void wardInfo(){
        String userWard = wards.get(selectChoice(wards, "wards"));
        if(userWard.equals("N/A")) {
            userWard = "";
        }
        parseData("Ward", userWard);
        System.out.println("In ward " + userWard + " the results were: ");
        printResults();
    }

    //prints the overall database
    public void overallInfo(){
        System.out.println("Overall Results of Election 2018:");
        for(String contestTitle: contestTitles) { //looping through contest titles
            System.out.println(contestTitle + ": ");
            for (String candidateName : candidateNames) {//Based on the contest titles get the candidates name
                int totalVotes = 0;
                for (int i = 0; i < dataValues.get("Total").size(); i++) {//Adding the votes of each candidate
                    if (dataValues.get("Contest Title").get(i).equals(contestTitle) && dataValues.get("Candidate Name").get(i).equals(candidateName)) {
                        totalVotes += Integer.parseInt(dataValues.get("Total").get(i));
                    }
                }
                //Printing total votes of each candidate
                if (dataValues.get("Contest Title").get(dataValues.get("Candidate Name").indexOf(candidateName)).equals(contestTitle)){
                    System.out.println("\t" + candidateName + ": " + totalVotes);
                }
            }
            System.out.println();
        }
    }

    //This methods will print an array list a user can select the choice
    public int selectChoice(ArrayList<String> data, String type){
        int choice;
        System.out.println("Please select the " + type +" you'd like to view [1-" + data.size() + "]: ");
        while(true){
            for(int i=1; i<=data.size(); i++){ //printing the options
                System.out.println("[" + i + "]: " + data.get(i-1));
            }
            choice = input.nextInt(); //Getting the choice form the user
            if(choice>0 && choice<data.size()+1){
                break; //If its valid exit the loop
            }
            System.out.println("Incorrect input! \nPlease select the " + type +" you'd like to view [1-" + data.size() + "]: ");
        }
        return choice-1; //returning the choice back to the user
    }

    //This method will get the data based on the search query
    public void parseData(String arrayType, String searchTerm){
        TempTitle currentTempTitle;
        String testCell = "";
        for(int i=0; i<dataValues.get(arrayType).size(); i++) {
            if(dataValues.get(arrayType).get(i).equals(searchTerm) && !dataValues.get("Contest Title").get(i).equals(testCell)) {
                testCell = dataValues.get("Contest Title").get(i);//Get the contest title
                currentTempTitle = new TempTitle(testCell); //Create a temptitle and add it to the array list
                tempTitles.add(currentTempTitle);
                getCandidatesOnQuery(currentTempTitle, testCell, arrayType, searchTerm);
            }
        }
    }

    //This method will get the candidates based on the query
    public void getCandidatesOnQuery(TempTitle currentTempTitle, String typeTitle, String arrayType, String searchTerm){
        ArrayList<String> candidateNames = new ArrayList<String>();
        String candidateName;
        for(int i=0; i<dataValues.get("Candidate Name").size(); i++) {
            candidateName = dataValues.get("Candidate Name").get(i);//Getting the candidate name
            if(!candidateNames.contains(candidateName) && dataValues.get(arrayType).get(i).equals(searchTerm) && dataValues.get("Contest Title").get(i).equals(typeTitle)){
                currentTempTitle.addCandidate(candidateName);//if candidate name isnt in the array add it
                candidateNames.add(candidateName);
                getPollResultsOnQuery(currentTempTitle, candidateName, arrayType, searchTerm);
            }
        }
    }

    //finds the votes of each candidate
    public void getPollResultsOnQuery(TempTitle currentTempTitle, String candidateName, String arrayType, String searchTerm){
        for(int i=0; i<dataValues.get("Candidate Name").size(); i++) {//loop through candidate names
            if(dataValues.get(arrayType).get(i).equals(searchTerm) && dataValues.get("Candidate Name").get(i).equals(candidateName)){
                currentTempTitle.addResults(candidateName, Integer.parseInt(dataValues.get("Total").get(i)));//add their votes
            }
        }
    }

    //prints the HashMap of each TempTitle
    public void printResults(){
        for(TempTitle tempTitle: tempTitles){
            System.out.println(tempTitle.getName() + ": ");
            tempTitle.getHashMap().forEach((key, value) -> System.out.println("\t" +key + ": " + value));
            System.out.println();
        }
    }

    @Override
    //user can select which option they want to view
    public void showOptions() {
        int choice = 0;
        System.out.println("\nHere are the options to choose from:");
        while (choice<6) {
            System.out.println("---------------------------------------------\n" +
                    "1. Results for a specific candidate\n" +
                    "2. Results for a specific contest titles\n" +
                    "3. Results from a specific polling location\n" +
                    "4. Results from a specific on ward\n" +
                    "5. Overall results of the 2018 election\n" +
                    "6. Exit Elections 2018\n"+
                    "---------------------------------------------");
            System.out.println("What would you like to view?");
            choice = input.nextInt(); //Getting the option from the user
            switch (choice) {
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
                case 6:
                    break; //Leave Elections2018 when 6 is entered breaks while loop
            }
        }
    }
}