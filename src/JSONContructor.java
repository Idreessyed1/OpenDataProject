import org.json.simple.*;
import java.io.FileWriter;
import java.io.IOException;

public class JSONContructor {

    FileWriter file;

    public JSONContructor(){
    }

    public JSONObject createJSONObject(){
        JSONObject jsonObject  = new JSONObject();
        return jsonObject;
    }

    public JSONObject putJSONObject(JSONObject jsonObject, String data1, String data2){
        jsonObject.put(data1, data2);
        return jsonObject;
    }

    public void putJSONObject(JSONObject jsonObject, JSONArray jsonArray, String arrayName){
        jsonObject.put(arrayName, jsonArray);
    }

    public JSONArray createJSONArray(){
        JSONArray jsonArray = new JSONArray();
        return jsonArray;
    }

    public void addToJSONArray(JSONArray jsonArray, JSONObject jsonObject){
        jsonArray.add(jsonObject);
    }

    public void addToJSONArray(JSONArray jsonArray, String data){
        jsonArray.add(data);
    }

    public void writeToFile(JSONObject jsonObject, String fileName) {
        try {
            file = new FileWriter(fileName+".json");
            file.write(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
