package ui.consoleversion;

// Runs the class TriviaApp

import java.io.FileNotFoundException;

public class MainConsoleUI {

    // EFFECTS: begins TriviaApp()
    public static void main(String[] args) {
        try {
            new TriviaApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
