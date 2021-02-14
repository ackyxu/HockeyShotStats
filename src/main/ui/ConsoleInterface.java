package ui;

import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;




public class ConsoleInterface {
    StoredMatchData storedMatchData;
    List<String> shotEvents;
    private Scanner input;

    public ConsoleInterface() throws IOException {

        String option;
        boolean keepgoing = true;
        init();
        while (keepgoing) {

            displayMenu();

            option = this.input.next();

            keepgoing = processMainOptions(option);

        }

    }

    // MODIFIES: this
    // EFFECTS: initializes fields
    private void init() {
        this.input = new Scanner(System.in);
        storedMatchData = new StoredMatchData();


        this.shotEvents = new ArrayList<>();
        this.shotEvents.add("BLOCKED_SHOT");
        this.shotEvents.add("SHOT");
        this.shotEvents.add("MISSED_SHOT");
        this.shotEvents.add("GOAL");
    }

    //MODIFIES: None
    //EFFECT: Process the options selected from displayMenu() in Console Interface
    //NOTE: Make sure to update displayMenu() when adding new options
    private boolean processMainOptions(String option) throws IOException {
        boolean keepgoing = true;
        if (option.equals("1")) {

            processStoredMatchOptions();

        } else if (option.equals("2")) {
            processEventOptions();

        } else if (option.equals("3")) {
            processSummaryOptions();

        } else if (option.equals("4")) {

            System.out.println("\nShutting Down");

            keepgoing = false;

        } else {
            System.out.println("Unrecognized Input");

        }

        return keepgoing;
    }

    //MODIFIES: None
    //EFFECT: Display  Menu Options
    //NOTE: Make sure to update processMainOptions() when adding new options
    private void displayMenu() {
        menuQuestion();
        System.out.println("1: Import/Drop Match");
        System.out.println("2: Open Events Menu");
        System.out.println("3: Matches Summary Menu");
        System.out.println("4: Exit");

    }

    private void displayStoreMatchMenu() {
        menuQuestion();
        System.out.println("1: Import Match");
        System.out.println("2: Drop Match");
        System.out.println("3: Return to Main Menu");

    }

    private void processStoredMatchOptions() {
        displayStoreMatchMenu();
        String option = this.input.next();

        if (option.equals("1")) {

            processMatch();

        } else if (option.equals("2")) {

            dropMatch();

        } else if (option.equals("3")) {

            System.out.println("\n");

        } else {
            System.out.println("Unrecognized Input");
        }


    }


    //MODIFIES: None
    //EFFECT: Opens and process the submenu for events related options

    private void processEventOptions() {

        displayEventMenu();
        String option = this.input.next();

        if (option.equals("1")) {

            processEvents("GOAL");

        } else if (option.equals("2")) {

            processEvents(shotEvents);

        } else if (option.equals("3")) {

            System.out.println("\n");

        } else {
            System.out.println("Unrecognized Input");
        }


    }

    //MODIFIES: None
    //EFFECT: Display Event Menu Options
    //NOTE: Make sure to update processMainOptions() when adding new options
    private void displayEventMenu() {
        menuQuestion();
        System.out.println("1: Return Goals Scored");
        System.out.println("2: Return Shot Events");
        System.out.println("3: Return to Main Menu");

    }

    //MODIFIES: None
    //EFFECT: Display Summary Menu Options
    //NOTE: Make sure to update processMainOptions() when adding new options
    private void displaySummaryMenu() {
        menuQuestion();
        System.out.println("1: List All Matches Imported");
        System.out.println("2: Summary of Imported Matches");
        System.out.println("3: Return to Main Menu");

    }

    private void processSummaryOptions() {

        displaySummaryMenu();
        String option = this.input.next();

        if (option.equals("1")) {

            printStoreMatchID();

        } else if (option.equals("2")) {

            processMatchSummary();

        } else if (option.equals("3")) {

            System.out.println(" ");

        } else {
            System.out.println("Unrecognized Input");
        }


    }


