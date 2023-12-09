package ui.triviaguiapplication.actionlisteners;

import ui.triviaguiapplication.TriviaGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Acts as an action listener for submit answer
public class SubmitAnswerActionListener implements ActionListener {

    TriviaGUI currentGUI; //tracks the current instance of Trivia GUI

    // EFFECTS: creates an action listener and sets currentGUI
    public SubmitAnswerActionListener(TriviaGUI currentGUI) {
        this.currentGUI = currentGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentGUI.submitAnswer();
    }
}