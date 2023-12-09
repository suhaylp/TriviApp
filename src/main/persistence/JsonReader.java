// Code influenced by the JsonSerizalizationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import model.AllTopics;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Topic;
import model.QuestionAnswerPair;
import org.json.*;

// Represents a reader that reads allTopics from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads allTopics from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AllTopics read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAllTopics(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses allTopics from JSON object and returns it
    private AllTopics parseAllTopics(JSONObject jsonObject) {
        AllTopics at = new AllTopics();
        addTopics(jsonObject, at);
        return at;
    }

    // MODIFIES: at
    // EFFECTS: parses topics from JSON object and adds them to allTopics
    private void addTopics(JSONObject jsonObject, AllTopics at) {
        JSONArray jsonArray = jsonObject.getJSONArray("topics");
        for (Object json : jsonArray) {
            JSONObject nextTopic = (JSONObject) json;
            addTopic(nextTopic, at);
        }
    }

    // MODIFIES: at
    // EFFECTS: parses topic, its name and qa pairs from JSON object and adds it to allTopics
    private void addTopic(JSONObject jsonObject, AllTopics at) {
        String topicName = jsonObject.getString("topic name");
        Topic currentTopic = new Topic(topicName);
        at.addToAllTopics(currentTopic);
        JSONArray jsonArray = jsonObject.getJSONArray("q/a pairs");
        for (Object json : jsonArray) {
            JSONObject nextQAP = (JSONObject) json;
            String question = nextQAP.getString("question");
            String answer = nextQAP.getString("answer");
            currentTopic.addQuestion(new QuestionAnswerPair(question, answer));
        }
    }
}