    //MODIFIES: NONE
    //EFFECT: Returns a parsed game match that has been:
    //Checked to Make sure the game hasn't already been parsed
    //Checked to make sure it is only parsing a Cancuks game
    //In Phase 1, this will take in a filePatch, but it will NOT parse a JSON file
    //This program will, for now, create a game from few predefined data for testing

    //TODO add a placeholder CSV parser to test the method
    private MatchData parseMatch(String filePath) throws IOException {

        //TODO in phase 2: implement JSON Parser to retrieve the required data to construct:
        // - Add a dummy text test file that contains team names, to test if it rejects duplicate games
        //   and to detect if the game contains the Canucks
        // - List<LiveData> and the individual LiveData
        // - GameData


        //In Phase 2, the variables will be formed by the JSON Parser.
        //For Now, to test the method, it will parse text files

        List<String> matchText = parseText(filePath);
        String homeTeamName = matchText.get(0);
        String homeTeamAbr = matchText.get(1);
        String awayTeamName = matchText.get(2);
        String awayTeamAbr = matchText.get(3);
        Integer matchID = Integer.parseInt(matchText.get(4));
        String matchDate = matchText.get(5);

        if (!awayTeamName.equals("Canucks") && !homeTeamName.equals("Canucks")) {
            System.out.println("The match does not contain the Canucks");
            return null;
        }


        Team homeTeam = new Team(homeTeamName, homeTeamAbr);
        Team awayTeam = new Team(awayTeamName, awayTeamAbr);

        GameData gameData = new GameData(homeTeam, awayTeam);

        List<LiveData> events = parseListLiveData(matchText.subList(6, (matchText.size())));

        return new MatchData(gameData, events, matchID, matchDate);


    }


    //For Testing Purpose Only, use to parse the test Files
    //Placeholder until JSON parser is implemented
    //MODIFIES: None
    //EFFECTS: Parse a test game file and returns a List<String> with the lines in the file.
    private List<String> parseText(String filePath) throws IOException {

        List<String> strs = new ArrayList<>();

        FileReader input;
        input = new FileReader(filePath);

        //Code from https://stackoverflow.com/questions/15695984/java-print-contents-of-text-file-to-screen
        try (BufferedReader br = new BufferedReader(input)) {
            String line;
            while ((line = br.readLine()) != null) {
                strs.add(line);
            }
        }

        return strs;
    }

    //For Testing Purpose in Phase 1 Only, use to parse the event data from the text file make convert it to a
    //List<LiveData>.  In Phase 2, these will use a JSON parser to parse the ArrayList (JSON) that represent all the
    //game events in the Match JSON file.
    //MODIFIES: None
    //EFFECTS: Parse the given List<String> into List<LiveData>

    private List<LiveData> parseListLiveData(List<String> lstr) {
        List<LiveData> allLiveData = new ArrayList<>();

        for (String s : lstr) {
            allLiveData.add(parseLiveData(s));

        }

        return allLiveData;

    }
    //REQUIRED: String is comma separated by this, with the data in the follow sequence that represents
    // fields in LiveData:
    //player0,player0Type,player1,player1Type,team,detail,event,eventType,period,periodType,periodTime,coorX,coorY
    //MODIFIES: None
    //EFFECT: create a LiveData from the given String

    private LiveData parseLiveData(String str) {

        List<String> s = Arrays.asList(str.split(","));


        return new LiveData(s.get(0), s.get(1), s.get(2), s.get(3), s.get(4), s.get(5),
                s.get(6), s.get(7), Integer.parseInt((s.get(9)).substring(0, 1)),
                s.get(8), LocalTime.parse(s.get(10)),
                Integer.parseInt(s.get(11)), Integer.parseInt(s.get(12)));
    }


