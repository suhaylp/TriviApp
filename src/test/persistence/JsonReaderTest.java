package persistence;


import model.AllTopics;
import model.QuestionAnswerPair;
import model.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AllTopics at = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAllTopics() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAllTopics.json");
        try {
            AllTopics at = reader.read();
            assertEquals(0, at.getAllTopics().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAllTopics() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAllTopics.json");
        try {
            AllTopics at = reader.read();
            List<Topic> allTopics = at.getAllTopics();
            assertEquals(2, allTopics.size());
            List<QuestionAnswerPair> mathQuestions = new ArrayList<>();
            mathQuestions.add(new QuestionAnswerPair("What is 5 + 5?", "10"));
            mathQuestions.add(new QuestionAnswerPair("What is 7 * 4?", "28"));
            checkTopic("Math", mathQuestions, allTopics.get(0));
            List<QuestionAnswerPair> musicQuestions = new ArrayList<>();
            checkTopic("Music", musicQuestions, allTopics.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}