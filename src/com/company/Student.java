package com.company;

public class Student extends User {

    public Student(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void sendMessage(String message) {
        this.mediator.send(this, message);
    }
}
