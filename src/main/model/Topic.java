package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents a topic with a name and corresponding list of Q/A pairs

public class Topic implements Writable {

    private final String name; // topic name
    private List<QuestionAnswerPair> questions; // list of Q/A pairs in this topic

    // REQUIRES: name has a non-zero length
    // MODIFIES: thi
    // EFFECTS: creates a new topic with name "name" and an empty list of questions
    public Topic(String name) {
        this.name = name;
        questions = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("New topic " + name + " created."));
    }

    // MODIFIES: this
    // EFFECTS: adds the specified Q/A pair to the topic
    public void addQuestion(QuestionAnswerPair q) {
        this.questions.add(q);
        EventLog.getInstance().logEvent(new Event("Question Answer Pair added to " + name + "."));
    }

    // MODIFIES: this
    // EFFECTS: removes the specified Q/A pair from the topic
    public void deleteQuestion(QuestionAnswerPair q) {
        this.questions.remove(q);
        EventLog.getInstance().logEvent(new Event("Question Answer Pair deleted from " + name + "."));
    }

    // MODIFIES: this
    // EFFECTS: sets this.questions to the inputted question answer list
    public void setQuestions(List<QuestionAnswerPair> qaList) {
        this.questions = qaList;
    }

    // EFFECTS: returns this question answer list
    public List<QuestionAnswerPair> getQuestions() {
        EventLog.getInstance().logEvent(new Event("List of all questions in topic " + name + " displayed."));
        return this.questions;
    }

    // EFFECTS: returns this topic's name
    public String getName() {
        return this.name;
    }

    // Code influenced by the JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: puts this topic's name and list of qa pairs into a JSON object and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("topic name", this.name);
        json.put("q/a pairs", this.questions);
        return json;
    }
}
