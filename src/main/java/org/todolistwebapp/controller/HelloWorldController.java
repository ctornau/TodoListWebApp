package org.todolistwebapp.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dies ist ein Demo-Controller der einen einfachen Textstring ausgibt. Er dient zur Anfangsdemonstration.
 */
@RestController
public class HelloWorldController {

    @RequestMapping("/helloworld")
    public String index() {
        return "Hello World! Dies ist eine Beispiel-Spring-Boot Applikation";
    }

}