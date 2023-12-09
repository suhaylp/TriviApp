package ui.triviaguiapplication.actionlisteners;

import ui.triviaguiapplication.TriviaGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Acts as an action listener for edit topics
public class EditTopicsActionListener implements ActionListener {

    TriviaGUI currentGUI; //tracks the current instance of Trivia GUI

    public EditTopicsActionListener(TriviaGUI currentGUI) {
        this.currentGUI = currentGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentGUI.editMode();
    }
}