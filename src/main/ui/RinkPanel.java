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

    public void updateRink(List<MatchData> matches, List<String> options) {

        rink.refreshRink();
        List<LiveData> events = new ArrayList<>();

        for (MatchData match: matches) {
            events.addAll(match.getFilteredEvent("VAN", options));
        }

        rink.mapShots(events);



    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawImage(image, 0, 0,getWidth(),getHeight(), this);
//        // see javadoc for more info on the parameters
//    }

}

