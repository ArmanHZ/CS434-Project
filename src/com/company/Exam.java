package com.company;

import java.util.ArrayList;

public class Exam {

    private ArrayList<Question> questions;
    private String examName;

    public Exam(String examName) {
        this.examName = examName;
        questions = new ArrayList<>();
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String toString() {
        return examName;
    }

}
