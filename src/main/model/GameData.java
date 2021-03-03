package model;

import org.json.JSONObject;
import persistence.JsonMethods;

//Sub-object of the parse JSON file, object > gameData
//Contains information about the match being played
//GameData contains multiple fields, but it will only store information on home and away team name
public class GameData implements JsonMethods {
    private Team home;
    private Team away;

    //EFFECT: construct a GameData
    public GameData(Team home, Team away) {
        this.home = home;
        this.away = away;
    }

    public Team getHome() {
        return home;
    }

    public Team getAway() {
        return away;
    }

    @Override
    public Object toJson() {
        JSONObject json = new JSONObject();

        json.put("home", this.home.toJson());
        json.put("away", this.away.toJson());

        return json;
    }


}
