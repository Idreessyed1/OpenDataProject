import java.util.ArrayList;
import java.util.HashMap;

public abstract class DataTemplate {
    private String dataDesc;
    private String dataName;
    protected HashMap<String, ArrayList<String>> dataValues;
    public abstract void showOptions();

    public DataTemplate(String dataDesc, String dataName, String workbookName) {
        this.dataDesc = dataDesc;
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

    public String getDataName(){
        return dataName;
    }
    public String getDataDesc() {
        return dataDesc;
    }
}
