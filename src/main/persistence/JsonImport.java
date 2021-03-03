package persistence;

import exceptions.CanucksNotInImport;
import model.GameData;
import model.LiveData;
import model.MatchData;
import model.Team;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//derived from JsonSerializationDemo
//Imports the match data from NHL API's JSON returns
public class JsonImport extends JsonReaders {

    private String source;

    //EFFECTS: create imported to read from the given source file
    public JsonImport(String source) {

        this.source = source;
    }

    //EFFECT: construct a MatchData from the import file
    //        throws IOException if there is an error reading the file

    public MatchData read() throws IOException, CanucksNotInImport {

        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMatchData(jsonObject);


    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private MatchData parseMatchData(JSONObject jsonObject) throws CanucksNotInImport {


        GameData gameData = parseGameData(jsonObject);
        List<LiveData> listLiveData = parseLiveDataList(jsonObject);
        Integer matchID = parseInteger(jsonObject, "gamePk");
        JSONObject gameDataObject = jsonObject.getJSONObject("gameData").getJSONObject("datetime");
        String matchDate = gameDataObject.getString("dateTime").substring(0,10);


        return new MatchData(gameData, listLiveData, matchID, matchDate);
    }

    private List<LiveData> parseLiveDataList(JSONObject jsonObject) {

        List<LiveData> liveDataList = new ArrayList<>();

        JSONArray liveDataJsonArray = jsonObject.getJSONObject("liveData")
                .getJSONObject("plays")
                .getJSONArray("allPlays");

        for (Object json: liveDataJsonArray) {

            JSONObject nextLiveData = (JSONObject) json;

            liveDataList.add(parseLiveData(nextLiveData));

        }

        return liveDataList;
    }

    private LiveData parseLiveData(JSONObject nextLiveData) {

        JSONObject result = getObject(nextLiveData,"result");
        JSONObject about = getObject(nextLiveData, "about");
        JSONObject coordinates = getObject(nextLiveData,"coordinates");

        String player0 = getPlayerLiveData(nextLiveData, 0, "player");
        String player0Type = getPlayerLiveData(nextLiveData, 0, "playerType");
        String player1 = getPlayerLiveData(nextLiveData, 1, "player");
        String player1Type = getPlayerLiveData(nextLiveData, 1, "playerType");
        String team = parseString(getObject(nextLiveData, "team"), "triCode");
        String detail = parseString(result, "description");
        String event = parseString(result, "event");
        String eventType = parseString(result, "eventTypeId");
        Integer period = parseInteger(about, "period"); //Integer.parseInt(parseString(about, "period"));
        String periodType = parseString(about, "periodType");
        LocalTime periodTime = LocalTime.parse(parseString(about, "periodTime"));
        Integer coorX = parseInteger(coordinates, "x");
        Integer coorY = parseInteger(coordinates, "y");


        return new LiveData(player0, player0Type,  player1,  player1Type, team,  detail,  event,
                eventType,  period, periodType,  periodTime,  coorX,  coorY);
    }

    private GameData parseGameData(JSONObject jsonObject) throws CanucksNotInImport {

        JSONObject gameDataObject = jsonObject.getJSONObject("gameData").getJSONObject("teams");


        Team homeTeam;
        Team awayTeam;

        homeTeam = parseTeam(gameDataObject, "home");
        awayTeam = parseTeam(gameDataObject, "away");

        if (!awayTeam.getTeamName().equals("Canucks") && !homeTeam.getTeamName().equals("Canucks")) {
            throw new CanucksNotInImport();
        }

        return new GameData(homeTeam, awayTeam);

    }

    private Team parseTeam(JSONObject jsonObject, String status) {

        JSONObject teamObject = getObject(jsonObject,status);

        String name = teamObject.getString("teamName");
        String abr = teamObject.getString("abbreviation");

        return new Team(name, abr);





    }


    private String getPlayerLiveData(JSONObject jsonObject, int index, String object) {
        JSONObject json;
        try {
            json =  jsonObject.getJSONArray("players").getJSONObject(index);

        } catch (JSONException e) {
            return " ";
        }

        if (object.equals("player")) {

            return parseString(getObject(json, object), "fullName");
        } else {
            return parseString(json, "playerType");
        }
    }



}
