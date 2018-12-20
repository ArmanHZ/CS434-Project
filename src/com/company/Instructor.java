package com.company;

public class Instructor extends User {

    public Instructor(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void sendMessage(String message) {
    }
}