    //MODIFIES: this.storedMatchData
    //EFFECT: parse a file containing a MatchData (and its other classes that makes up a MatchData) into storedMatchData
    private void processMatch() {

        System.out.println("Enter the file name");
        System.out.println("For Phase 1, there are two test parse file in the Project:");
        System.out.println("test.txt");
        System.out.println("test2.txt");
        String file = input.next() + "/";
        MatchData matchData;
        try {
            matchData = parseMatch(file);

            assert matchData != null;
            if (this.storedMatchData.checkContainMatchID(matchData.getMatchID())) {
                System.out.println("Error: Match Already Imported");
            } else {
                System.out.println("Match Imported!");
                System.out.println("Game Played on " + matchData.getMatchDate() + " ID: " + matchData.getMatchID());
                storedMatchData.addMatchData(matchData);


            }

        } catch (IOException e) {
            System.out.println("Error: File not Found/Does Not Contain the Cancuks");
        }


    }


    //MODIFIES: this.storedMatchData
    //EFFECT: Drops Selected Match
    private void dropMatch() {
        MatchData match = null;
        if (this.storedMatchData.storedSize() == 0) {
            System.out.println("Error: Currently not Matches are imported");
        } else {
            System.out.println("Enter the ID of the Match you wish to drop");
            Integer id = Integer.parseInt(input.next());

            if (!this.storedMatchData.checkContainMatchID(id)) {
                System.out.println("The Match was not imported/ID was entered incorrectly");
            }

            for (MatchData m : this.storedMatchData.getStoredMatches()) {


                if (m.getMatchID().equals(id)) {
                    System.out.println("Match dropped " + m.getMatchDate() + " ID: " + m.getMatchID());
                    System.out.println("\n");
                    match = m;


                }
            }
        }


        this.storedMatchData.dropMatchData(match);

    }


    //REQUIRES: input be String or List<String> that contains an eventType
    //MODIFIES: None
    //EFFECT: Retrieve Goals from the Match and Prints it out.
    //TODO: Maybe make List<String> into a hashmap
    // so I can use the key to identify which game the list of string is from

    private void processEvents(Object o) {
        List<String> events = new ArrayList<>();
        if (this.storedMatchData.storedSize() == 0) {
            System.out.println("No matches are currently imported");
        } else {
            List<MatchData> matches = matchOptionSelector();
            for (MatchData m : matches) {
                if (o instanceof String) {
                    events.addAll(retrieveEvents(m, (String) o));
                } else {
                    events.addAll(retrieveEvents(m, (List<String>) o));

                }

                System.out.println("\n");
                printEvents(events);
                System.out.println("\n");
            }
        }
    }


    private List<String> retrieveEvents(MatchData match, String s) {


        List<String> events = new ArrayList<>();


        for (LiveData l : match.getAllLiveData()) {
            if (l.filterEvent(s)) {
                String event = retrieveEvent(l);
                events.add(event);
            }
        }
        if (events.size() == 0) {
            System.out.println("No " + s + " for the Cancuks in This Game");
        }

        return events;

    }

    private List<String> retrieveEvents(MatchData match, List<String> los) {


        List<String> events = new ArrayList<>();


        for (LiveData l : match.getAllLiveData()) {
            if (l.filterEvent(los)) {
                String event = retrieveEvent(l);
                events.add(event);
            }
        }
        if (events.size() == 0) {
            System.out.println("Nothing happened for the Cancuks in This Game");
        }

        return events;

    }


    //MODIFIES: None
    //EFFECT: convert a LiveData into an String that will be printed onto the console
    private String retrieveEvent(LiveData l) {

        String coor = "x: " + l.getCoorX() + " , y: " + l.getCoorY();
        String line1 = l.getPeriod()  + " " + l.getPeriodType() + " " + l.getPeriodTime() + "\n";
        String line2 = l.getPlayer0() +  " " + l.getPlayer0Type() + " | " + l.getPlayer1() +  " " + l.getPlayer1Type()
                + "\n";
        String line3 = l.getEvent() + " " + l.getEventType() + " "  + l.getTeam() + "\n";

        String line4 = l.getDetail() + " " + l.getPlayer0() + " @ " + "(" + coor + ") ";
        return line1 + line2 + line3 + line4 + "\n";

    }


