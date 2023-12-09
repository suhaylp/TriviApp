package persistence;

import model.AllTopics;
import model.QuestionAnswerPair;
import model.Topic;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            AllTopics at = new AllTopics();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAllTopics.json");
            writer.open();
            writer.write(at);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAllTopics.json");
            at = reader.read();
            assertEquals(0, at.getAllTopics().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAllTopics() {
        try {
            AllTopics at = new AllTopics();
            Topic math = new Topic("Math");
            Topic music = new Topic("Music");

            List<QuestionAnswerPair> mathQuestions = new ArrayList<>();
            mathQuestions.add(new QuestionAnswerPair("What is 5 + 5?", "10"));
            mathQuestions.add(new QuestionAnswerPair("What is 7 * 4?", "28"));
            math.setQuestions(mathQuestions);
            at.addToAllTopics(math);
            at.addToAllTopics(music);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAllTopics.json");
            writer.open();
            writer.write(at);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAllTopics.json");
            at = reader.read();
            List<Topic> allTopics = at.getAllTopics();
            assertEquals(2, allTopics.size());
            checkTopic("Math", mathQuestions, allTopics.get(0));
            List<QuestionAnswerPair> musicQuestions = new ArrayList<>();
            checkTopic("Music", musicQuestions, allTopics.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}