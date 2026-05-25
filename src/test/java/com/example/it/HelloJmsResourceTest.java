package com.example.it;

import com.example.jms.HelloJmsResource;
import com.example.rest.RestActivator;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ArquillianTest
public class HelloJmsResourceTest {
    private final static Logger LOGGER = Logger.getLogger(HelloJmsResourceTest.class.getName());

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "HelloJmsResourceTest.war")
                .addPackage(HelloJmsResource.class.getPackage())
                .addClasses(RestActivator.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        LOGGER.log(Level.INFO, "war archive: {0}", war.toString(true));
        return war;
    }

    @ArquillianResource
    URL baseUrl;

    private Client client;

    @BeforeEach
    public void setup() {
        LOGGER.log(Level.INFO, "base URL:{0}", new Object[]{baseUrl});
        this.client = ClientBuilder.newClient();
    }

    @AfterEach
    public void teardown() {
        if (this.client != null) {
            this.client.close();
        }
    }

    @Test
    public void testHelloJmsAPI() throws Exception {
        LOGGER.log(Level.INFO, " Running test:: testHelloJmsAPI ... ");
        final WebTarget helloJmsTarget = client.target(URI.create(baseUrl.toExternalForm() + "api/hellojms"));
        try (final Response response = helloJmsTarget.request()
                .accept(MediaType.TEXT_PLAIN)
                .get()) {
            assertEquals(200, response.getStatus());
            String message = response.readEntity(String.class);
            LOGGER.log(Level.INFO, " Get /hellojms response: {0} ", message);
            assertTrue(message.contains("sent"));
        }
    }
}
