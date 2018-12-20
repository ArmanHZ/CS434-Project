package com.company;

import java.util.Iterator;
import java.util.function.Consumer;

public class MessageIterator implements Iterator {

    private Mediator mediator;
    private int index;

    public MessageIterator(Mediator mediator) {
        this.mediator = mediator;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return mediator.getMessagesSize() > index;
    }

    @Override
    public Object next() {
        String message = mediator.getMessageAt(index);
        index++;
        return message;
    }

    @Override
    public void remove() {
    }

    @Override
    public void forEachRemaining(Consumer action) {
    }
}
