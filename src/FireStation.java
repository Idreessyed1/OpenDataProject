import java.util.*;
import java.lang.Math;
import org.json.simple.*;

//This method controls the FireStations database. This is a very basic database with only 7 lines in it, so there
//is not much for the user that it can provide
public class FireStation extends DataTemplate{

    private String desc = "";
    private Scanner sc = new Scanner(System.in);
    private JSONContructor jsonContructor;

    //Contructor with the same logic as the NoiseRequests one
    public FireStation(boolean userView){
        super("FireStations", "Worksheet", userView);
        jsonContructor = new JSONContructor();
        setDataDesc(desc);
    }

    //Prints the options the user can select
    @Override
    public void showOptions() {
        int choice = 0;
        System.out.println("\nHere are the options to choose from:");
        while (choice<3) {
            System.out.println("---------------------------------------------------\"\n" +
                    "1. Quick summary of the Fire Stations in Windsor\n" +
                    "2. Find the nearest fire station to your location \n" +
                    "3. Exit Fire Station Database\n" +
                    "---------------------------------------------------");
            System.out.println("What would you like to view?");
            choice = sc.nextInt(); //Getting input from the user
            switch (choice) {
                case 1:
                    summary();
                    break;
                case 2:
                    System.out.print("Enter the latitude: "); //Asking the user for latitude
                    double lat = sc.nextDouble();
                    System.out.print("Enter the longitude: "); //Asking the user for longitude
                    double log = sc.nextDouble();
                    getClosestStation(lat, log); //Calling the getClosetStation method
                    break;
                case 3:
                    break;
            }
        }
    }

    //This method provides a very brief summary of each fire station in Windsor and its address
    public void summary(){
        ArrayList<String> addresses = dataValues.get("ADDRESS");
        JSONObject mainObject = jsonContructor.createJSONObject();
        JSONArray mainArray = jsonContructor.createJSONArray();
        jsonContructor.putJSONObject(mainObject, mainArray, "Fire Stations");
        for (int i = 0; i < dataValues.get("FID").size(); i++){
            if(userView == true) {
                System.out.println("Fire Station #" + (i + 1));
                System.out.println("Address: " + addresses.get(i) + "\n");
            }
            JSONObject fireStationObject = jsonContructor.createJSONObject();
            jsonContructor.putJSONObject(fireStationObject, "Fire Station", Integer.toString(i+1));
            jsonContructor.putJSONObject(fireStationObject, "Address", addresses.get(i));
            jsonContructor.addToJSONArray(mainArray, fireStationObject);
        }
        if(userView==false){
            jsonContructor.writeToFile(mainObject, "Fire_Station_Summery");
            System.out.println("JSON file created");
        }
    }

    //This method takes latitude and longitutde from the user and outputs the closest firestation to the user's location
    public void getClosestStation(double lat, double log){
        ArrayList<String> latitudes = dataValues.get("Y");
        ArrayList<String> longitudes = dataValues.get("X");
        ArrayList<String> fids = dataValues.get("FID");
        int minIndex = -1;
        double minDist = 0;
        /*
        In this for loop, we are going through each fire station in the FireStations database and calculating
        the distance between the latitude and longitude entered by the user and the latitude and longitude of fire station
        using the Haversine formula to calculate distance between latitude and longitude points
         */
        for (int i = 0; i < latitudes.size(); i++){
            double currLat = Double.parseDouble(latitudes.get(i));
            double currLong = Double.parseDouble(longitudes.get(i));
            double latDiff = (currLat - lat) * (Math.PI / 180);
            double longDiff = (currLong - log) * (Math.PI / 180);
            double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) + Math.cos(lat * (Math.PI / 180)) * Math.cos(currLat * (Math.PI / 180)) * Math.sin(longDiff / 2) * Math.sin(longDiff / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double d = 6371* c;
            if (i == 0){
                minDist = d;
                minIndex = i;
            }
            else{
                if (d < minDist){
                    minDist = d;
                    minIndex = i;
                }
            }
        }
        JSONObject mainObject = jsonContructor.createJSONObject();
        jsonContructor.putJSONObject(mainObject, "Fire Station", fids.get(minIndex));
        jsonContructor.putJSONObject(mainObject, "Address", dataValues.get("ADDRESS").get(minIndex));

        if(userView == true){
            System.out.println("Closest fire station is fire station #" + fids.get(minIndex) + "\nAddress: " + dataValues.get("ADDRESS").get(minIndex));
        }
        else{
            jsonContructor.writeToFile(mainObject, "Closest_Fire_Station");
            System.out.println("JSON file created");
        }
    }
}