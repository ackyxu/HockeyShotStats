package model;

import java.util.List;

//Object for the JSON to parse to
public class MatchData {
    private GameData gameData;
    private List<LiveData> allLiveData;

    public MatchData(GameData gameData, Team team, List<LiveData> allLiveData) {
        this.gameData = gameData;
        this.allLiveData = allLiveData;

    }

    public GameData getGameData() {
        return gameData;
    }



    public List<LiveData> getAllLiveData() {
        return allLiveData;
    }
}
