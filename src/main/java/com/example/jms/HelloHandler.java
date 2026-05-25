package com.example.jms;


import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class HelloHandler {
    private final static Logger LOGGER = Logger.getLogger(HelloHandler.class.getName());
    private final List<String> messages = new CopyOnWriteArrayList<>();

    public void handle(String message) {
        LOGGER.log(Level.INFO, "handling message: {0}", message);
        messages.add(message);
    }

    public List<String> getMessages() {
        return this.messages;
    }
}
