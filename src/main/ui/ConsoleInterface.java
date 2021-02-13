package ui;

import model.GameData;
import model.LiveData;
import model.MatchData;
import model.Team;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//TODO refact out the code in ConsoleInterface to Seperate Methods
//     make sure all methods are used in LiveData

public class ConsoleInterface {



    private Scanner input;
    List<MatchData> storedMatches;
    List<String> shotEvents;
    List<Integer> storedMatchIDs;




    public ConsoleInterface() throws IOException {

        String option;
        boolean exitcode = false;
        init();
        while (!exitcode) {

            displayMenu();

            option = this.input.next();

            exitcode = processMainOptions(option);

//            if (option.equals("1")) {
//
//                processMatch();
//
//            } else if (option.equals("2")) {
//
//                processEvents("GOAL");
//
//            } else if (option.equals("3")) {
//
//                processEvents(shotEvents);
//
//
//
//            } else if (option.equals("4")) {
//
//                System.out.println("\nShutting Down");
//
//                exitcode = true;
//
//            } else {
//                System.out.println("Unrecognized Input");
//                option = null;
//            }
        }

    }

    // MODIFIES: this
    // EFFECTS: initializes fields
    private void init() {
        this.input = new Scanner(System.in);
        this.storedMatches = new ArrayList<>();
        this.storedMatchIDs = new ArrayList<>();

        this.shotEvents = new ArrayList<>();
        this.shotEvents.add("BLOCKED_SHOT");
        this.shotEvents.add("SHOT");
        this.shotEvents.add("MISSED_SHOT");
        this.shotEvents.add("GOAL");
    }

