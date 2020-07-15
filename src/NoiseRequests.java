import java.util.*;

public class NoiseRequests extends DataTemplate {
    private String desc = "";
    ArrayList<String> streets = new ArrayList();
    private Scanner sc = new Scanner(System.in);
    private String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    public NoiseRequests(){
        super("Noise2019", "Worksheet");
        setDataDesc(desc);
        ArrayList<String> str = dataValues.get("Street");
        for (int i = 0; i < str.size(); i++){
            if (!streets.contains(str.get(i))){
                streets.add(str.get(i));
            }
        }
    }

    @Override
    public void showOptions() {
        System.out.println("Here are your options:\n" +
                "1. Quick summary of the 2019 Noise Service Requests\n" +
                "2. Display the number of noise service requests for a particular ward\n" +
                "3. Get the number of noise service requests based on a specific month\n" +
                "4. Enter a street name and see the number of noise service requests\n" +
                "5. Get a breakdown of noise service requests by the hour");
        System.out.println("What would you like to view?");
        int choice = sc.nextInt();
        switch(choice){
            case 1:
                summary();
                break;
            case 2:
                System.out.print("Enter the ward that you would like to view (1 - 11): ");
                int ward = sc.nextInt();
                getWardResults("user", ward);
                break;
            case 3:
                System.out.print("Which month would you like to choose (enter the number associated with the month): ");
                int month = sc.nextInt();
                getMonth("user", month);
                break;
            case 4:
                System.out.println("Which street would you like to choose?");
                sc.nextLine();
                String street = sc.nextLine();
                getStreet("user", street);
                break;
            case 5:
                getHours();
                break;
        }
    }

    public void summary(){
        System.out.println("In total, there were " + dataValues.get("Service Request Description").size() + " noise service requests");
        System.out.println("Here is a brief breakdown of each ward for 2019: ");
        for (int i = 1 ; i <= 11; i++) getWardResults("method", i);
        System.out.println("Here is a brief breakdown of each month for 2019: ");
        for (int i = 1; i <= 12; i++) getMonth("method", i);
        System.out.println("Here is a brief breakdown of each street for 2019: ");
        for (int i = 0; i < streets.size(); i++){
            if (streets.get(i).length() > 1) getStreet("method", streets.get(i));
        }
    }
    public void getWardResults(String requestType, int ward){
        String wardName = "WARD " + ward;
        ArrayList<String> wards = dataValues.get("Ward");
        ArrayList<Integer> spots = new ArrayList<>();
        int total = 0;
        for (int i = 0; i < wards.size(); i++){
            if (wards.get(i).equals(wardName)){
                spots.add(i);
                ++total;
            }
        }
        if (requestType.equals("user")) System.out.println("In 2019, there were total " + total + " noise service requests in ward " + ward);
        else System.out.println("Ward " + ward + ": " + total);
    }

    public void getMonth(String requestType, int month){
        String monthName = months[month - 1];
        ArrayList<String> dates = dataValues.get("Created Date");

        int total = 0;
        for (int i = 0; i < dates.size(); i++){
            String[] dateSplit = dates.get(i).split(" ");
            if (dateSplit[0].equals(monthName)) ++total;
        }
        if (requestType.equals("user")) System.out.println("In 2019, there were " + total + " noise service request in the month of " + monthName);
        else System.out.println(monthName + ": " + total);
    }

    public void getStreet(String requestType, String street){
        String streetName = street.toUpperCase();
        ArrayList<String> allStreets = dataValues.get("Street");
        int total = 0;
        for (int i = 0; i < allStreets.size(); i++){
            String currStreet = allStreets.get(i);
            if (currStreet.length() != 1) {
                if (streetName.contains(allStreets.get(i))) {
                    ++total;
                }
            }
        }
        if (requestType.equals("user")) System.out.println("In 2019, there were total " + total + " noise service requests on " + street);
        else System.out.println(street + ": "+ total);
    }

    public void getHours(){
        String[] hours = {"12 AM", "01 AM", "02 AM", "03 AM", "04 AM", "05 AM", "06 AM", "07 AM", "08 AM", "09 AM", "10 AM", "11 AM", "12 PM", "01 PM", "02 PM", "03 PM", "04 PM", "05 PM", "06 PM", "07 PM", "08 PM", "09 PM", "10 PM", "11 PM", "12 PM"};
        Thing[] things = new Thing[24];
        for (int i = 0; i < 24; i++){
            things[i] = new Thing(hours[i]);
        }
        ArrayList<String> times = dataValues.get("Created Date");
        for (int i = 0; i < times.size(); i++){
            String date = times.get(i);
            String[] dateParts = date.split("2019 ");
            String extension = (dateParts[1].split(" "))[1];
            String hour = (dateParts[1].split(":"))[0];
            String trueTime = hour + " " + extension;
            things[Arrays.asList(hours).indexOf(trueTime)].total += 1;
        }
        Arrays.sort(things);
        System.out.println("Here is the breakdown of the noise requests in 2019 by the hour from most to least: ");
        for (int i = 0; i < 24; i++){
            if (things[i].total == 0) break;
            System.out.println(things[i]);
        }
    }
}

class Thing implements Comparable<Thing>{
    public String name;
    public int total;
    public Thing(String name){
        this.name = name;
        total = 0;
    }

    @Override
    public int compareTo(Thing o) {
        if (this.total > o.total) return -1;
        else if (this.total < o.total) return 1;
        else return 0;
    }

    public String toString(){
        return name + ": " + total;
    }
}