package com.company;

public interface Mediator {

    void send(User from, String message);
    int getMessagesSize();
    String getMessageAt(int index);
}
