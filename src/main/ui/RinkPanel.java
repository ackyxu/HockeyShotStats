package ui;

import model.LiveData;
import model.MatchData;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

//https://stackoverflow.com/questions/23183679/resize-image-so-that-it-fits-jpanel-grid


public class RinkPanel extends JPanel {

    RinkLabel rink;


    public RinkPanel() {
        rink = new RinkLabel();
        add(rink);
    }

    //Modifies: this
    //Effect: Updates the RinkLabel the desire shot plots

    public void updateRink(List<MatchData> matches, List<String> options) {

        rink.refreshRink();
        List<LiveData> events = new ArrayList<>();

        for (MatchData match: matches) {
            events.addAll(match.getFilteredEvent("VAN", options));
        }

        rink.mapShots(events);



    }



}

