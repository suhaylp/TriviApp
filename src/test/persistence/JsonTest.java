package persistence;

import model.QuestionAnswerPair;
import model.Topic;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTopic(String topicName, List<QuestionAnswerPair> qaPairs, Topic t) {
        assertEquals(topicName, t.getName());
        assertEquals(qaPairs.size(), t.getQuestions().size());
    }
}