    //MODIFIES: None
    //EFFECT: Prints out a list of Strings that represents an event
    private void printEvents(List<String> ls) {
        for (String s : ls) {
            System.out.print(s + "\n");
        }
    }

    //REQUIRES: this.storedMatchData != null
    //MODIFIES: None
    //EFFECT: Retrieve either all matches in storedMatchData or select matches form storedMatchData
    private List<MatchData> matchOptionSelector() {
        boolean exit = true;
        List<MatchData> matchList = new ArrayList<>();

        System.out.println("Which Game you want to print out? ");
        System.out.println("Type the number of the option you want to select:\n");
        System.out.println("1: All Games To Date");
        System.out.println("2: Only Selected Games");

        String selection = input.next();
        while (exit) {
            if (selection.equals("1")) {
                matchList.addAll(this.storedMatchData.getStoredMatches());
                exit = false;

            } else if (selection.equals("2")) {
                matchList.addAll(retrieveSelectedMatches());
                exit = false;
            }

        }

        return matchList;

    }
    //REQUIRES: this.storedMatchData != null
    //MODIFIES: None
    //EFFECT: Retrieve select Matches base on matchID

    private List<MatchData> retrieveSelectedMatches() {
        List<MatchData> matches = new ArrayList<>();
        boolean exit = true;

        while (exit) {
            selectMatchOptions();
            Scanner option = new Scanner(System.in);
            String selection = option.next();
            if (selection.equals("Games")) {
                printStoreMatchID();

            } else {
                Integer i;
                for (String s : selection.split(",")) {

                    i = parseStringToID(s);

                    if (this.storedMatchData.checkContainMatchID(i)) {
                        matches.add(retrieveMatchByID(i));
                        exit = false;

                    } else {
                        System.out.println("Error: Game ID not found. Please Try Again \n");
                    }


                }

            }

        }
        return matches;
    }


    private void printStoreMatchID() {
        System.out.print("\n");
        for (Integer i : this.storedMatchData.getMatchIDs()) {
            System.out.print(i + "\n");
        }
        System.out.print("\n");
    }
    //REQUIRE: i must be in this.storedMatchIDs
    //MODIFIES: None
    //EFFECT: retrieve matches with the same matchID as the given integer

    private MatchData retrieveMatchByID(Integer i) {

        MatchData match = null;

        for (MatchData m : this.storedMatchData.getStoredMatches()) {
            if (m.compareMatchID(i)) {
                match = m;
                break;

            }


        }
        if (match.equals(null)) {
            System.out.println("Game not found, please check that you enter the correct ID");
        }
        return match;
    }


    private void selectMatchOptions() {

        System.out.println("Which Game you want to print out? ");
        System.out.println("Type \"Games\" for list of all ID of games imported:\n");
        System.out.println("Type the ID of the game you want to retrieve, separated by a comma:\n");
    }



    private Integer parseStringToID(String s) {
        Integer i = 0;
        try {
            i = Integer.parseInt(s);

        } catch (NumberFormatException e) {
            System.out.print("Please check the format of the IDs you entered\n");

        }

        return i;
    }

    //MODIFIES: None
    //EFFECT: Prints out a Summary of all the imported Matches
    private void processMatchSummary() {
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

    private List<Integer> countShotEvents(MatchData m) {
        List<Integer> counts = new ArrayList<>();
        for (String s : this.shotEvents) {
            Integer i = retrieveEvents(m, s).size();

            counts.add(i);
        }

        return counts;

    }

    private void menuQuestion() {
        System.out.println("Please Enter the Number of the Option:");
    }


}






