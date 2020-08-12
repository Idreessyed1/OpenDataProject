import java.util.ArrayList;
import java.util.HashMap;

//This class is the abstract class in which all the features that each of the databases share are stored
//such as a description of the database, the name of the database, etc.
public abstract class DataTemplate {

    private String dataDesc; //The description of the current database
    private String dataName; //The name of the current database
    protected boolean userView;
    protected HashMap<String, ArrayList<String>> dataValues; //All the values extracted from the database

    //Constructor that takes in the database name and the name of the workbook inside of the database
    public DataTemplate(String dataName, String workbookName, boolean userView) {
        this.dataName = dataName;
        this.userView = userView;
        this.dataValues = new HashMap<>();
        try {
            Extractor e = new Extractor(dataName, workbookName); //Calls on the Extract to extract info from the specified database
            dataValues = e.extractInfo();
        }
        catch(Exception e){
            System.out.println("File not found");
        }
    }

    //Setter for the database description; will be important later on in each of the database classes
    public void setDataDesc(String description){
        this.dataDesc = description;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataName(){
        return dataName;
    }


    //The abstract method that each of the databases will implement. This method will display all the
    //possible data that the user can extract from the database they they can then choose from
    public abstract void showOptions();
}