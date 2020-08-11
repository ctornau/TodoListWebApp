package org.todolistwebapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Dies ist ein Integrationstest für Spring Boot. Er fährt Spring-Boot auf einem zufälligen Port hoch
 * und setzt einen Request ab. Damit ist geprüft, dass Spring Boot ordnungsgemäß starten kann.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloWorldControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testReturnsTheString() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/helloworld",
                 String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

}
