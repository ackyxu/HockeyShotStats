package model;

//Class object for Team information retrieve from JSON object > gameData > teams > away and home
public class Team {
    //Team ID
    private final Integer teamNumber;
    //Team Name
    private final String teamName;
    // Abbreviation of Team Name
    private final String teamAbr;


    public Team(Integer id, String name, String abr) {
        this.teamNumber = id;
        this.teamName = name;
        this.teamAbr = abr;
    }

    public int getTeamNumber() {
        return this.teamNumber;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public String getTeamAbr() {
        return this.teamAbr;
    }

}
