package ui;

import model.GameData;
import model.Team;

public class Main {

    static Team team0;
    static Team team1;
    static GameData gameData;


    public static GameData createGame() {

        team0 = new Team(24, "Canucks", "VAN");
        team1 = new Team(12, "Oilers", "EDM");
        gameData = new GameData(team0, team1);

        return gameData;

    }

    public static void matchInfo(GameData game) {
        String homeName;
        String awayName;
        String homeAbr;
        String awayAbr;

        homeName = game.getHome().getTeamName();
        awayName = game.getAway().getTeamName();
        homeAbr = game.getHome().getTeamAbr();
        awayAbr = game.getAway().getTeamAbr();

        System.out.println("The " + homeName + " (" + homeAbr + ") " + "vs the " + awayName + " (" + awayAbr + ")");


    }


    public static void main(String[] args) {

        GameData game;
        game = createGame();

        matchInfo(game);

    }
}
