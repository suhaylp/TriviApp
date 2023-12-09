package model;

// Represents a specific question and its corresponding answer
public class QuestionAnswerPair {

    private final String question; // the question portion of this q/a pair
    private final String answer; // the answer portion of this q/a pair

    // REQUIRES: question and answer have non-zero length
    // EFFECTS: question in Q/A pair is set to question, answer in Q/A pair is set to answer
    public QuestionAnswerPair(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // EFFECTS: returns the question from this QA pair
    public String getQuestion() {
        return this.question;
    }

    // EFFECTS: returns the answer from this QA pair
    public String getAnswer() {
        return this.answer;
    }
}

