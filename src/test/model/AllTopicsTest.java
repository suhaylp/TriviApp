package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AllTopicsTest {

    private AllTopics testStorage;
    private Topic testTopic;
    private Topic testTopic2;

    @BeforeEach
    void runBefore() {
        testStorage = new AllTopics();
        testTopic = new Topic("TestTopic");
        testTopic2 = new Topic("TestTopic2");
        testStorage.addToAllTopics(testTopic);
        testStorage.addToAllTopics(testTopic2);
    }

    @Test
    void testTopicAlreadyExists() {
        assertFalse(testStorage.topicAlreadyExists("Random Name"));
        assertFalse(testStorage.topicAlreadyExists("Test Topic"));
        assertTrue(testStorage.topicAlreadyExists("TestTopic"));
        assertTrue(testStorage.topicAlreadyExists("TESTTOPIC"));
        assertTrue(testStorage.topicAlreadyExists("testtopic"));
        assertTrue(testStorage.topicAlreadyExists("tEsTToPIC"));
    }

    @Test
    void testTopicFinder() {
        assertNull(testStorage.masterTopicFinder("Jeff"));
        assertEquals(testTopic, testStorage.masterTopicFinder("TestTopic"));
        assertEquals(testTopic2, testStorage.masterTopicFinder("TestTopic2"));
        assertEquals(testTopic, testStorage.masterTopicFinder("TeSTToPIC"));
    }

    @Test
    void testGetAllTopics() {
        List<Topic> allTopicList = new ArrayList<>();
        allTopicList.add(testTopic);
        allTopicList.add(testTopic2);
        assertEquals(2,testStorage.getAllTopics().size());
        assertEquals(allTopicList,testStorage.getAllTopics());
    }

    @Test
    void testGetAllTopicNames() {
        List<String> allTopicList = new ArrayList<>();
        allTopicList.add(testTopic.getName());
        allTopicList.add(testTopic2.getName());
        assertEquals(2,testStorage.getAllTopicNames().size());
        assertEquals(allTopicList,testStorage.getAllTopicNames());
    }
}
