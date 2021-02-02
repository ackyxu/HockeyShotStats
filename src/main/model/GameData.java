package model;

//Sub-object of the parse JSON file, object > gameData
//Contains information about the match being played
//GameData contains multiple fields, but it will only store information on home and away team name
public class GameData {
    private Team home;
    private Team away;

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
}
