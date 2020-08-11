package org.todolistwebapp.service;

import com.sendgrid.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.todolistwebapp.model.TodoTask;
import org.todolistwebapp.model.TodoTaskState;

import java.io.IOException;

@Service
public class NotificationService {

    Logger LOGGER = LogManager.getLogger(NotificationService.class.getName());


    @Value("${sendgrid.apikey:undefined}")
    private String sendgridApiKey;

    @Value("${sendgrid.senderemailaddress:sender@example.com}")
    private String senderEmailAddress;

    public void sendNotification(TodoTask todoTask) {

        Email from = new Email(senderEmailAddress);
        String subject = todoTask.getState().equals(TodoTaskState.IN_PROGRESS) ?
                "Task " + todoTask.getName() + " wurde gestartet" :
                "Task " + todoTask.getName() + " wurde fertiggestellt";
        Email to = new Email(todoTask.getOwner());
        Content content = new Content("text/html","<table>"+
                "<tr><td>Taskname</td><td>"+ todoTask.getName() +"</td></tr>\n"+
                "<tr><td>Erzeugt</td><td>"+ todoTask.getCreationTime() + "</td></tr>\n"+
                "<tr><td>Gestartet</td><td>"+ todoTask.getStartTime() + "</td></tr>\n"+
                "<tr><td>Abgeschlossen</td><td>"+ todoTask.getStartTime() + "</td></tr>\n"+
                "</table>"
                );

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendgridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }

}

