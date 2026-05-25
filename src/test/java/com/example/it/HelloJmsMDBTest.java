package com.example.it;

import com.example.jms.HelloConsumer;
import com.example.jms.HelloHandler;
import com.example.jms.HelloSender;
import com.example.jms.JmsResourcesSetup;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jms.Destination;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ArquillianTest
public class HelloJmsMDBTest {
    private final static Logger LOGGER = Logger.getLogger(HelloJmsMDBTest.class.getName());

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "HelloJmsTest.war")
                .addClasses(
                        JmsResourcesSetup.class,
                        HelloHandler.class,
                        HelloSender.class,
                        HelloConsumer.class
                )
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        LOGGER.log(Level.INFO, "deployment war:{0}", new Object[]{war.toString(true)});
        return war;
    }

    @Inject
    HelloSender sender;

    @Inject
    HelloHandler handler;

    @Resource(lookup = "java:comp/jms/HelloQueue")
    private Destination helloQueue;

    @Test
    public void testHelloQueue() throws Exception {
        sender.sayHellFromJms();

        Thread.sleep(1000);
        List<String> messages = handler.getMessages();
        assertNotNull(messages);
        assertEquals(1, messages.size());

        assertTrue(messages.getFirst().startsWith("Hello JMS"));
    }
}
