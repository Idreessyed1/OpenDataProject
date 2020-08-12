import javax.xml.crypto.Data;
import java.util.Scanner;

public class MenuInterface {

    private boolean userView;

    public MenuInterface(){
    }

    public void chooseView(){
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream.
        int choice = 0;
        while (choice < 3) {
            System.out.println("How would you like to interact with the databases: \n" +
                    "1. User Response\n" +
                    "2. JSON Response \n" +
                    "3. Exit");
            System.out.print("How would you like to interact with the databases: ");
            choice = sc.nextInt(); //User selects the option they wish
            switch (choice) {
                case 1:
                    userView = true;
                    chooseDataBase();
                    break;
                case 2:
                    userView = false;
                    chooseDataBase();
                case 3:
                    System.out.println("Have a good day!"); //Exits the program
                    break;
            }
        }
    }

    //This method will display the menu two the user
    //It will keep looping until 4 is entered
    public void chooseDataBase() {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream.
        int choice = 0;
        while (choice < 4) {
            System.out.println("Here are the databases that you can choose from: \n" +
                    "1. Elections 2018\n" +
                    "2. Fire Stations\n" +
                    "3. Noise Service Request\n" +
                    "4. Return");
            System.out.print("Which database would you like to see (or press 4 to exit): ");
            choice = sc.nextInt(); //User selects the option they wish
            switch (choice) {
                case 1:
                    Elections2018 elections2018 = new Elections2018(userView); //Creates an instance of the Elections2018 class
                    elections2018.showOptions();
                    break;
                case 2:
                    FireStation fireStation = new FireStation(userView); //Creates an instance of the FireStation  class
                    fireStation.showOptions();
                    break;
                case 3:
                    NoiseRequests noiseRequests = new NoiseRequests(userView); //Creates an instance of the NoiseRequests class
                    noiseRequests.showOptions();
                    break;
                case 4:
                    break;
            }
        }
    }
}