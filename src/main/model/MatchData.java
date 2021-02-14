//CONSIDER FOR DELETION


package model;


import java.util.ArrayList;
import java.util.List;

//Object for the JSON to parse to
public class MatchData {
    private GameData gameData;
    private List<LiveData> allLiveData;
    //The offical ID for the match, per NHL API
    private Integer matchID;
    //For filtering games withing certain date
    private String matchDate;

    public MatchData(GameData gameData, List<LiveData> allLiveData, Integer matchID, String matchDate) {
        this.gameData = gameData;
        this.allLiveData = allLiveData;
        this.matchID = matchID;
        this.matchDate = matchDate;

    }

    public GameData getGameData() {
        return gameData;
    }


    public List<LiveData> getAllLiveData() {
        return allLiveData;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public Integer getMatchID() {
        return matchID;
    }
    //REQUIRES: an integer
    //MODIFIES: none
    //EFFECT: return true if the given integer matches the matchID

    public boolean compareMatchID(Integer i) {
        return this.getMatchID().equals(i);
    }

    public List<LiveData> getFilteredEvent(String filter, String s) {

        List<LiveData> events = new ArrayList<>();

        for (LiveData l : this.getAllLiveData()) {
            if (l.filterEvent(filter, s)) {
                events.add(l);
            }
        }

        return events;


    }

    public List<LiveData> getFilteredEvent(String filter, List<String> los) {

        List<LiveData> events = new ArrayList<>();

        for (LiveData l : this.getAllLiveData()) {
            if (l.filterEvent(filter, los)) {
                events.add(l);
            }
        }

        return events;


    }
}
