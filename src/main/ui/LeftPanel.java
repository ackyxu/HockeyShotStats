package ui;

import javax.swing.*;

public class LeftPanel extends JPanel {
    JButton btn;

    public LeftPanel() {
        btn = new JButton("Change");
        btn.setActionCommand("myButton");
        btn.setAlignmentX(LEFT_ALIGNMENT);
        add(btn);


    }
}
