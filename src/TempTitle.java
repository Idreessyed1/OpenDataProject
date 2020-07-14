import java.util.HashMap;

public class TempTitle {
    private String typeName;
    private HashMap<String, Integer> results;

    public TempTitle(String typeName) {
        this.typeName = typeName;
        results = new HashMap<String, Integer>();
    }

    //Creates new candidate
    public void addCandidate(String name){
        results.put(name, 0);
    }

    public void addResults(String name, int totalVotes){
        results.put(name, results.get(name) + totalVotes);
    }

    public String getName(){
        return typeName;
    }

    public HashMap getHashMap(){
        return results;
    }
}
