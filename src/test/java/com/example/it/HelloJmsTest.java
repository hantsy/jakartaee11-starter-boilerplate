package com.example.it;

import com.example.jms.HelloSender;
import com.example.jms.JmsResources;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jms.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ArquillianTest
public class HelloJmsTest {
    private final static Logger LOGGER = Logger.getLogger(HelloJmsTest.class.getName());

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "HelloJmsTest.jar")
                .addClass(JmsResources.class)
                // .addClass(HelloConsumer.class)
                .addClass(HelloSender.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        LOGGER.log(Level.INFO, "deployment war:{0}", new Object[]{javaArchive.toString(true)});
        return javaArchive;
    }

    @Inject
    HelloSender sender;

    @Inject
    JMSContext jmsContext;

    @Resource(lookup = "java:comp/jms/HelloQueue")
    private Destination helloQueue;

    @Test
    public void testHelloQueue() throws JMSException {
        JMSConsumer consumer = jmsContext.createConsumer(helloQueue);
        sender.sayHellFromJms();
        Message message = consumer.receive(1000);
        assertInstanceOf(TextMessage.class, message);
        String text = message.getBody(String.class);
        LOGGER.info("message text:" + text);
        assertTrue(text.startsWith("Hello JMS"));
        consumer.close();
    }
}
