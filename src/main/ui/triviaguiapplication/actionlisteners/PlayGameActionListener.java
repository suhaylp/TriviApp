package ui.triviaguiapplication.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ui.triviaguiapplication.TriviaGUI;

// Acts as an action listener for play game
public class PlayGameActionListener implements ActionListener {

    TriviaGUI currentGUI; //tracks the current instance of Trivia GUI

    // EFFECTS: creates an action listener and sets currentGUI
    public PlayGameActionListener(TriviaGUI currentGUI) {
        this.currentGUI = currentGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentGUI.startPlayMode();
    }
}