package ui.consoleversion;

import model.AllTopics;
import model.QuestionAnswerPair;
import model.Topic;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


// TriviaApp application
public class TriviaApp {

    private AllTopics topicStorage;
    private static String username; //the user's name
    private Scanner input; //used for user input based selections

    private static String JSON_STORE; //name of file data with AllTopics saved data
    private Topic userCreatedTopic; //stores the most recent topic created by user

    // EFFECTS: begins the TriviaApp by initializes topics and topicNames to empty lists and running initial methods
    public TriviaApp() throws FileNotFoundException {
        topicStorage = new AllTopics();
        welcomeNameSelect();
        startSelection();
    }


    // MODIFIES: this
    // EFFECTS: welcomes the user and stores their input as this.name
    private void welcomeNameSelect() {
        System.out.println("Welcome to Trivia App. Please enter your name: ");
        input = new Scanner(System.in);
        username = input.nextLine();
        System.out.println("Hello " + username + "!");
    }

    // EFFECTS: displays main menu options and runs the main menu chooser
    private void startSelection() {
        System.out.println("Please choose from one of the following options to begin using the app: ");
        mainMenuOptions();
        mainMenuChooser();
    }

    // EFFECTS: prints main menu options corresponding to game modes
    private void mainMenuOptions() {
        System.out.println("\tp -> Play Mode");
        System.out.println("\te -> Edit Mode");
        System.out.println("\ts -> Save Topics");
        System.out.println("\tl -> Load Topics");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: prints edit menu options corresponding to editor methods
    private void editMenuOptions() {
        System.out.println("\tc -> Create a new topic");
        System.out.println("\te -> Edit an existing topic");
        System.out.println("\tm -> Main Menu");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: prints topic editor options corresponding to topic modification methods
    private void topicEditOptions(Topic t) {
        System.out.println("\ta -> Add new question/answer pairs to " + t.getName());
        System.out.println("\tr -> Remove question/answer pairs from " + t.getName());
        System.out.println("\tm -> Main Menu");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: accepts a user input based on main menu options and runs the corresponding method
    private void mainMenuChooser() {
        input = new Scanner(System.in);
        String selection = input.next();
        selection = selection.toLowerCase();
        switch (selection) {
            case "p":
                playModeStarter();
                break;
            case "e":
                editMode();
                break;
            case "s":
                saveAllTopic();
                break;
            case "l":
                loadAllTopic();
                break;
            case "q":
                triviaQuit();
                break;
            default:
                System.out.println("Invalid entry.");
                startSelection();
        }
    }

    // EFFECTS: prints welcome runs topicChooser() if at least 1 topic exists. Otherwise, sends user back to menu
    private void playModeStarter() {
        if (!topicStorage.getAllTopics().isEmpty()) {
            System.out.println("Time to play!");
            topicChooser();
        } else {
            System.out.println("Sorry, you must either load or create at least one topic to play with!");
            System.out.println("Please choose from one of the following options: ");
            mainMenuOptions();
            mainMenuChooser();
        }

    }

    // EFFECTS: prints welcome and runs editMenuChooser()
    private void editMode() {
        System.out.println("Welcome to the topic editor.");
        editMenuChooser();
    }

    // EFFECTS: prints a goodbye message
    private void triviaQuit() {
        System.out.println("Thank you for using 'Thematic TriviaApp' " + username + ", see you next time!");
        System.exit(0);
    }

    // EFFECTS: prints topicNames and runs topicFinder() with user input
    private void topicChooser() {
        System.out.println("Choose from the following topics: ");
        System.out.println(topicStorage.getAllTopicNames());
        input = new Scanner(System.in);
        String choice = input.nextLine();
        if (topicStorage.masterTopicFinder(choice) != null) {
            selectedTopicPlayer(topicStorage.masterTopicFinder(choice));
        } else {
            System.out.println("The topic you entered was not found.");
            topicChooser();
        }
    }

    // EFFECTS: prints the user's trivia result, then uses their input (Y/N) to either:
    //          Y: send them back to main menu
    //          N (or any other response): end the game

    private void endOfTrivia(String choice, int score, int totalNumQuestions) {
        System.out.println("Thank you for playing the " + choice + " trivia! ");
        System.out.println("You got " + score + "/" + totalNumQuestions + " questions correct.");
        System.out.println("Would you like to return to the main menu? (Y/N)");
        input = new Scanner(System.in);
        String response = input.next();
        response = response.toLowerCase();
        if (response.equals("y")) {
            startSelection();
        } else {
            triviaQuit();
        }
    }


    // EFFECTS: runs method corresponding to the user input from editMenuOptions();
    private void editMenuChooser() {
        System.out.println("Please select from the options below: ");
        editMenuOptions();
        input = new Scanner(System.in);
        String selection = input.next();
        switch (selection.toLowerCase()) {
            case "c":
                topicCreator();
                System.out.println("You must add at least 1 question/answer pair to your topic.");
                addQuestionsToTopic(userCreatedTopic);
                break;
            case "e":
                questionToEdit();
                break;
            case "m":
                startSelection();
                break;
            case "q":
                triviaQuit();
                break;
            default:
                System.out.println("Invalid entry.");
                editMenuChooser();
        }
    }


    // MODIFIES: this
    // EFFECTS: checks if the user input already exists as a topic
    //           if it does: prints an error and allows the user to enter another name
    //           if it doesn't: creates a new topic with that name
    private void topicCreator() {
        System.out.println("Please enter your new topic's name: ");
        input = new Scanner(System.in);
        String topicName = input.nextLine();

        if (topicStorage.topicAlreadyExists(topicName)) {
            System.out.println("Sorry, this topic already exists. Please select another topic name: ");
            topicCreator();
        } else {
            userCreatedTopic = new Topic(topicName);
            topicStorage.addToAllTopics(userCreatedTopic);
            System.out.println("The topic " + topicName + " has been created.");

        }
    }

    // MODIFIES: this, t
    // EFFECTS: adds an arbitrary number of Q/A pairs to t based on user input, then sends them back to the main menu
    private void addQuestionsToTopic(Topic t) {
        int numQuestionsAdded = 0;
        System.out.println("Enter a question, with a question mark: ");
        Scanner userQuestion = new Scanner(System.in);
        String question = userQuestion.nextLine();
        System.out.println("Enter the answer: ");
        Scanner userAnswer = new Scanner(System.in);
        String answer = userAnswer.nextLine();
        QuestionAnswerPair qaPair = new QuestionAnswerPair(question, answer);
        t.addQuestion(qaPair);
        numQuestionsAdded++;
        System.out.println("Your question/answer pair was successfully added! Would you like to add another? (Y/N)");
        Scanner userResponse = new Scanner(System.in);
        String response = userResponse.next().toLowerCase();
        if (response.equals("y")) {
            addQuestionsToTopic(t);
        } else {
            System.out.println(numQuestionsAdded + " question(s) successfully added to " + t.getName());
            startSelection();
        }


    }

    // EFFECTS: runs topicEditor with user input if corresponding topic is found, otherwise prompts user to enter
    //          a valid topic name
    private void questionToEdit() {
        if (!topicStorage.getAllTopics().isEmpty()) {
            System.out.println("Select one of the following topics to edit: ");
            System.out.println(topicStorage.getAllTopicNames());
            input = new Scanner(System.in);
            Topic topicSel = topicStorage.masterTopicFinder(input.nextLine());
            if (topicSel != null) {
                topicEditChooser(topicSel);
            } else {
                System.out.println("Invalid topic, please try again");
                questionToEdit();
            }
        } else {
            System.out.println("There are no topics to edit. "
                    + "Please import or create at least one topic to use the app.");
            editMenuChooser();
        }
    }

    // EFFECTS: runs method corresponding to the user input from topicEditOptions();
    private void topicEditChooser(Topic editingTopic) {
        System.out.println("Please select from the options below: ");
        topicEditOptions(editingTopic);
        input = new Scanner(System.in);
        String selection = input.next();
        selection = selection.toLowerCase();
        switch (selection) {
            case "a":
                addQuestionsToTopic(editingTopic);
                break;
            case "r":
                removeQuestionsInTopic(editingTopic);
                break;
            case "m":
                startSelection();
                break;
            case "q":
                System.out.println("Thank you for using 'Thematic TriviaApp' " + username + ", see you next time!");
                break;
            default:
                System.out.println("Invalid entry.");
                topicEditChooser(editingTopic);
        }
    }

    // MODIFIES: this, topic
    // EFFECTS: if currentTopic has more than 1 question answer pair, runs the questionDeleter, otherwise returns user
    //          to the edit menu
    public void removeQuestionsInTopic(Topic editingTopic) {
        List<QuestionAnswerPair> currentTopicQuestions = editingTopic.getQuestions();
        if (currentTopicQuestions.size() <= 1) {
            System.out.println("This topic only has one question, "
                    + "you must add another question before removing a question.");
            topicEditChooser(editingTopic);
        } else {
            displayQuestions(currentTopicQuestions);
            questionDeleter(editingTopic, currentTopicQuestions);
        }

    }


    // EFFECTS: prints a list of questions in the current topic and user options to go back / main menu
    private void displayQuestions(List<QuestionAnswerPair> currentTopicQuestions) {
        int questionNumber = 0;
        List<String> viewedTopicQuestions = new ArrayList<>();
        for (QuestionAnswerPair q : currentTopicQuestions) {
            questionNumber++;
            viewedTopicQuestions.add("Question " + questionNumber + ": " + q.getQuestion());
        }
        System.out.println(viewedTopicQuestions);
        System.out.println("Type the number corresponding to the question you'd like to delete,"
                + " type 'b' to go back to the previous menu, or 'm' to go to the main menu.");
    }

    // REQUIRES: currentTopicQuestions must have more than 1 item in the list
    // MODIFIES: this, editingTopic
    // EFFECTS: if found, deletes the specified Q/A pair from editingTopic. otherwise runs removeInputInterpreter
    private void questionDeleter(Topic editingTopic, List<QuestionAnswerPair> currentTopicQuestions) {
        input = new Scanner(System.in);
        String indexInput = input.nextLine();
        boolean questionDeleted = false;
        if (indexInput.matches(("\\d+"))) {
            int indexToDelete = Integer.parseInt(indexInput);
            int questionParser = 0;
            for (QuestionAnswerPair q : currentTopicQuestions) {
                questionParser++;
                if (indexToDelete == questionParser) {
                    editingTopic.deleteQuestion(q);
                    System.out.println("Question " + questionParser + " has been deleted.");
                    removeQuestionsInTopic(editingTopic);
                    questionDeleted = true;
                    break;
                }
            }
        }
        removeInputInterpreter(questionDeleted, editingTopic, indexInput);
    }

    // EFFECTS: checks user input from the removeQuestion interface and runs the corresponding method
    private void removeInputInterpreter(boolean questionFound, Topic editingTopic, String indexInput) {
        if (indexInput.equalsIgnoreCase("b")) {
            topicEditChooser(editingTopic);
        } else if (indexInput.equalsIgnoreCase("m")) {
            startSelection();
        } else if (!questionFound) {
            System.out.println("Input does not correspond to a question in the list.");
            removeQuestionsInTopic(editingTopic);
        } else {
            System.out.println("Invalid input.");
            removeQuestionsInTopic(editingTopic);
        }
    }

    // REQUIRES: topic has more at least 1 question answer pair
    // EFFECTS: runs through the questions in selectedTopic, adds one for each correct answer, then runs endOfTrivia
    private void selectedTopicPlayer(Topic selectedTopic) {
        System.out.println("You selected " + selectedTopic.getName() + " TriviaApp!");
        List<QuestionAnswerPair> currentQuestions = selectedTopic.getQuestions();
        int totalNumQuestions = currentQuestions.size();
        int score = 0;
        int count = 1;
        for (QuestionAnswerPair q : currentQuestions) {
            System.out.println("Question " + count + ": " + q.getQuestion());
            count++;
            input = new Scanner(System.in);
            String answer = input.nextLine().toLowerCase();
            if (!answer.equals(q.getAnswer().toLowerCase())) {
                System.out.println("Incorrect! The correct answer was: " + q.getAnswer());
            } else {
                score++;
                System.out.println("Correct!");
            }
        }
        endOfTrivia(selectedTopic.getName(), score, totalNumQuestions);
    }

    // EFFECTS: saves all topics to file
    private void saveAllTopic() {
        try {
            System.out.println("Enter a name for your save file: ");
            input = new Scanner(System.in);
            JSON_STORE = "./data/" + input.nextLine() + ".json";
            JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
            jsonWriter.open();
            jsonWriter.write(topicStorage);
            jsonWriter.close();
            System.out.println("Saved all topics to " + JSON_STORE);
            startSelection();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file to " + JSON_STORE);
            startSelection();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads all topics from file
    private void loadAllTopic() {
        try {
            System.out.println("Enter the name of the file you'd like to open: ");
            input = new Scanner(System.in);
            JSON_STORE = "./data/" + input.nextLine() + ".json";
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            topicStorage = jsonReader.read();
            System.out.println("Loaded topics from " + JSON_STORE);
            startSelection();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            startSelection();
        }
    }

}