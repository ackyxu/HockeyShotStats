package ui;


import exceptions.CanucksNotInImport;
import model.LiveData;
import model.MatchData;
import persistence.JsonImport;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphicInterface extends Interface implements ActionListener, WindowListener {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    private JFrame frame;
    private JPanel topPanel;
    private JPanel optionPanel;
    private RinkPanel rink;
    private JPanel textbox;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JTextArea textArea;
    private String input;
    private JList<Integer> selections;
    private List<Integer> matchSelections;
    private JFrame listFrame;
    private String selectedEvent;


    public GraphicInterface() {


        frame = new JFrame("The Best Program EVER");
        makeFrame(this.frame);

        initOptionPanel();
        makeRink();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(this);

        frame.setMaximumSize(new Dimension(0, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, 0));


    }


    protected void init() {

        super.init();

        this.textbox = new JPanel();
        this.textbox.setLayout(new BorderLayout());

        this.selectedEvent = "";


        textArea = new JTextArea();
        textArea.setText("");
        textArea.setBorder(BorderFactory.createTitledBorder("Console"));
        textArea.setLineWrap(true);
        JScrollPane sp = new JScrollPane(textArea);
        textArea.setEditable(false);
        textbox.add(sp, BorderLayout.CENTER);
        topPanel = new JPanel();
        optionPanel = new JPanel();


        loadOptions();


    }

    /***UI Methods***/

    public void initTopPanel() {


        displayMenu();


    }


    public void setEventText(String str) {
        textArea.append("\n" + str);

    }

    //MODIFIES: this
    //EFFECT: Pops up a dialog box that lets users choose to import a previous instance or not.
    protected void loadOptions() {

        int option = JOptionPane.showConfirmDialog(null,
                "Do you want to load a previous instance?", null, JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {

            loadPreviousInstance();

        }
    }

    //MODIFIES: this
    //EFFECT: initiate JFrame
    private void makeFrame(JFrame frame) {

        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    //MODIFIES: this
    //EFFECT: creates the Rink Panel on the right side of the JFrame
    private void makeRink() {

        rink = new RinkPanel();
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;  // **** comment this line out to see effect ****
        gbc.weighty = 1;  // **** comment this line out to see effect ****

        frame.add(rink, gbc);

    }

    //MODIFIES: this
    //EFFECT: initialize the Option Panel on the LeftSide of the JFrame

    private void initOptionPanel() {

        initTopPanel();
        frame.add(optionPanel);
        refreshOptionPanel();

    }

    private void refreshOptionPanel() {
        frame.remove(optionPanel);
        optionPanel.setLayout(new GridLayout(2, 1));
        optionPanel.add(topPanel);
        optionPanel.add(textbox);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 10;
        gbc.weighty = 10;

        frame.add(optionPanel, gbc);
        frame.validate();

    }

    //Modifies: this
    //Effect: refresh and set the Menu Button Panel in the right panel
    private JPanel setOptionMenu() {
        optionPanel.remove(topPanel);
        JPanel test = new JPanel();
        test.setLayout(new GridLayout(4, 1));

        return test;
    }

    //MODIFIES: this
    //EFFECT: open a dialog box for user to choose a previous instance and set it to storedMatchData
    private void loadPreviousInstance() {

        createLoadSaveJsonWindow(JSON_STORE);


        try {
            loadStoredMatches(this.input);
            textArea.append("Loaded from " + this.input + "\n");
        } catch (IOException e) {
            textArea.append("Unable to read from file: " + this.input);
        }

    }

    private void createLoadSaveJsonWindow(String directory) {

        JFileChooser jfc = new JFileChooser(directory);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JSON Files", "json");
        jfc.setFileFilter(filter);
        int option;

        option = jfc.showDialog(null, "Select");
        if (option == JFileChooser.APPROVE_OPTION) {
            this.input = jfc.getSelectedFile().getName();
        }

    }

//    @Override
//    protected void loadStoredMatches(String option) throws IOException {
//
//        super.loadStoredMatches(option);
//        textArea.append("Loaded from " + JSON_STORE);
//
//    }


    @Override
    protected void displayMenu() {

        List<String> optionList = new ArrayList<>();
        optionList.add("Import/Drop Match");
        optionList.add("Open Events Menu");
        optionList.add("Matches Summary Menu");
        optionList.add("Exit");

        JPanel test = setOptionMenu();

        createMainOptionButtons(optionList, test);

        this.topPanel = test;
        refreshOptionPanel();


    }

    @Override
    protected void displaySummaryMenu() {

        List<String> optionList = new ArrayList<>();
        optionList.add("List All Matches Imported");
        optionList.add("Summary of Imported Matches");
        optionList.add("Return to Main Menu");

        JPanel test = setOptionMenu();

        createSummaryMenuButtons(optionList, test);

        this.topPanel = test;
        refreshOptionPanel();


    }

    @Override
    //refer to Interface for sepcs
    protected void displayEventMenu() {

        List<String> optionList = new ArrayList<>();
        optionList.add("Return Goals Scored");
        optionList.add("Return Shot Events");
        optionList.add("Return to Main Menu");

        JPanel test = setOptionMenu();

        createEventMenuButtons(optionList, test);

        this.topPanel = test;
        refreshOptionPanel();


    }

    //https://stackoverflow.com/questions/45878032/joptionpane-showconfirmdialog-with-a-jscrollpane-and-a-maximum-size
    //https://stackoverflow.com/questions/21941884/joptiopane-multi-select
    //Modifies: none
    //Effect: creates a new JFrame with a JList combo-box for all matches imported
    private void listOptionFrame(String title) {

        this.matchSelections = new ArrayList<>();

        JOptionPane optionPane = new JOptionPane(title);



        List<Integer> matchIDs = this.storedMatchData.getMatchIDs();
        this.selections = new JList<>(matchIDs.toArray(new Integer[0]));


        JScrollPane scrollPane = new JScrollPane(selections);

        int choice = optionPane.showConfirmDialog(null, scrollPane,
                "Select Match(es)", JOptionPane.OK_CANCEL_OPTION);

        if (choice == JOptionPane.OK_OPTION) {

            this.matchSelections = this.selections.getSelectedValuesList();

        }


    }


    //refer to Interface for specification
    //EFFECT: (changed) create option button for StoredMatchMenu and create the JPanel for it
    @Override
    protected void displayStoreMatchMenu() {
        List<String> optionList = new ArrayList<>();
        optionList.add("Import Match");
        optionList.add("Drop Match");
        optionList.add("Return to Main Menu");

        JPanel p = setOptionMenu();

        createStoreMatchMenuButtons(optionList, p);

        this.topPanel = p;
        refreshOptionPanel();


    }


    protected void displayMatchSelectorOption() {
        List<String> optionList = new ArrayList<>();
        optionList.add("All Games To Date");
        optionList.add("Only Selected Games");

        JPanel p = setOptionMenu();
        createMatchSelectorOption(optionList, p);

        this.topPanel = p;
        refreshOptionPanel();


    }


    protected void processExitOptions() {

        int option = JOptionPane.showConfirmDialog(null,
                "Do you want to save this instance?", null, JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {

            saveStoreMatches();

        }

    }


    protected void processEvents(Object o) {

    }

    @Override
    protected List<MatchData> retrieveSelectedMatches() {

        List<MatchData> matches = new ArrayList<>();

        for (Integer id : this.matchSelections) {

            if (this.storedMatchData.checkContainMatchID(id)) {
                matches.add(retrieveMatchByID(id));

            } else {
                textArea.append("Error: " + id + " not found. Please Try Again \n");
            }

        }

        return matches;
    }

    @Override
    protected List<String> retrieveEvents(MatchData match, String s) {
        List<LiveData> liveDatas = match.getFilteredEvent(this.team, s);
        List<String> events = new ArrayList<>();

        if (liveDatas.size() == 0) {
            textArea.append("\nNo " + s + " for the Cancuks in This Game");
        }

        for (LiveData l : liveDatas) {
            events.add(retrieveEvent(l));
        }

        return events;
    }


    @Override
    protected List<String> retrieveEvents(MatchData match, List<String> los) {

        List<LiveData> liveDatas = match.getFilteredEvent(this.team, los);
        List<String> events = new ArrayList<>();

        if (liveDatas.size() == 0) {
            textArea.append("Nothing happened for the Cancuks in This Game");
        }

        for (LiveData l : liveDatas) {
            String event = retrieveEvent(l);
            events.add(event);

        }


        return events;

    }


    @Override
    protected void printEvents(List<String> ls) {

    }


    @Override
    protected Integer parseStringToID(String s) {
        return null;
    }

    @Override
    protected void saveStoreMatches() {

        createLoadSaveJsonWindow(JSON_STORE);


        JsonWriter writer = new JsonWriter(JSON_STORE + this.input + ".json");


        try {
            writer.open();
            writer.write(this.storedMatchData);
            writer.close();
            System.out.println("Saved to " + JSON_STORE);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }


    }

    //REQUIRE: string list has less 4 or less elements, as per design principal of the program
    //MODIFIES: none
    //EFFECTS: dynamically creates options buttons
    private void createMainOptionButtons(List<String> strings, JPanel panel) {

        for (String s : strings) {
            JButton btn;
            btn = new JButton(s);
            btn.setActionCommand(s);
            btn.addActionListener(this::processMainOptions);
            panel.add(btn);
        }

    }

    //REQUIRE: string list has less 4 or less elements, as per design principal of the program
    //MODIFIES: none
    //EFFECTS: dynamically creates options buttons
    private void createStoreMatchMenuButtons(List<String> strings, JPanel panel) {

        for (String s : strings) {
            JButton btn;
            btn = new JButton(s);
            btn.setActionCommand(s);
            btn.addActionListener(this::processStoredMatchOptions);
            panel.add(btn);
        }

    }

    //REQUIRE: string list has less 4 or less elements, as per design principal of the program
    //MODIFIES: none
    //EFFECTS: dynamically creates options buttons
    private void createSummaryMenuButtons(List<String> strings, JPanel panel) {

        for (String s : strings) {
            JButton btn;
            btn = new JButton(s);
            btn.setActionCommand(s);
            btn.addActionListener(this::processSummaryOptions);
            panel.add(btn);
        }

    }

    private void createEventMenuButtons(List<String> strings, JPanel panel) {

        for (String s : strings) {
            JButton btn;
            btn = new JButton(s);
            btn.setActionCommand(s);
            btn.addActionListener(this::processEventOptions);
            panel.add(btn);
        }

    }

    private void createMatchSelectorOption(List<String> strings, JPanel panel) {

        for (String s : strings) {
            JButton btn;
            btn = new JButton(s);
            btn.setActionCommand(s);
            btn.addActionListener(this::processGameSelectionOptions);
            panel.add(btn);
        }
    }


    /***METHODS TO PROCESSS DATA***/

    //MODIFIES: this.storedMatchData
    //EFFECT: Drops Selected Match
    protected void dropMatch() {
        List<MatchData> matches = new ArrayList();
        if (this.storedMatchData.storedSize() == 0) {
            textArea.append("Error: Currently not Matches are imported");

        } else {

            listOptionFrame("Select Matches to drop");

            for (Integer id : this.matchSelections) {

                for (MatchData m : this.storedMatchData.getStoredMatches()) {


                    if (m.getMatchID().equals(id)) {
                        textArea.append("\nMatch dropped " + m.getMatchDate() + " ID: " + m.getMatchID());
                        textArea.append("\n");
                        matches.add(m);


                    }
                }
            }

            for (MatchData match : matches) {

                this.storedMatchData.dropMatchData(match);

            }
        }
    }

    //REQUIRE: storedMatchData.size() != 0
    //MODIFIES: None
    //EFFECT: Prints out a Summary of all the imported Matches in textbox
    @Override
    protected void processMatchSummary() {
        for (MatchData m : this.storedMatchData.getStoredMatches()) {
            List<Integer> counts = countShotEvents(m);
            Integer block = counts.get(0);
            Integer shots = counts.get(1);
            Integer missed = counts.get(2);
            Integer goal = counts.get(3);

            textArea.append("\nHome Team: " + m.getGameData().getHome().getTeamName() + " "
                    + m.getGameData().getHome().getTeamAbr());
            textArea.append("\nAway Team: " + m.getGameData().getAway().getTeamName() + " "
                    + m.getGameData().getAway().getTeamAbr());
            textArea.append(" ");
            textArea.append("\nDate: " + m.getMatchDate() + " ID: " + m.getMatchID());
            textArea.append("\nCancuks:");
            textArea.append("\nBlocked Shots:" + block + "  Shots On Net:" + shots + "  Missed Shots:" + missed);
            textArea.append("\nGoals: " + goal + "\n");

        }
    }

    @Override
    //MODIFIES: this
    //EFFECTS: Process importing a NHL matches retrieved from API (currently, only local cache files in JSON)
    protected void processImportMatch(String file) {

        matchImport = new JsonImport(JSON_IMPORT + file);

        MatchData match;


        try {
            match = matchImport.read();

            if (!this.storedMatchData.getMatchIDs().contains(match.getMatchID())) {

                this.storedMatchData.addMatchData(match);
                textArea.append("\nGreat Success!\n");
            } else {
                textArea.append("\nMatch ID already in the program: Match not imported\n");
            }

        } catch (IOException e) {
            textArea.append("Error: File not found \n please check the name of the file you fish to import");
        } catch (CanucksNotInImport canucksNotInImport) {
            textArea.append("Oops! Looks like the Canucks did not play in that game!");
        }


    }


    //Modifies: none
    //Effect: process the button actions from displayMainOptions Panel
    protected void processMainOptions(ActionEvent e) {


        if (e.getActionCommand().equals("Import/Drop Match")) {
            displayStoreMatchMenu();

        } else if (e.getActionCommand().equals("Open Events Menu")) {
            displayEventMenu();
        } else if (e.getActionCommand().equals("Matches Summary Menu")) {
            displaySummaryMenu();

        } else if (e.getActionCommand().equals("Exit")) {
            processExitOptions();
            System.exit(0);
        }


    }

    //Modifies: none
    //Effect: process the button actions from displayMainOptions Panel

    protected void processStoredMatchOptions(ActionEvent e) {

        if (e.getActionCommand().equals("Import Match")) {
            createLoadSaveJsonWindow(JSON_IMPORT);
            processImportMatch(this.input);
            displayMenu();

        } else if (e.getActionCommand().equals("Drop Match")) {
            dropMatch();
            displayStoreMatchMenu();

        } else if (e.getActionCommand().equals("Return to Main Menu")) {
            displayMenu();
        }


    }

    protected void processSummaryOptions(ActionEvent e) {

        if (e.getActionCommand().equals("List All Matches Imported")) {
            printStoreMatchID();
            displayMenu();
        } else if (e.getActionCommand().equals("Summary of Imported Matches")) {
            processMatchSummary();
            displayMenu();
        } else if (e.getActionCommand().equals("Return to Main Menu")) {
            displayMenu();
        }

    }

    //Modifies: none
    //Effect: Retrieve either all games or just selected games, according to option chosen for Event selection
    protected void processGameSelectionOptions(ActionEvent e) {

        List<MatchData> matches;
        List<String> eventType = new ArrayList<>();

        if (this.selectedEvent.equals("Goals")) {

            if (e.getActionCommand().equals("All Games To Date")) {
                this.matchSelections = this.storedMatchData.getMatchIDs();


            } else {
                if (e.getActionCommand().equals("Only Selected Games")) {
                    listOptionFrame("Select Matches Match(es)");

                }
            }

            matches = retrieveSelectedMatches();
            eventType.add("GOAL");
            rink.updateRink(matches, eventType);

        //Else options displays all shots, regardless of what the selectedEvent indicates
        } else {
            if (e.getActionCommand().equals("All Games To Date")) {
                this.matchSelections = this.storedMatchData.getMatchIDs();

            } else if (e.getActionCommand().equals("Only Selected Games")) {
                listOptionFrame("Select Matches Match(es)");

            }
            matches = retrieveSelectedMatches();
            rink.updateRink(matches, shotEvents);

        }
        displayMenu();
    }


    protected void processEventOptions(ActionEvent e) {


        if (e.getActionCommand().equals("Return Goals Scored")) {
            this.selectedEvent = "Goals";
            displayMatchSelectorOption();
        } else if (e.getActionCommand().equals("Return Shot Events")) {
            this.selectedEvent = "Shots";
            displayMatchSelectorOption();
        } else if (e.getActionCommand().equals("Return to Main Menu")) {
            displayMenu();
        }


    }


    @Override
    protected void printStoreMatchID() {
        textArea.append("\nMatches Stored In This Instance\n");


        try {
            for (Integer i : this.storedMatchData.getMatchIDs()) {
                textArea.append(i + "\n");
            }
            textArea.append("\n");
        } catch (Exception e) {
            textArea.append("No Matches Currently Imported \n");
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e) {

        processExitOptions();
        System.exit(0);

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
//button.addAction(this::onButtonPress);
//onButtonPress(Action e);