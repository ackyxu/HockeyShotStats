package model;

import java.util.ArrayList;
import java.util.List;

public class StoredMatchData {

    private List<MatchData> storedMatches;
    private List<Integer> matchIDs;

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
