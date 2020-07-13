import java.util.ArrayList;
import java.util.Scanner;

public class Elections2018 extends DataTemplate {

    private final String fileName = "Elections2018";
    private final String workBookName = "Worksheet";
    private final String description = "";

    private ArrayList<String> candidateNames;
    private ArrayList<String> locationNames;




    public Elections2018(){
        super("Elections2018", "Worksheet");
        candidateNames = new ArrayList<String>();
        locationNames = new ArrayList<String>();
    }

    public void getWardInformation(){

    }

    public void getCandidates(){
        candidateNames.add(dataValues.get("Candidate Name").get(0));
        for(String candidateName : dataValues.get("Candidate Name")) {
            if (!candidateNames.contains(candidateName)){
                candidateNames.add(candidateName);
            }
        }
        System.out.println(candidateNames);
    }

    public void getLocations(){
        System.out.println(dataValues.keySet());
        locationNames.add(dataValues.get("Poll Name").get(0));
        for(String locationName : dataValues.get("Poll Name")) {
            if (!locationNames.contains(locationName)){
                locationNames.add(locationName);
            }
        }
        System.out.println(locationNames);
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
                getCandidates();

            case 4:
                getLocations();
        }

    }
}