    private boolean processMainOptions(String option) throws IOException {
        boolean exitcode = false;
        if (option.equals("1")) {

            processMatch();

        } else if (option.equals("2")) {

            processEvents("GOAL");

        } else if (option.equals("3")) {

            processEvents(shotEvents);



        } else if (option.equals("4")) {

            System.out.println("\nShutting Down");

            exitcode = true;

        } else {
            System.out.println("Unrecognized Input");

        }

        return exitcode;

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

        for (String s: lstr) {
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


        return new LiveData(s.get(0), s.get(1),s.get(2),s.get(3),s.get(4),s.get(5),
                                        s.get(6),s.get(7),Integer.parseInt((s.get(9)).substring(0,1)),
                                        s.get(8), LocalTime.parse(s.get(10)),
                                        Integer.parseInt(s.get(11)),Integer.parseInt(s.get(12)));
    }

    private void retrieveGoals(List<MatchData> listMatch) {
        List<String> goals = new ArrayList<>();
        for (MatchData m: listMatch) {
            goals.addAll(retrieveGoals(m));
        }
    }


    //MODIFIES: None
    //Effect: Return a list of Goals Scored
    //TODO: remove the input, change to inlcude a selector for all game or just one
    private List<String> retrieveGoals(MatchData match) {



        List<String> goals = new ArrayList<>();


        for (LiveData l: match.getAllLiveData()) {
            if (l.getTeam().equals("VAN") && l.getEventType().equals("GOAL")) {
                String goal = retrieveEvent(l);
                goals.add(goal);
            }
        }
        if (goals.size() == 0) {
            System.out.println("No Goals Were Scored By the Cancuks in This Game");
        }

        return goals;

    }



    //EFFECT: Display Menu Options

    private void displayMenu() {
        System.out.println("Please Enter the Number of the Option:");
        System.out.println("1: Import Match");
        System.out.println("2: Return Goals Scored");
        System.out.println("3: Return Shots");
        System.out.println("4: Exit");

    }

    //MODIFIES: this
    //EFFECT: parse Match via parsing match into MatchData and updates storedMatchesIDs
    private void processMatch() throws IOException {

        System.out.println("Enter the file location");
        Scanner matchjson = new Scanner(System.in);
        String file = matchjson.next();

        MatchData matchData = parseMatch("test.txt/");
        System.out.println("Match Imported!");
        assert matchData != null;
        System.out.println("Game Played on " + matchData.getMatchDate() + " ID: " + matchData.getMatchID());
        storedMatches.add(matchData);
        retrievedStoredID();

    }

    private void processShotAttempts() {
        List<String> events = new ArrayList<>();
        if (storedMatches.size() == 0) {
            System.out.println("No matches are currently imported");
        } else {
            for (MatchData m: storedMatches) {
                events.addAll(retrieveShotAttempts(m));
            }
            System.out.println("\n");
            printEvents(events);
            System.out.println("\n");
        }

    }

    //REQUIRES: input be String or List<String> that contains an eventType
    //MODIFIES: None
    //EFFECT: Retrieve Goals from the Match and Prints it out.
    //TODO: Maybe make List<String> into a hashmap
    // so I can use the key to identify which game the list of string is from

    private void processEvents(Object o) {
        List<String> events = new ArrayList<>();
        if (storedMatches.size() == 0) {
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


        List<String> goals = new ArrayList<>();


        for (LiveData l: match.getAllLiveData()) {
            if (l.filterEvent(s)) {
                String goal = retrieveEvent(l);
                goals.add(goal);
            }
        }
        if (goals.size() == 0) {
            System.out.println("No Goals Were Scored By the Cancuks in This Game");
        }

        return goals;

    }

    private List<String> retrieveEvents(MatchData match, List<String> los) {


        List<String> goals = new ArrayList<>();


        for (LiveData l: match.getAllLiveData()) {
            if (l.filterEvent(los)) {
                String goal = retrieveEvent(l);
                goals.add(goal);
            }
        }
        if (goals.size() == 0) {
            System.out.println("No Goals Were Scored By the Cancuks in This Game");
        }

        return goals;

    }




    //MODIFIES: None
    //EFFECT: Return

    private  List<String> retrieveShotAttempts(MatchData match) {

        List<String> eventList = new ArrayList<>();
        for (LiveData l : match.getAllLiveData()) {
            if (l.getTeam().equals("VAN") && shotEvents.contains(l.getEventType())) {
                String event = retrieveEvent(l);
                eventList.add(event);

            }
        }

        return eventList;
    }

    //MODIFIES: None
    //EFFECT: convert a LiveData into an String that will be printed onto the console
    private String retrieveEvent(LiveData l) {

        String coor = "x: " + l.getCoorX() + " , y: " + l.getCoorY();
        String event = l.getDetail() + " " + l.getPlayer0() + " @ " + "(" + coor + ")";

        return event;

    }
    //REQUIRES: ?
    //MODIFIES: None
    //EFFECT: Retrieve Goals from the Match and Prints it out.
    //TODO: Maybe make List<String> into a hashmap
    // so I can use the key to identify which game the list of string is from

    private void processGoals() {
        List<String> events = new ArrayList<>();
        if (storedMatches.size() == 0) {
            System.out.println("No matches are currently imported");
        } else {
            List<MatchData> matches  = matchOptionSelector();
            for (MatchData m: matches) {
                events.addAll(retrieveGoals(m));
            }
            System.out.println("\n");
            printEvents(events);
            System.out.println("\n");
        }

    }

    //MODIFIES: None
    //EFFECT: Prints out a list of Strings that represents an event
    private void printEvents(List<String> ls) {
        for (String s: ls) {
            System.out.print(s + "\n");
        }
    }

    //REQUIRES: this.storedMatches != null
    //MODIFIES: None
    //EFFECT: Retrieve either all matches in storedMatches or select matches form storedMatches
    private List<MatchData> matchOptionSelector() {
        boolean exit = true;
        List<MatchData> matchList = new ArrayList<>();

        System.out.println("Which Game you want to print out? ");
        System.out.println("Type the number of the option you want to select:\n");
        System.out.println("1: All Games To Date");
        System.out.println("2: Only Selected Games");

        Scanner option = new Scanner(System.in);
        String selection = option.next();
        while (exit) {
            if (selection.equals("1")) {
                matchList.addAll(this.storedMatches);
                exit = false;

            } else if (selection.equals("2")) {
                //else if instead of else has I want to add more options later on.
                matchList.addAll(retrieveSelectedMatches());
                exit = false;
            }

        }

        return matchList;

    }
    //REQUIRES: this.storedMatches != null
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

                    if (checkCorrectIDInput(i)) {
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
    //MODIFIES: this
    //EFFECT: retrieve all the ID of matches in storeMatches

    private void retrievedStoredID() {

        for (MatchData m: storedMatches) {
            this.storedMatchIDs.add(m.getMatchID());
        }

    }

    private void printStoreMatchID() {
        for (Integer i : storedMatchIDs) {
            System.out.print(i + "\n");
        }
    }
    //REQUIRE: i must be in this.storedMatchIDs
    //MODIFIES: None
    //EFFECT: retrieve matches with the same matchID as the given integer

    private MatchData retrieveMatchByID(Integer i) {

        MatchData match = null;

        for (MatchData m : this.storedMatches) {
            if (m.compareMatchID(i)) {
                match = m;

            } else {
                System.out.println("Game not found, please check that you enter the correct ID");
            }
        }

        return match;
    }


    private void selectMatchOptions() {

        System.out.println("Which Game you want to print out? ");
        System.out.println("Type \"Games\" for list of all ID of games imported:\n");
        System.out.println("Type the ID of the game you want to retrieve, separated by a comma:\n");
    }

    private boolean checkCorrectIDInput(Integer i) {
        return storedMatchIDs.contains(i);
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



}






