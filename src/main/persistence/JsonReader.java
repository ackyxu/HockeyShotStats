package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//Imports in a previous session from a JSON file
public class JsonReader extends JsonReaders {

    private String source;


    public JsonReader(String source) {

        this.source = source;
    }

    //EFFECT: construct a MatchData from the import file
    //        throws IOException if there is an error reading the file

    public StoredMatchData read() throws IOException {

        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStoredMatchData(jsonObject);


    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: convert a NHL API JSON file to a StoredMatchData
    private StoredMatchData parseStoredMatchData(JSONObject json) {
        StoredMatchData storedMatchData = new StoredMatchData();

        JSONArray storeMatchesArray = json.getJSONArray("storedMatches");

        for (Object match: storeMatchesArray) {

            JSONObject nextMatch = (JSONObject) match;

            storedMatchData.addMatchData(parseMatchData(nextMatch));


        }

        return storedMatchData;

    }

    //EFFECTS: convert a NHL API JSON file a MatchData
    private MatchData parseMatchData(JSONObject jsonObject) {


        GameData gameData = parseGameData(jsonObject);
        List<LiveData> listLiveData = parseLiveDataList(jsonObject);
        Integer matchID = parseInteger(jsonObject, "matchID");
        String matchDate = parseString(jsonObject, "matchDate");


        return new MatchData(gameData, listLiveData, matchID, matchDate);
    }

    //EFFECTS: convert a JSONArray from NHL API JSON  to List<LiveData>
    private List<LiveData> parseLiveDataList(JSONObject jsonObject) {

        List<LiveData> liveDataList = new ArrayList<>();

        JSONArray liveDataJsonArray = jsonObject.getJSONArray("allLiveData");

        for (Object json: liveDataJsonArray) {

            JSONObject nextLiveData = (JSONObject) json;

            liveDataList.add(parseLiveData(nextLiveData));

        }

        return liveDataList;
    }

    //EFFECTS: convert a JSONObject from NHL API JSON to LiveData
    private LiveData parseLiveData(JSONObject nextLiveData) {

        String player0 = parseString(nextLiveData, "player0");
        String player0Type = parseString(nextLiveData, "player0Type");
        String player1 = parseString(nextLiveData, "player1");
        String player1Type = parseString(nextLiveData, "player1Type");
        String team = parseString(nextLiveData, "team");
        String detail = parseString(nextLiveData, "detail");
        String event = parseString(nextLiveData, "event");
        String eventType = parseString(nextLiveData, "eventType");
        Integer period = parseInteger(nextLiveData, "period");
        String periodType = parseString(nextLiveData, "periodType");
        LocalTime periodTime = LocalTime.parse(parseString(nextLiveData, "periodTime"));
        Integer coorX = parseInteger(nextLiveData, "coorX");
        Integer coorY = parseInteger(nextLiveData, "coorY");


        return new LiveData(player0, player0Type,  player1,  player1Type, team,  detail,  event,
                eventType,  period, periodType,  periodTime,  coorX,  coorY);
    }

    //EFFECTS: convert a JSONObject from NHL API JSON to a GameData
    private GameData parseGameData(JSONObject jsonObject)  {

        JSONObject gameDataObject = jsonObject.getJSONObject("gameData");


        Team homeTeam;
        Team awayTeam;

        homeTeam = parseTeam(gameDataObject, "home");
        awayTeam = parseTeam(gameDataObject, "away");


        return new GameData(homeTeam, awayTeam);

    }

    //EFFECTS: convert a JSONObject from NHL API JSON to a Team
    private Team parseTeam(JSONObject jsonObject, String status) {

        JSONObject teamObject = getObject(jsonObject,status);

        String name = teamObject.getString("name");
        String abr = teamObject.getString("abr");

        return new Team(name, abr);

    }


}
