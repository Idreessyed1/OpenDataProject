
import java.util.*;

//This class represents the Noise Service Requests database
public class NoiseRequests extends DataTemplate {

    private String desc = ""; //Each of the databases will have an associated description to them; haven't implemented it yet since there is no use
    /*
    This is the ArrayList that contains all the unique streets in the database. The reason why this is important is because
    there are several different streets in the database, so it is important to keep track of which unique streets there are
    so that when a user requests to view a street's requests, we can first check in here if it exists instead of having
    to go through the entire database and see if it exists
    */
    private ArrayList<String> streets;

    private Scanner sc;
    //This is just a simple array with all the months in the year that will come in handy for one of the methods
    private String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private String[] hours = {"12 AM", "01 AM", "02 AM", "03 AM", "04 AM", "05 AM", "06 AM", "07 AM", "08 AM", "09 AM", "10 AM", "11 AM", "12 PM", "01 PM", "02 PM", "03 PM", "04 PM", "05 PM", "06 PM", "07 PM", "08 PM", "09 PM", "10 PM", "11 PM", "12 PM"};


    public NoiseRequests(){
        super("Noise2019", "Worksheet");
        /*
        The reason why we call setDataDesc instead of just passing the description into the constructor was because
        of a bug we ran into. When we would pass the description from this class in the constructor, we would get an
        error which we assumed had to do with the fact that when the constructor is invoked, that description is still
        not yet instantiated, so we decided to remove the description as part of the constructor of DataTemplate and just
        update the datadesc through setDataDesc and the bug was solved
         */
        setDataDesc(desc);
        streets = new ArrayList();
        sc = new Scanner(System.in);
        //This goes through the HashMap associated to this database, finds the ArrayList associated with the streets in
        //the database,and extracts the unique streets from them
        ArrayList<String> str = dataValues.get("Street");
        for (int i = 0; i < str.size(); i++){
            if (!streets.contains(str.get(i))){
                streets.add(str.get(i));
            }
        }
    }

    //This is the implemented abstract method from the DataTemplate class that shows all the data that the user
    //can extract from the database
    @Override
    public void showOptions() {
        int choice = 0;
        System.out.println("\nHere are the options to choose from:");
        while (choice<6) {
            System.out.println("------------------------------------------------------------------------\n" +
                    "1. Quick summary of the 2019 Noise Service Requests\n" +
                    "2. Display the number of noise service requests for a particular ward\n" +
                    "3. Get the number of noise service requests based on a specific month\n" +
                    "4. Enter a street name and see the number of noise service requests\n" +
                    "5. Get a breakdown of noise service requests by the hour\n" +
                    "6. Exit Noise Service Requests\n" +
                    "------------------------------------------------------------------------");
            System.out.println("What would you like to view?");
            choice = sc.nextInt();
            switch (choice) {
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
                case 6:
                    break;
            }
        }
    }

    //This method displays a summary of the noise service requests from the past year
    public void summary(){
        //Total number of requests
        System.out.println("In total, there were " + dataValues.get("Service Request Description").size() + " noise service requests");
        //Breakdown of the number of requests from each ward
        System.out.println("Here is a brief breakdown of each ward for 2019: ");
        for (int i = 1 ; i <= 11; i++) getWardResults("method", i); //Pass in each ward and get the results
        //Breakdown for each month
        System.out.println("Here is a brief breakdown of each month for 2019: ");
        for (int i = 1; i <= 12; i++) getMonth("method", i);
        System.out.println("Here is a brief breakdown of each street for 2019: ");
        /*
        Breakdown of each street. The significance of the if statement is that there is actually a couple of requests
        had no street name attached to them (probably since the street name was not reported). If we were to pass in
        an empty street name, then it would cause an error in the getStreet() method because it wouldn't be able to
        split by spaces. However, it seems that isEmpty() does not work because the length of the blank street name
        is actually 1, so that's why we have the if statement set up the way we do
         */
        for (int i = 0; i < streets.size(); i++){
            if (streets.get(i).length() > 1) getStreet("method", streets.get(i));
        }


    }

