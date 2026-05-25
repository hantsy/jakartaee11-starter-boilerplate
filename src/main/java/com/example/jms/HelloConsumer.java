package com.example.jms;


import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import java.util.logging.Level;
import java.util.logging.Logger;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName = "destinationType",
                        propertyValue = "jakarta.jms.Queue"),
                @ActivationConfigProperty(
                        propertyName = "destinationLookup",
                        propertyValue = "java:comp/jms/HelloQueue")
        }
)
public class HelloConsumer implements MessageListener {
    private static final Logger LOGGER = Logger.getLogger(HelloConsumer.class.getName());

    @Inject
    HelloHandler handler;
    
    @Override
    public void onMessage(Message message) {
        try {
            String body = message.getBody(String.class);
            LOGGER.log(Level.INFO, "received message: {0}", body);
            handler.handle(body);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
