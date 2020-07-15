import java.util.Scanner;

public class MenuInterface {
    public MenuInterface(){
    }

    public void menuControl(){
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream.
        System.out.println("Here are the databases that you can choose from: \n" +
        "1. Elections 2018\n" + "2. Fire Stations\n" + "3. Noice Service Request\n4. Exit");

        System.out.print("Which database would you like to see (or press 4 to exit): ");

        int choice = sc.nextInt();

        switch(choice){
            case 1:
                Elections2018 elections2018 = new Elections2018();
                elections2018.showOptions();
                break;
            case 3:
                NoiseRequests noiseRequests = new NoiseRequests();
                noiseRequests.showOptions();
                break;
            case 4:
                break;
        }
    }
}
