import java.util.*;
import java.lang.Math;

public class FireStation extends DataTemplate{

    private String desc = "";
    private Scanner sc = new Scanner(System.in);

    public FireStation(){
        super("FireStations", "Worksheet");
        setDataDesc(desc);
    }

    @Override
    public void showOptions() {
        System.out.println("Here are your options:\n" +
                "1. Quick summary of the Fire Stations in Windsor\n" +
                "2. Find the nearest fire station to your location \n");
        System.out.println("What would you like to view?");
        int choice = sc.nextInt();
        switch(choice){
            case 1:
                summary();
                break;
            case 2:
                System.out.print("Enter the latitude: ");
                double lat = sc.nextDouble();
                System.out.print("Enter the longitude: ");
                double log = sc.nextDouble();
                getClosestStation(lat, log);
                break;
        }
    }

    public void summary(){
        ArrayList<String> addresses = new ArrayList<>();
        for (int i = 0; i < dataValues.get("FID").size(); i++){
            System.out.println("Fire Station #" + (i + 1));
            System.out.println("Address: " + addresses.get(i) + "\n");
        }
    }

    public void getClosestStation(double lat, double log){
        ArrayList<String> latitudes = dataValues.get("Y");
        ArrayList<String> longitudes = dataValues.get("X");
        ArrayList<String> fids = dataValues.get("FID");
        int minIndex = -1;
        double minDist = 0;
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
        System.out.println("Closest fire station is fire station #" + fids.get(minIndex));
    }
}
