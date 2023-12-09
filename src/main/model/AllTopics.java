package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class AllTopics implements Writable {

    private List<Topic> allTopics; // list of all topics

    public AllTopics() {
        this.allTopics = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: returns true if the entered string is already an existing topic, and false otherwise
    public boolean topicAlreadyExists(String userTopicName) {
        return masterTopicFinder(userTopicName) != null;
    }

    // MODIFIES: this
    // EFFECTS: returns the topic corresponding to the entered string if it is already an existing topic
    //          otherwise, returns null
    public Topic masterTopicFinder(String userTopicName) {
        userTopicName = userTopicName.toLowerCase();
        Topic selectedTopic = null;
        for (Topic t : this.allTopics) {
            String loweredTopicName = t.getName().toLowerCase();
            if (userTopicName.equals(loweredTopicName)) {
                selectedTopic = t;
                break;
            }
        }
        return selectedTopic;
    }

    // MODIFIES: this
    // EFFECTS: Adds t to list allTopics
    public void addToAllTopics(Topic t) {
        this.allTopics.add(t);
    }

    // EFFECTS: returns list allTopics
    public List<Topic> getAllTopics() {
        return this.allTopics;
    }

    // EFFECTS: returns the names of each Topic in allTopics as a list
    public List<String> getAllTopicNames() {
        //EventLog.getInstance().logEvent(new Event("List of all topic names displayed."));
        List<String> topicNames = new ArrayList<>();
        for (Topic t : this.allTopics) {
            topicNames.add(t.getName());
        }
        return topicNames;
    }

    // Code influced by the JsonSerizalizationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns a json object with every topic in AllTopics
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("topics", topicsToJson());
        return json;
    }

    // EFFECTS: returns things in AllTopics as a JSON array
    private JSONArray topicsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Topic t : this.allTopics) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }

}
