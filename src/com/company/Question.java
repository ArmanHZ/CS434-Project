package com.company;

enum Type {
    TRUE_FALSE, MULTIPLE, TEST;
}

public class Question {
    private String description;
    private String answer;
    private Type type;

    public Question(String description, String answer, Type type) {
        this.description = description;
        this.answer = answer;
        this.type = type;
    }

}
