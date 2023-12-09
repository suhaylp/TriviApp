package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class QuestionAnswerPairTest {

    private QuestionAnswerPair testQuestionAnswerPair;

    @BeforeEach
    void runBefore() {
        testQuestionAnswerPair = new QuestionAnswerPair("Test Question A", "Test Answer A");
    }

    @Test
    void testConstructor() {
        assertEquals("Test Question A", testQuestionAnswerPair.getQuestion());
        assertEquals("Test Answer A", testQuestionAnswerPair.getAnswer());
    }

}