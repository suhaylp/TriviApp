package ui.triviaguiapplication.actionlisteners;

import model.Event;
import model.EventLog;
import ui.triviaguiapplication.TriviaGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Acts as an action listener for add question

public class QuitButtonActionListener implements ActionListener {

    TriviaGUI currentGUI; //tracks the current instance of Trivia GUI

    // EFFECTS: creates an action listener and sets currentGUI
    public QuitButtonActionListener(TriviaGUI currentGUI) {
        this.currentGUI = currentGUI;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        currentGUI.quitProcedure();
    }
}