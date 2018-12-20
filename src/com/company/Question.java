package com.company;

public class Question {
    private String description;
    private String answer;
    private String type;

    public Question(String description, String answer, String type) {
        this.description = description;
        this.answer = answer;
        this.type = type;
    }

    public String toString() {
        return "Question text: " + description + ", answer:" + answer + ", type:" + type;
    }

    public String getDescription() {
        return description;
    }

    public String getAnswer() {
        return answer;
    }

}
