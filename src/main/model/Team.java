package model;

//Class object for Team information retrieve from JSON object > gameData > teams > away and home
public class Team {
    //Team Name
    private final String teamName;
    // Abbreviation of Team Name
    private final String teamAbr;


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

}
