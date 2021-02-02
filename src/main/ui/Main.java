package ui;

import model.Team;

public class Main {
    public static void main(String[] args) {
        Integer id = 0;
        String name = "Nucks";
        String abv = "NUC";
        Team team = new Team(id,name,abv);

        System.out.println(team.getTeamNumber());


    }
}
