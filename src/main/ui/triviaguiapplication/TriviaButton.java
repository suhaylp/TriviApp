package ui.triviaguiapplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Represents a special type of JButton with specific formatting
public class TriviaButton extends JButton {

    //EFFECTS: Creates a Trivia Button with text, action listener l, and specific formatting
    public TriviaButton(String text, ActionListener l) {
        super(text);
        this.setFont(new Font("Monospaced", Font.BOLD, 30));
        this.setForeground(Color.darkGray);
        this.addActionListener(l);
    }

}
