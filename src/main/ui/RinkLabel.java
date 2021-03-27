package ui;

import model.LiveData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

    //since I am using only side of the rink, specifically the the left hand side of the rink, per the image
    //nhl_rink_coordinates in the ui/images folder, retrieved from:
    //https://community.rapidminer.com/discussion/44904/using-the-nhl-api-to-analyze-pro-ice-hockey-data-part-1
    //Grapics drawOval sets the origin point at the topmost left corner, where as LiveData has the origin point at the
    //center of the rink.
    //First, I need to rotate the coordiantes, so that x = y and y = x (as the program display a vertical rink)
    //The coordiantes also needs to be mirrored: if (after rotation) y > 0 after rotation, make y negative and mirror
    //the x coordinates.
    //Then shift (the after rotation) x coordinates by 42.5, so that the left most point is at 0.


public class RinkLabel extends JLabel {

    private BufferedImage image;
    private ImageIcon rink;
    private static double RINK_X_INCREMENT;
    private static double RINK_Y_INCREMENT;
    private static final Color blue = new Color(0,0,255, 100);
    private static final Color green = new Color(0,254,0, 100);

    public RinkLabel() {
        try {
            image = ImageIO.read(new File("./src/main/ui/images/nhlrink2.png"));

        } catch (IOException e) {

            System.out.println("Okay");

        }

//        this.setSize(image.getWidth(), image.getHeight());

        //Set the increment for x and y coordinates.  See reasoning in the top comment of the class
        RINK_X_INCREMENT = image.getHeight() / (2 * 42.5);
        RINK_Y_INCREMENT = image.getWidth() / 100;

        rink = new ImageIcon(image);

        setIcon(rink);


    }

    //modifies: none
    //effect: mirrors the y coordinate of the event so that it is on one side of the rink, and shifted 42.5, so that the
    // range of coordinates is [0.85] instead of [-42.5, 42.5] per the NHL Stat API

    public Double convertYCoordinate(LiveData event) {

        Integer coorY = event.getCoorY();

        if (event.getCoorX() < 0) {

            coorY = -coorY;
        }

        return coorY + 42.5;


    }



    //modifies:  this
    //effect: clears the Rink label to a blank state

    public void refreshRink() {

        try {
            image = ImageIO.read(new File("./src/main/ui/images/nhlrink2.png"));

        } catch (IOException e) {

            System.out.println("Okay");

        }

        rink = new ImageIcon(image);

        setIcon(rink);
    }

    //https://stackoverflow.com/questions/40439572/drawing-on-a-buffered-image
    //Modifies: this
    //Effect: overlays shots in the given list on to the rink image
    public void mapShots(List<LiveData> events) {

        Graphics g = image.getGraphics();

        for (LiveData event: events) {

            int y = (int) Math.round((100 - Math.abs(event.getCoorX())) * RINK_Y_INCREMENT);
            int x = (int) Math.round(convertYCoordinate(event) * RINK_X_INCREMENT);

            if (event.getEventType().equals("GOAL")) {
                g.setColor(green);
            } else {
                g.setColor(blue);
            }
            g.fillOval(x, y, 7,7);
        }


    }
}
