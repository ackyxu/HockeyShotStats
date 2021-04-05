package ui;

import model.*;
import persistence.JsonWriter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//Console Interface for phase 2 of the project
public class ConsoleInterface extends Interface {

    private Scanner input;



    //EFFECTS: Stats the Console Interface
    public ConsoleInterface() {



        super();
        boolean keepgoing = true;
        while (keepgoing) {

            displayMenu();

            option = this.input.next();

            keepgoing = processMainOptions(option);

        }

    }

    // MODIFIES: this
    // EFFECTS: initializes fields
    protected void init() {

        super.init();
        this.input = new Scanner(System.in);




        loadOptions();
    }



    //MODIFIES: this
    //EFFECTS: Displays the menus option for Loading Stored Matches
    protected void loadOptions() {

        Boolean keepGoing = true;

        while (keepGoing) {

            System.out.println("Do you wish to load an saved Matches file? (y/n)");
            option = this.input.next();

            if (option.equals("y")) {

                System.out.println("Enter the file name you wish to load");
                option = this.input.next();


                try {
                    loadStoredMatches(option + ".json");
                } catch (IOException e) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                }

                keepGoing = false;

            } else if (option.equals("n")) {

                keepGoing = false;

            } else {

                System.out.println("Error: please type y or n to select your choice!");
            }
        }
    }



    //MODIFIES: None
    //EFFECT: Process the options selected from displayMenu() in Console Interface
    //NOTE: Make sure to update displayMenu() when adding new options
    protected boolean processMainOptions(String option) {
        boolean keepgoing = true;
        if (option.equals("1")) {

            processStoredMatchOptions();

        } else if (option.equals("2")) {
            processEventOptions();

        } else if (option.equals("3")) {
            processSummaryOptions();

        } else if (option.equals("4")) {

            processExitOptions();

            System.out.println("\nShutting Down");

            keepgoing = false;

        } else {
            System.out.println("Unrecognized Input");

        }

        return keepgoing;
    }

    //MODIFIES: None
    //EFFECT: Display Main Menu Options
    //NOTE: Make sure to update processMainOptions() when adding new options
    protected void displayMenu() {
        menuQuestion();
        System.out.println("1: Import/Drop Match");
        System.out.println("2: Open Events Menu");
        System.out.println("3: Matches Summary Menu");
        System.out.println("4: Exit");

    }

    //MODIFIES: None
    //Effect: Print out menu options for Importing and Dropping Matches
    protected void displayStoreMatchMenu() {
        menuQuestion();
        System.out.println("1: Import Match");
        System.out.println("2: Drop Match");
        System.out.println("3: Return to Main Menu");
        System.out.println("4: Import From Text File (Deprecated)");


    }

    //MODIFIES: None
    //EFFECT: Open and process the user's input for Importing and Dropping Matches menu options
    protected void processStoredMatchOptions() {
        displayStoreMatchMenu();
        String  option = this.input.next();

        if (option.equals("1")) {
            System.out.println("Enter the name of the game you wish to import");
            String file = JSON_IMPORT + input.next() + ".json";
            processImportMatch(file);


        } else if (option.equals("2")) {

            dropMatch();

        } else if (option.equals("3")) {

            System.out.println("\n");

        } else if (option.equals("4")) {

            processMatch();

        } else {
            System.out.println("Unrecognized Input");
        }


    }

    @Override
    protected void loadStoredMatches(String option) throws IOException {

        super.loadStoredMatches(option);
        System.out.println("Loaded from " + JSON_STORE);

    }


    //MODIFIES: none
    //EFFECT: Opens and process the submenu for events related options

    protected void processEventOptions() {

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
    protected void displayEventMenu() {
        menuQuestion();
        System.out.println("1: Return Goals Scored");
        System.out.println("2: Return Shot Events");
        System.out.println("3: Return to Main Menu");

    }

    //MODIFIES: None
    //EFFECT: Display Summary Menu Options
    //NOTE: Make sure to update processMainOptions() when adding new options
    protected void displaySummaryMenu() {
        menuQuestion();
        System.out.println("1: List All Matches Imported");
        System.out.println("2: Summary of Imported Matches");
        System.out.println("3: Return to Main Menu");

    }

    //MODIFIES: None
    //EFFECT: display and process menu options for Match Summaries
    protected void processSummaryOptions() {

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


    //MODIFIES: This
    //EFFECTS: Display and process the option the users elects in the Exit Menu
    protected void processExitOptions() {

        Boolean keepGoing = true;


        while (keepGoing) {

            displayExitMenu();
            String option = this.input.next();

            if (option.equals("y")) {

                saveStoreMatches();

                keepGoing = false;

            } else if (option.equals("n")) {

                keepGoing = false;

            } else {

                System.out.println("Error: please type y or n to select your choice!");
            }

        }

    }

    //MODIFIES: this.storedMatchData
    //EFFECT: Drops Selected Match
    protected void dropMatch() {
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


    //MODIFIES: none
    //EFFECTS: Displays the menus option for the Exit Menu
    private void displayExitMenu() {
        System.out.println("Do you wish to save the Stored Matches? (y/n)");
    }


    //MODIFIES: NONE
    //EFFECT: Returns a parsed game match that has been:
    //Checked to Make sure the game hasn't already been parsed
    //Checked to make sure it is only parsing a Cancuks game
    //In Phase 1, this will take in a filePatch, in Phase 2, it will parse a JSON file
    //There are two test files included in the package to test the parsing method and to populate the model Classes

    private MatchData parseMatch(String filePath) throws IOException {

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

    //Use to parse the event data from the text file make convert it to a list of LiveData for MatchData
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
    //REQUIRED: The String to be parse is in the predefine order below:
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


    //MODIFIES: this
    //EFFECT: construct a MatchData by parsing a file that contains MatchData, then store it in StoredMatchData
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




    //REQUIRES: input is an eventType or list of eventType, which are criteria for filtering LiveData
    //MODIFIES: None
    //EFFECT: process and prints out events, fitlered by the given input EventType/EventTypes with options to select
    //        all MatchData in StoreMatchData, or user selected MatchData
    protected void processEvents(Object o) {
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

    //MODIFIES: None
    //EFFECT: Retrieved filtered event base on the give EventType S from give MatchData, then return the events as
    //        processed Strings
    //Part of a Method Overload, to allowed a single EventType, or list of EventTypes
    protected List<String> retrieveEvents(MatchData match, String s) {


        List<LiveData> liveDatas = match.getFilteredEvent(this.team, s);
        List<String> events = new ArrayList<>();

        if (liveDatas.size() == 0) {
            System.out.println("No " + s + " for the Cancuks in This Game");
        }

        for (LiveData l : liveDatas) {
            events.add(retrieveEvent(l));
        }

        return events;

    }

    //MODIFIES: None
    //EFFECT: Retrieved filtered event base on the give EventTypes los from give MatchData, then return the events as
    //        processed Strings
    //Part of a Method Overload, to allowed a single EventType, or list of EventTypes
    protected List<String> retrieveEvents(MatchData match, List<String> los) {


        List<LiveData> liveDatas = match.getFilteredEvent(this.team, los);
        List<String> events = new ArrayList<>();

        if (liveDatas.size() == 0) {
            System.out.println("Nothing happened for the Cancuks in This Game");
        }

        for (LiveData l : liveDatas) {
            String event = retrieveEvent(l);
            events.add(event);

        }


        return events;

    }





    //MODIFIES: None
    //EFFECT: Prints out a list of Strings that represents an event
    protected void printEvents(List<String> ls) {
        for (String s : ls) {
            System.out.print(s + "\n");
        }
    }

    //REQUIRES: this.storedMatchData != null
    //MODIFIES: None
    //EFFECT: Retrieve either all matches in storedMatchData or select matches form storedMatchData
    protected List<MatchData> matchOptionSelector() {
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

    protected List<MatchData> retrieveSelectedMatches() {
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

    //MODIFIES: None
    //EFFECT: Display the menu options for retrieveSelectedMatches()
    private void selectMatchOptions() {

        System.out.println("Which Game you want to print out? ");
        System.out.println("Type \"Games\" for list of all ID of games imported:\n");
        System.out.println("Type the ID of the game you want to retrieve, separated by a comma:\n");
    }

    //REQUIRE: this.storedMatchData != null
    //MODIFIES: None
    //EFFECT: Prints out the IDs of all the matches in storedMatchData
    protected void printStoreMatchID() {
        System.out.print("\n");
        for (Integer i : this.storedMatchData.getMatchIDs()) {
            System.out.print(i + "\n");
        }
        System.out.print("\n");
    }



    //REQUIRE: s is a String that contains only numerical Digits\
    //MODIFIES: None
    //EFFECT: Parse a string into Integer that represents a MatchData.MatchID
    protected Integer parseStringToID(String s) {
        Integer i = 0;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.print("Please check the format of the IDs you entered\n");
        }

        return i;

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

    //MODIFIES: None
    //Display the menu option questions for the Main Menu and Submenus
    private void menuQuestion() {

        System.out.println("Please Enter the Number of the Option:");
    }

    protected void saveStoreMatches() {

        System.out.println("Please enter the name you wish to save the file as:");
        String file = input.next();
        JsonWriter writer = new JsonWriter(JSON_STORE + file + ".json");


        try {
            writer.open();
            writer.write(this.storedMatchData);
            writer.close();
            System.out.println("Saved to " + JSON_STORE);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }


    }



}






