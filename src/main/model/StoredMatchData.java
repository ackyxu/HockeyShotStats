package model;

import java.util.ArrayList;
import java.util.List;

//The Class that stores all the MatchData imported into the program, with a list of matchIDs
//REQUIRE: size of storedMatches and matchID is the same at all times, as it is a tandem list to represent the stored
//         MatchData and the matches' matchID

public class StoredMatchData {

    private List<MatchData> storedMatches;
    private List<Integer> matchIDs;

    //REQUIRE: None
    //EFFECT: Construct a new StoredMatchData with both field being an empty list.
    public StoredMatchData() {
        List<MatchData> storedMatches = new ArrayList<>();
        List<Integer> matchIDs = new ArrayList<>();
        this.storedMatches = storedMatches;
        this.matchIDs = matchIDs;
    }

    public List<MatchData> getStoredMatches() {
        return storedMatches;
    }


    public List<Integer> getMatchIDs() {
        return matchIDs;
    }

    //MODIFIES: This
    //EFFECT: Add a MatchData to StoredMatchData
    public void addMatchData(MatchData m) {
        this.storedMatches.add(m);
        this.refreshStoredID();
    }

    //MODIFIES: This
    //EFFECT: Drop a MatchData to StoredMatchData
    public void dropMatchData(MatchData m) {
        this.storedMatches.remove(m);
        this.refreshStoredID();
    }

    //MODIFIES: None
    //EFFECT: Return the size of storedMatches

    public Integer storedSize() {
        return this.storedMatches.size();
    }

    //REQUIRE: the given i is in the same format as MatchData.matchId
    //MODIFIES: None
    //EFFECT: Return True if our StoredMatchData Contains the given match ID
    public boolean checkContainMatchID(Integer i) {
        return this.matchIDs.contains(i);
    }

    //MODIFIES: this
    //EFFECT: Refresh the storeMatchIDs to ensure that it only contains the ones in the storeMatchData
    private void refreshStoredID() {

        this.matchIDs = new ArrayList<>();

        for (MatchData m : storedMatches) {
            this.matchIDs.add(m.getMatchID());
        }

    }


}
