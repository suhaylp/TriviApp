package ui.triviaguiapplication;

import model.AllTopics;
import model.EventLog;
import model.Event;
import model.QuestionAnswerPair;
import model.Topic;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.triviaguiapplication.actionlisteners.*;
import ui.triviaguiapplication.assets.TriviaButton;
import ui.triviaguiapplication.assets.TriviaLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Trivia App GUI
public class TriviaGUI extends JFrame {

    private static final String JSON_STORE = "./data/mainfile.json"; //main JSON file to load from and save to

    private JFrame frame; //GUI's frame

    // GUI Panels
    private JPanel homeScreen;
    private JPanel playScreen;
    private JPanel editScreen;
    private JPanel topicEditScreen;
    private JPanel editScreen2;
    private JPanel questionRemoveScreen;

    //GUI Labels
    private TriviaLabel welcomeText;
    private TriviaLabel welcomeText2;
    private TriviaLabel adderMessageLabel1;
    private TriviaLabel adderMessageLabel2;
    private TriviaLabel initialEditText;
    private TriviaLabel initialRemoveText;
    private TriviaLabel initialPlayText;
    private JLabel qaLabel;

    //GUI Buttons
    private TriviaButton playModeButton;
    private TriviaButton editModeButton;
    private TriviaButton saveButton;
    private TriviaButton loadButton;
    private TriviaButton createTopicButton;
    private TriviaButton editTopicButton;
    private TriviaButton addQuestionsButton;
    private TriviaButton removeQuestionsButton;
    private TriviaButton addQAPairButton;
    private TriviaButton mainMenuButton;
    private TriviaButton addTopicButton;
    private TriviaButton submitAnswerButton;
    private TriviaButton selectButton;
    private TriviaButton startButton;
    private TriviaButton deleteQuestionButton;
    private TriviaButton quitButton;

    //GUI Text Fields
    private JTextField enteredTopic;
    private JTextField questionToAdd;
    private JTextField answerToAdd;
    private JTextField enteredAnswer;

    //GUI Other Objects
    private JComboBox<Object> allQuestionsDropdown;
    private JComboBox<Object> allTopicsDropdown;
    private List<QuestionAnswerPair> currentQAPlist;
    private List<String> listForDropdownString;
    private String currentAnswer;
    private String currentQuestion;
    private AllTopics topicStorage; // represents all topics in current GUI instance
    private Topic editChoice;
    private Topic playChoice;
    private int score;
    private int count;


    // EFFECTS: begins the Trivia App by running TriviaGUI method
    public static void main(String[] args) {
        new TriviaGUI();
        //EventLog.getInstance().logAndExit();
    }

    // EFFECTS: begins the Trivia App by running initial methods and creating a new AllTopics object
    private TriviaGUI() {
        super("Trivia App");
        mainMenuSetup();
        topicStorage = new AllTopics();
    }

    // EFFECTS: calls methods to set up labels, buttons, main menu, and the initial frame
    private void mainMenuSetup() {
        labelCreator();
        buttonCreators();
        homeScreenPanelSetup();
        initialFrameSetup();
    }

