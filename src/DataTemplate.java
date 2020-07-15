import java.util.ArrayList;
import java.util.HashMap;

public abstract class DataTemplate {

    private String dataDesc;
    private String dataName;
    protected HashMap<String, ArrayList<String>> dataValues;

    public DataTemplate(String dataName, String workbookName) {
        this.dataName = dataName;
        dataValues = new HashMap<>();
        try {
            Extractor e = new Extractor(dataName, workbookName);
            dataValues = e.extractInfo();
        }
        catch(Exception e){
            System.out.println("File not found");
        }
    }

    public void setDataDesc(String description){
        this.dataDesc = description;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataName(){
        return dataName;
    }

    public abstract void showOptions();
}