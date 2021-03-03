


package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonMethods;

import java.util.ArrayList;
import java.util.List;

//Object for the JSON to parse to
public class MatchData implements JsonMethods {
    private GameData gameData;
    private List<LiveData> allLiveData;
    //The offical ID for the match, per NHL API
    private Integer matchID;
    //For filtering games withing certain date
    private String matchDate;

    //REQUIRES: None
    //EFFECT: Construct a MatchData
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

    //REQUIRES: an integer that represent a matchID, or in the same format
    //MODIFIES: none
    //EFFECT: return true if the given integer matches the matchID
    public boolean compareMatchID(Integer i) {
        return this.getMatchID().equals(i);
    }



    //REQUIRE: given filter is in the same format as GameData.Team.teamAbr, s is an EventType found in LiveData
    //MODIFIES: None
    //Returns a filter list of LiveData from this.allLiveData base on the given criteria
    //Part of a method overload to allow a single EventType filter, or a list of EventType
    public List<LiveData> getFilteredEvent(String filter, String s) {

        List<LiveData> events = new ArrayList<>();

        for (LiveData l : this.getAllLiveData()) {
            if (l.filterEvent(filter, s)) {
                events.add(l);
            }
        }

        return events;


    }


    //REQUIRE: given filter is in the same format as GameData.Team.teamAbr, los is list with  EventType found in
    //         LiveData
    //MODIFIES: None
    //EFFECT: Returns a filter list of LiveData from this.allLiveData base on the given criteria
    //Part of a method overload to allow a single EventType filter, or a list of EventType
    public List<LiveData> getFilteredEvent(String filter, List<String> los) {

        List<LiveData> events = new ArrayList<>();

        for (LiveData l : this.getAllLiveData()) {
            if (l.filterEvent(filter, los)) {
                events.add(l);
            }
        }

        return events;


    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("gameData", this.gameData.toJson());
        json.put("allLiveData", liveDataToJson());
        json.put("matchID", this.matchID);
        json.put("matchDate", this.matchDate);


        return json;
    }

    private JSONArray liveDataToJson() {

        JSONArray array = new JSONArray();

        for (LiveData l: this.allLiveData) {

            array.put(l.toJson());

        }

        return array;
    }
}
