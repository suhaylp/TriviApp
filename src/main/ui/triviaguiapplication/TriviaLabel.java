package ui.triviaguiapplication;

import javax.swing.*;
import java.awt.*;

// Creates a specific format of JLabel with inputted text and font size
public class TriviaLabel extends JLabel {

    //EFFECTS: Creates a JLabel with text, specified font size, and specific formatting
    public TriviaLabel(String text, int fontSize) {
        super(text);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        this.setText(text);
        this.setForeground(Color.white);
    }

}