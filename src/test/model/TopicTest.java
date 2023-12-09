package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopicTest {
    private Topic testTopic;
    private QuestionAnswerPair testQuestion1;
    private QuestionAnswerPair testQuestion2;

    @BeforeEach
    void runBefore() {
        testTopic = new Topic("TestTopic");
        testQuestion1 = new QuestionAnswerPair("Test Question", "Test Answer");
        testQuestion2 = new QuestionAnswerPair("Test Question 2", "Test Answer 2");
    }

    @Test
    void testConstructor() {
        assertEquals("TestTopic", testTopic.getName());
        assertEquals(new ArrayList<>(), testTopic.getQuestions());
    }

    @Test
    void testAddQuestion() {
        assertEquals(0 ,testTopic.getQuestions().size());
        assertFalse(testTopic.getQuestions().contains(testQuestion1));
        assertFalse(testTopic.getQuestions().contains(testQuestion2));

        testTopic.addQuestion(testQuestion1);
        assertEquals(1 ,testTopic.getQuestions().size());
        assertTrue(testTopic.getQuestions().contains(testQuestion1));
        assertFalse(testTopic.getQuestions().contains(testQuestion2));

        testTopic.addQuestion(testQuestion2);
        assertEquals(2 ,testTopic.getQuestions().size());
        assertTrue(testTopic.getQuestions().contains(testQuestion1));
        assertTrue(testTopic.getQuestions().contains(testQuestion2));
    }

    @Test
    void testDeleteQuestion() {
        testTopic.addQuestion(testQuestion1);
        testTopic.addQuestion(testQuestion2);
        assertEquals(2 ,testTopic.getQuestions().size());
        assertTrue(testTopic.getQuestions().contains(testQuestion1));
        assertTrue(testTopic.getQuestions().contains(testQuestion2));

        testTopic.deleteQuestion(testQuestion1);
        assertEquals(1 ,testTopic.getQuestions().size());
        assertFalse(testTopic.getQuestions().contains(testQuestion1));
        assertTrue(testTopic.getQuestions().contains(testQuestion2));
    }

    @Test
    void testSetQuestion() {
        List<QuestionAnswerPair> newQAList = new ArrayList<>();
        newQAList.add(testQuestion1);
        newQAList.add(testQuestion2);
        testTopic.setQuestions(newQAList);
        assertEquals(2, testTopic.getQuestions().size());
        assertEquals(newQAList, testTopic.getQuestions());
    }

}
