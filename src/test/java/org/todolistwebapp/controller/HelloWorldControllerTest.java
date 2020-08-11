package org.todolistwebapp.controller;

import org.junit.jupiter.api.Test;
import org.todolistwebapp.controller.HelloWorldController;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloWorldControllerTest {

    @Test
    public void invocationTest() {
        assertEquals("Hello World! Dies ist eine Beispiel-Spring-Boot Applikation", (new HelloWorldController()).index());
    }

}