    // MODIFIES: this
    // EFFECTS: sets up the initial frame formatting
    private void initialFrameSetup() {
        frame = new JFrame();
        frame.add(homeScreen, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Trivia Application");
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // Code influenced by: https://docs.oracle.com/javase/8/docs/api/java/awt/event/WindowListener.html#windowClosed-java.awt.event.WindowEvent-
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitProcedure();
            }
        });

    }

    // MODIFIES: this
    // EFFECTS: sets up the main menu screen
    private void homeScreenPanelSetup() {
        homeScreen = new JPanel();
        homeScreen.setLayout(new GridLayout(7, 1));
        homeScreen.add(welcomeText);
        homeScreen.add(welcomeText2);
        homeScreen.add(playModeButton);
        homeScreen.add(editModeButton);
        homeScreen.add(loadButton);
        homeScreen.add(saveButton);
        homeScreen.add(quitButton);
        ImageIcon icon = new ImageIcon("data/TEST1.png");
        JLabel label = new JLabel(icon);
        //homeScreen.add(label);
        homeScreen.setBackground(Color.darkGray);
        homeScreen.setBorder(BorderFactory.createEmptyBorder(50, 50, 20, 50));
    }

    // MODIFIES: this
    // EFFECTS: initializes all Trivia Buttons, with specified action listeners
    private void buttonCreators() {
        addQAPairButton = new TriviaButton("Click to add Q/A pair", new AddQAPairActionListener(this));
        mainMenuButton = new TriviaButton("Main Menu", new MainMenuActionListener(this));
        addTopicButton = new TriviaButton("Add Topic", new AddTopicActionListener(this));
        deleteQuestionButton = new TriviaButton("Delete Question", new DeleteQuestionActionListener(this));
        submitAnswerButton = new TriviaButton("Submit Answer", new SubmitAnswerActionListener(this));
        createTopicButton = new TriviaButton("Create Topic", new CreateTopicActionListener(this));
        editTopicButton = new TriviaButton("Edit Topic", new EditTopicActionListener(this));
        playModeButton = new TriviaButton("Play Game", new PlayGameActionListener(this));
        editModeButton = new TriviaButton("Edit Topics", new EditTopicsActionListener(this));
        loadButton = new TriviaButton("Load Topics", new LoadTopicsActionListener(this));
        saveButton = new TriviaButton("Save Topics", new SaveTopicsActionListener(this));
        addQuestionsButton = new TriviaButton("Add Questions", new AddQuestionActionListener(this));
        removeQuestionsButton = new TriviaButton("Remove Questions", new RemoveQuestionsActionListener(this));
        selectButton = new TriviaButton("Select Topic", new SelectTopicActionListener(this));
        startButton = new TriviaButton("Start Trivia", new StartTriviaActionListener(this));
        quitButton = new TriviaButton("Quit Application", new QuitButtonActionListener(this));
    }

    // MODIFIES: this
    // EFFECTS: initializes all Trivia Labels with specified text and font size
    private void labelCreator() {
        welcomeText = new TriviaLabel("Welcome to TriviApp", 68);
        welcomeText2 = new TriviaLabel("Select an option to get started!", 40);
        welcomeText2.setForeground(Color.lightGray);
        adderMessageLabel1 = new TriviaLabel("Question: ", 40);
        adderMessageLabel2 = new TriviaLabel("Answer: ", 40);
        initialRemoveText = new TriviaLabel("Select a question to remove: ", 40);
        initialEditText = new TriviaLabel("Select a topic to edit: ", 40);
        initialPlayText = new TriviaLabel("Select a topic by clicking the dropdown menu below: ", 40);
    }


    // MODIFIES: this
    // EFFECTS: sends the user to add/remove question selection based on their topic selection
    public void sendToAddRemoveSelection() {
        String tempChoice = (String) allTopicsDropdown.getSelectedItem();
        editChoice = topicStorage.masterTopicFinder(tempChoice);
        addRemoveSelection();
    }

    // MODIFIES: this
    // EFFECTS: submits the users entered answer, providing feedback and incrementing the current question
    public void submitAnswer() {
        String userEnteredString = enteredAnswer.getText();
        if (userEnteredString.equalsIgnoreCase(currentAnswer)) {
            score++;
            JOptionPane.showMessageDialog(null, "Nice work!");
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect! The correct answer was: " + currentAnswer);
        }

        enteredAnswer.setText("");
        count++;
        questionAnswerGenerator();
    }

    // MODIFIES: this
    // EFFECTS: sets the entered question and answer strings and sends program to QA Pair Adder
    public void addQAPair() {
        currentQuestion = questionToAdd.getText();
        currentAnswer = answerToAdd.getText();
        qaPairAdder();
    }

    // MODIFIES: this
    // EFFECTS: creates a new topic with entered name if it doesn't already exist, otherwise presents an error
    public void addTopic() {
        if (!topicStorage.getAllTopicNames().contains(enteredTopic.getText())) {
            topicAdder();
        } else {
            JOptionPane.showMessageDialog(null, "Unable to add: a topic with this name already exists!");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the play topic screen and begins the question answer generator
    public void startPlayingSelectedTopic() {
        initializePlayTopicScreen();
        score = 0;
        count = 1;
        String tempChoice = (String) allTopicsDropdown.getSelectedItem();
        playChoice = topicStorage.masterTopicFinder(tempChoice);
        currentQAPlist = playChoice.getQuestions();
        questionAnswerGenerator();
    }

    // EFFECTS: allows user to enter topic editor screen if at least one topic already exists
    public void editTopic() {
        if (!topicStorage.getAllTopics().isEmpty()) {
            topicEditor();
        } else {
            JOptionPane.showMessageDialog(null, "You must load or create or load at least one topic to edit!");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the question selected by user in the dropdown menu
    public void deleteQuestion() {
        int indexForQ = listForDropdownString.indexOf(allQuestionsDropdown.getSelectedItem());
        QuestionAnswerPair qapToDelete = currentQAPlist.get(indexForQ);
        editChoice.deleteQuestion(qapToDelete);
        frame.remove(questionRemoveScreen);
        questionRemover();
    }

    // EFFECTS: removes all panels from the frame and adds the main menu panel back
    public void sendBackToMainMenu() {
        frame.getContentPane().removeAll();
        frame.add(homeScreen);
        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: allows the user to enter play mode if there is at least one topic to play
    public void startPlayMode() {
        if (!topicStorage.getAllTopics().isEmpty()) {
            playMode();
        } else {
            JOptionPane.showMessageDialog(null, "You must load or create at least one topic to play!");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the play mode screen
    private void playMode() {
        playScreen = new JPanel();

        List<String> listForDropdown = topicStorage.getAllTopicNames();
        allTopicsDropdown = new JComboBox<>(listForDropdown.toArray());

        playScreen.setLayout(new GridLayout(4, 1));
        playScreen.add(initialPlayText);
        playScreen.add(allTopicsDropdown);
        playScreen.add(startButton);
        playScreen.add(mainMenuButton);
        playScreen.setBackground(Color.darkGray);
        playScreen.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        frame.remove(homeScreen);
        frame.add(playScreen);
        frame.revalidate();
        frame.repaint();
    }

    private void questionAnswerGenerator() {
        int totalNumQuestions = currentQAPlist.size();
        if (count <= totalNumQuestions) {
            selectedQuestionPlayer(currentQAPlist.get(count - 1));
        } else {
            count = count - 1;
            JOptionPane.showMessageDialog(null, "Congrats! Your score was: " + score + "/" + count);

            frame.getContentPane().removeAll();
            frame.add(homeScreen);
            frame.revalidate();
            frame.repaint();
        }



    }

    // MODIFIES: this
    // EFFECTS: adds the user's inputted string as a topic with no Q/A pairs
    private void topicAdder() {
        Topic userCreatedTopic = new Topic(enteredTopic.getText());
        topicStorage.addToAllTopics(userCreatedTopic);
        enteredTopic.setText("");
        JOptionPane.showMessageDialog(null, "Topic successfully added");
    }

    // MODIFIES: this
    // EFFECTS: adds user's inputted Q/A pair in their selected topic
    private void qaPairAdder() {
        QuestionAnswerPair qaPair = new QuestionAnswerPair(currentQuestion, currentAnswer);
        editChoice.addQuestion(qaPair);
        questionToAdd.setText("");
        answerToAdd.setText("");
        JOptionPane.showMessageDialog(null, "Question successfully added");
    }

    // MODIFIES: this
    // EFFECTS: creates the question adder panel
    public void questionAdder() {
        JPanel questionAdderScreen = new JPanel();



        questionToAdd = new JTextField();
        questionToAdd.setHorizontalAlignment(JLabel.CENTER);
        questionToAdd.setFont(new Font("Monospaced", Font.BOLD, 40));

        answerToAdd = new JTextField();
        answerToAdd.setHorizontalAlignment(JLabel.CENTER);
        answerToAdd.setFont(new Font("Monospaced", Font.BOLD, 40));

        questionAdderScreen.setLayout(new GridLayout(6, 1));
        questionAdderScreen.add(adderMessageLabel1);
        questionAdderScreen.add(questionToAdd);
        questionAdderScreen.add(adderMessageLabel2);
        questionAdderScreen.add(answerToAdd);
        questionAdderScreen.add(addQAPairButton);
        questionAdderScreen.add(mainMenuButton);
        questionAdderScreen.setBackground(Color.darkGray);
        questionAdderScreen.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        frame.remove(editScreen2);
        frame.add(questionAdderScreen);
        frame.revalidate();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates the question remover panel
    public void questionRemover() {
        questionRemoveScreen = new JPanel();
        currentQAPlist = editChoice.getQuestions();
        listForDropdownString = new ArrayList<>();
        for (QuestionAnswerPair q : currentQAPlist) {
            listForDropdownString.add(q.getQuestion());
        }
        allQuestionsDropdown = new JComboBox<>(listForDropdownString.toArray());

        questionRemoveScreen.setLayout(new GridLayout(4, 1));
        questionRemoveScreen.add(initialRemoveText);
        questionRemoveScreen.add(allQuestionsDropdown);

        questionRemoveScreen.add(deleteQuestionButton);
        questionRemoveScreen.add(mainMenuButton);
        questionRemoveScreen.setBackground(Color.darkGray);
        questionRemoveScreen.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        frame.remove(editScreen2);
        frame.add(questionRemoveScreen);
        frame.revalidate();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates the edit screen panel
    public void editMode() {
        editScreen = new JPanel();
        editScreen.setLayout(new GridLayout(3, 1));
        editScreen.setBackground(Color.darkGray);
        editScreen.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        editScreen.add(createTopicButton);
        editScreen.add(editTopicButton);
        editScreen.add(mainMenuButton);

        frame.remove(homeScreen);
        frame.add(editScreen);
        frame.revalidate();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates the topic creator panel
    public void topicCreator() {
        JPanel topicCreationScreen = new JPanel();

        JLabel initialCreationText = new JLabel();
        initialCreationText.setHorizontalAlignment(JLabel.CENTER);
        initialCreationText.setFont(new Font("Monospaced", Font.BOLD, 40));
        initialCreationText.setText("Enter a new topic name: ");
        initialCreationText.setForeground(Color.white);

        enteredTopic = new JTextField();
        enteredTopic.setHorizontalAlignment(JLabel.CENTER);
        enteredTopic.setFont(new Font("Monospaced", Font.BOLD, 40));

        topicCreationScreen.setLayout(new GridLayout(4, 1));
        topicCreationScreen.add(initialCreationText);
        topicCreationScreen.add(enteredTopic);
        topicCreationScreen.add(addTopicButton);
        topicCreationScreen.add(mainMenuButton);
        topicCreationScreen.setBackground(Color.darkGray);
        topicCreationScreen.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        frame.remove(editScreen);
        frame.add(topicCreationScreen);
        frame.revalidate();
        frame.repaint();

    }

    // MODIFIES: this
    // EFFECTS: creates the topic editor panel
    private void topicEditor() {
        topicEditScreen = new JPanel();

        List<String> listForDropdown = topicStorage.getAllTopicNames();
        allTopicsDropdown = new JComboBox<>(listForDropdown.toArray());

        topicEditScreen.setLayout(new GridLayout(4, 1));
        topicEditScreen.add(initialEditText);
        topicEditScreen.add(allTopicsDropdown);
        topicEditScreen.add(selectButton);
        topicEditScreen.add(mainMenuButton);
        topicEditScreen.setBackground(Color.darkGray);
        topicEditScreen.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        frame.remove(editScreen);
        frame.add(topicEditScreen);
        frame.revalidate();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates the add/remove selection panel
    private void addRemoveSelection() {
        editScreen2 = new JPanel();

        editScreen2.setLayout(new GridLayout(3, 1));
        editScreen2.setBackground(Color.darkGray);
        editScreen2.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        editScreen2.add(addQuestionsButton);
        editScreen2.add(removeQuestionsButton);
        editScreen2.add(mainMenuButton);

        frame.remove(topicEditScreen);
        frame.add(editScreen2);
        frame.revalidate();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: initializes the play topic screen
    private void initializePlayTopicScreen() {
        JPanel topicPlayerScreen = new JPanel();

        qaLabel = new JLabel();
        qaLabel.setHorizontalAlignment(JLabel.CENTER);
        qaLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        qaLabel.setForeground(Color.white);

        enteredAnswer = new JTextField();
        enteredAnswer.setHorizontalAlignment(JLabel.CENTER);
        enteredAnswer.setFont(new Font("Monospaced", Font.BOLD, 40));

        topicPlayerScreen.setLayout(new GridLayout(4, 1));
        topicPlayerScreen.add(qaLabel);
        topicPlayerScreen.add(enteredAnswer);
        topicPlayerScreen.add(submitAnswerButton);
        topicPlayerScreen.add(mainMenuButton);
        topicPlayerScreen.setBackground(Color.darkGray);
        topicPlayerScreen.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        frame.remove(playScreen);
        frame.add(topicPlayerScreen);
        frame.revalidate();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: displays the current question for the user to answer
    private void selectedQuestionPlayer(QuestionAnswerPair q) {
        currentAnswer = q.getAnswer();
        currentQuestion = q.getQuestion();
        qaLabel.setText("Question " + count + ": " + q.getQuestion());

    }

    // EFFECTS: saves all topics to file
    public void saveAllTopic() {
        try {
            JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
            jsonWriter.open();
            jsonWriter.write(topicStorage);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "All topics were saved to " + JSON_STORE);
            //System.out.println("Saved all topics to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            //System.out.println("Unable to write to file to " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads all topics from file
    public void loadAllTopic() {
        try {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            topicStorage = jsonReader.read();
            JOptionPane.showMessageDialog(null, "All topics were loaded from " + JSON_STORE);
            //System.out.println("Loaded topics from " + JSON_STORE);
        } catch (IOException e) {
            //System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: Prints the events in this instance of event log and exits the program
    public void quitProcedure() {
        System.out.println("Trivia Application Event Log: ");
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
        System.exit(0);
    }
}