    //This method outputs the results for each ward in the past year
    public void getWardResults(String requestType, int ward){
        String wardName = "WARD " + ward;
        ArrayList<String> wards = dataValues.get("Ward");
        int total = 0;
        for (int i = 0; i < wards.size(); i++){
            if (wards.get(i).equals(wardName)){
                ++total;
            }
        }
        //If the user is the one requesting the data, then there is a little more description to be provided since in
        //this case they will only be requesting that specific ward's data. However, if the request is from a method,
        //then it will be the summary method which requests each ward's data, so the description isn't as much
        if (requestType.equals("user")) System.out.println("In 2019, there were total " + total + " noise service requests in ward " + ward);
        else System.out.println("Ward " + ward + ": " + total);
    }

    //Similar to the previous method, but outputs the results for a month instead of a ward
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

    /*
    This method outputs the results of a given street. However, when implementing this method, we assumed that the
    user will enter an entire street name, such as Hunt Club Crescent, but in the Noise Service Requests database the
    streets appear as just the name, so in this case only Hunt Club.
     */
    public void getStreet(String requestType, String street){
        //First convert the street name to upper case since all the streets are upper case in the database
        String streetName = street.toUpperCase();
        ArrayList<String> allStreets = dataValues.get("Street");
        int total = 0;

        //Since the user can type an entire street name, we use the contains() method to see if the street in the database
        //is a subset of the street entered by the user. We have to check if the current street has length 1 because
        //the street with length 1 is the blank street, and this street will be a substring of any street entered by
        //the user which would produce incorrect results.
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

    /*
    This method returns the number of service requests in each hour throughout the year in descending order. With this
    method, there were two ways to approach it. Either I have a HashMap in which each key is the hour which is connected
    to the total number of requests for that hour. The second approach is to create a helper class in which each object
    takes a string and an integer and almost mimicks a HashMap. The reason why I chose the second approach over the
    first approach is becuase after the values are created, the data structure in which they are stored must be sorted,
    and it really goes against the point of a HashMap to sort it, so I decided to go with the second approach and create
    an ArrayList of these objects and then sort the objects
     */
    public void getHours(){
        //Use the totalTrackers class to
        TotalTracker[] totalTrackers = new TotalTracker[24];
        for (int i = 0; i < 24; i++){
            totalTrackers[i] = new TotalTracker(hours[i]);
        }

        ArrayList<String> times = dataValues.get("Created Date");
        for (int i = 0; i < times.size(); i++){
            String date = times.get(i);
            /*
            Each value in the column has the date followed by the time, so first we need to isolate the time from the
            actual date which is done in the first line. The next line then isolates AM or PM from the time, and the
            third line then isolates the hour from the time since it is in an HH:MM:SS format.
             */
            String[] dateParts = date.split("2019 ");
            String extension = (dateParts[1].split(" "))[1];
            String hour = (dateParts[1].split(":"))[0];
            String trueTime = hour + " " + extension;
            totalTrackers[Arrays.asList(hours).indexOf(trueTime)].total += 1;
        }
        Arrays.sort(totalTrackers);
        System.out.println("Here is the breakdown of the noise requests in 2019 by the hour from most to least: ");
        for (int i = 0; i < 24; i++){
            //If an hour has 0 requests for it, no need to display it, and since the array is sorted, if the current
            //object has 0 requests, then the rest of the objects after it have 0 requests, so we can just break the
            //for loop
            if (totalTrackers[i].total == 0) break;
            System.out.println(totalTrackers[i]);
        }
    }
}

//This helper class helps out primarily with the sorting of totals for particular data such as in the getHour() method
//In order for the objects of this class to be able to sort, we must implement the Comparable interface
class TotalTracker implements Comparable<TotalTracker>{

    public String name;
    public int total;

    public TotalTracker(String name){
        this.name = name;
        total = 0;
    }

    //This is the method implemented from the Comparable interface which determines how to swap two objects of this
    //class when sorting
    @Override
    public int compareTo(TotalTracker o) {
        if (this.total > o.total) return -1;
        else if (this.total < o.total) return 1;
        else return 0;
    }

    public String toString() {
        return name + ": " + total;
    }
}