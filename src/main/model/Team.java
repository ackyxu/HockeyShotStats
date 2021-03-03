package model;

import org.json.JSONObject;
import persistence.JsonMethods;

//Class object for Team information retrieve from JSON object > gameData > teams > away and home
public class Team implements JsonMethods {
    //Team Name
    private final String teamName;
    // Abbreviation of Team Name
    private final String teamAbr;

    //REQUIRE: None (Format enforced by the parse JSON)
    //EFFECT: Construct a new Team
    public Team(String name, String abr) {
        this.teamName = name;
        this.teamAbr = abr;
    }


    public String getTeamName() {
        return this.teamName;
    }

    public String getTeamAbr() {
        return this.teamAbr;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", this.teamName);
        json.put("abr", this.teamAbr);

        return json;
    }
}
