import java.util.HashMap;

public class TempTitle {

    private String typeName;
    private HashMap<String, Integer> results; //hashmap to store candidate and votes

    public TempTitle(String typeName) {
        this.typeName = typeName;
        results = new HashMap<String, Integer>();
    }

    //Creates new candidate
    public void addCandidate(String name){
        results.put(name, 0);
    }

    //Adds the total votes of the candidate
    public void addResults(String name, int totalVotes){
        results.put(name, results.get(name) + totalVotes);
    }

    public String getName(){
        return typeName;
    }

    //Returns the HashMap
    public HashMap getHashMap(){
        return results;
    }
}
