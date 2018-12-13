package com.company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MediatorImpl implements Mediator {

    private static List<String> messages = new ArrayList<String>();

    @Override
    public void send(User from, String message) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String msg = "[" + simpleDateFormat.format(date) + "] " + from.getName() + " : " + message;
        messages.add(msg);
    }

    public int getMessagesSize() {
        return messages.size();
    }

    public String getMessageAt(int index) {
        return messages.get(index);
    }
}
