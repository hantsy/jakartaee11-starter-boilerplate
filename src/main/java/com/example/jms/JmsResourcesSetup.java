package com.example.jms;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.JMSDestinationDefinition;

// No need @JMSDestinationDefinitions({ }) wrapper
// due to JMSDestinationDefinition is repeatable.
@JMSDestinationDefinition(
        name = "java:comp/jms/HelloQueue",
        // resourceAdapter = "jmsra",
        interfaceName = "jakarta.jms.Queue",
        destinationName = "HelloQueue"
)
//@Singleton
//@Startup
@ApplicationScoped
public class JmsResourcesSetup {
}
