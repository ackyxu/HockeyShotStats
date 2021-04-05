package ui;

import exceptions.CanucksNotInImport;
import model.LiveData;
import model.MatchData;
import model.StoredMatchData;
import persistence.JsonImport;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Abstract Class to hold all the common methods and fields between the Interfaces Class
public abstract class Interface {
    protected static final String JSON_STORE = "./data/";
    protected static final String JSON_IMPORT = "./data/imports/";
    protected StoredMatchData storedMatchData;
    protected List<String> shotEvents;
    protected String team;
    protected JsonImport matchImport;
    protected String option;


    //EFFECTS: Stats the Console Interface
    public Interface() {

        init();
        this.shotEvents = new ArrayList<>();
        this.shotEvents.add("BLOCKED_SHOT");
        this.shotEvents.add("SHOT");
        this.shotEvents.add("MISSED_SHOT");
        this.shotEvents.add("GOAL");

    }



    // MODIFIES: this
    // EFFECTS: initializes fields
    protected  void init() {
        storedMatchData = new StoredMatchData();
        team = "VAN";
    }

    //MODIFIES: this
    //EFFECTS: Loads the matches stored from the previous section, if any.  Allows user to select which instance they
    //         wish to load in.
    protected void loadStoredMatches(String option)  throws IOException {

        JsonReader reader = new JsonReader(JSON_STORE + option);

        this.storedMatchData = reader.read();


    }

    //MODIFIES: this
    //EFFECTS: Displays the menus option for Loading Stored Matches
    protected abstract void loadOptions();


    //MODIFIES: None
    //EFFECT: Display Main Menu Options
    //NOTE: Make sure to update processMainOptions() when adding new options
    protected abstract void displayMenu();

    //MODIFIES: None
    //Effect: Print out menu options for Importing and Dropping Matches
    protected abstract void displayStoreMatchMenu();



    //MODIFIES: None
    //EFFECT: Display Event Menu Options
    //NOTE: Make sure to update processMainOptions() when adding new options
    protected abstract void displayEventMenu();

    //MODIFIES: None
    //EFFECT: Display Summary Menu Options
    //NOTE: Make sure to update processMainOptions() when adding new options
    protected abstract void displaySummaryMenu();




    //MODIFIES: this
    //EFFECTS: Process importing a NHL matches retrieved from API (currently, only local cache files in JSON)
    protected void processImportMatch(String file) {

        matchImport = new JsonImport(file);

        try {
            this.storedMatchData.addMatchData(matchImport.read());

        } catch (IOException e) {
            System.out.println("Error: File not found \n please check the name of the file you fish to import");
        } catch (CanucksNotInImport canucksNotInImport) {
            System.out.println("Oops! Looks like the Canucks did not play in that game!");
        }

        System.out.println("\nGreat Success!\n");
    }


    //MODIFIES: this.storedMatchData
    //EFFECT: Drops Selected Match
    protected abstract void dropMatch();


    //MODIFIES: None
    //EFFECT: Retrieved filtered event base on the give EventType S from give MatchData, then return the events as
    //        processed Strings
    //Part of a Method Overload, to allowed a single EventType, or list of EventTypes
    protected abstract List<String> retrieveEvents(MatchData match, String s);



    //MODIFIES: None
    //EFFECT: Retrieved filtered event base on the give EventTypes los from give MatchData, then return the events as
    //        processed Strings
    //Part of a Method Overload, to allowed a single EventType, or list of EventTypes
    protected abstract List<String> retrieveEvents(MatchData match, List<String> los);

    //MODIFIES: None
    //EFFECT: convert a LiveData into an String that will be printed onto the console
    protected String retrieveEvent(LiveData l) {

        String coor = "x: " + l.getCoorX() + " , y: " + l.getCoorY();
        String line1 = l.getPeriod() + " " + l.getPeriodType() + " " + l.getPeriodTime() + "\n";
        String line2 = l.getPlayer0() + " " + l.getPlayer0Type() + " | " + l.getPlayer1() + " " + l.getPlayer1Type()
                + "\n";
        String line3 = l.getEvent() + " " + l.getEventType() + " " + l.getTeam() + "\n";

        String line4 = l.getDetail() + " " + l.getPlayer0() + " @ " + "(" + coor + ") ";
        return line1 + line2 + line3 + line4 + "\n";

    }






    //REQUIRES: this.storedMatchData != null
    //MODIFIES: None
    //EFFECT: Retrieve select Matches base on matchID

    protected abstract List<MatchData> retrieveSelectedMatches();



    //REQUIRE: this.storedMatchData != null
    //MODIFIES: None
    //EFFECT: Prints out the IDs of all the matches in storedMatchData
    protected abstract void printStoreMatchID();

    //REQUIRE: i must be in this.storedMatchIDs
    //MODIFIES: None
    //EFFECT: retrieve matches with the same matchID as the given integer
    protected MatchData retrieveMatchByID(Integer i) {

        MatchData match = null;

        for (MatchData m : this.storedMatchData.getStoredMatches()) {
            if (m.compareMatchID(i)) {
                match = m;
                break;

            }

        }

        return match;
    }



    //REQUIRE: storedMatchData.size() != 0
    //MODIFIES: None
    //EFFECT: Prints out a Summary of all the imported Matches in system out
    protected void processMatchSummary() {
        for (MatchData m : this.storedMatchData.getStoredMatches()) {
            List<Integer> counts = countShotEvents(m);
            Integer block = counts.get(0);
            Integer shots = counts.get(1);
            Integer missed = counts.get(2);
            Integer goal = counts.get(3);

            System.out.println("\nHome Team: " + m.getGameData().getHome().getTeamName() + " "
                    + m.getGameData().getHome().getTeamAbr());
            System.out.println("Away Team: " + m.getGameData().getAway().getTeamName() + " "
                    + m.getGameData().getAway().getTeamAbr());
            System.out.println(" ");
            System.out.println("Date: " + m.getMatchDate() + " ID: " + m.getMatchID());
            System.out.println("Cancuks:");
            System.out.println("Blocked Shots:" + block + "  Shots On Net:" + shots + "  Missed Shots:" + missed);
            System.out.println("Goals: " + goal + "\n");

        }
    }


    //MODIFIES: None
    //EFFECT: Count the number of event that matches the ones defined in shotEvents.
    protected List<Integer> countShotEvents(MatchData m) {
        List<Integer> counts = new ArrayList<>();
        for (String s : this.shotEvents) {
            Integer i = retrieveEvents(m, s).size();

            counts.add(i);
        }

        return counts;

    }



    protected abstract void saveStoreMatches();


}






