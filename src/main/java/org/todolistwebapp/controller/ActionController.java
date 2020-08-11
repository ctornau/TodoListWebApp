package org.todolistwebapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.todolistwebapp.model.ArchivedTodoTask;
import org.todolistwebapp.model.TodoTask;
import org.todolistwebapp.model.TodoTaskState;
import org.todolistwebapp.repository.ArchivedTodoTaskRepository;
import org.todolistwebapp.repository.TodoTaskRepository;
import org.todolistwebapp.repository.UserInfoRepository;
import org.todolistwebapp.service.NotificationService;

import java.util.Date;


/**
 * Mit Hilfe dieses Controllers werden Aktionen auf den Items durchgeführt.
 */
@Controller
public class ActionController {

    Logger LOGGER = LogManager.getLogger(ActionController.class.getName());

    @Autowired
    TodoTaskRepository todoTaskRepository;

    @Autowired
    ArchivedTodoTaskRepository archivedTodoTaskRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    NotificationService notificationService;

    @RequestMapping(value = {"/action"}, method = RequestMethod.GET)
    public String doAction(Model model, @RequestParam("id") long id, @RequestParam("action") String action) {

        TodoTask todoTask = todoTaskRepository.getOne(id);

        String ownerEmail = userInfoRepository.getUserInfo().get("email");

        // Änderung nur erlauben, wenn der richtige Benutzer die Änderung veranlasst
        if (todoTask.getOwner().equals(ownerEmail)) {

            if (action.equals("start")) {
                todoTask.setState(TodoTaskState.IN_PROGRESS);
                todoTask.setStartTime(new Date());
                // Benachrichtigungsemail versenden
                notificationService.sendNotification(todoTask);
                // Task in der Datenbank persistieren
                todoTaskRepository.save(todoTask);
            }
            if (action.equals("close")) {
                todoTask.setState(TodoTaskState.FINISHED);
                todoTask.setFinishTime(new Date());
                // Benachrichtigungsemail versenden
                notificationService.sendNotification(todoTask);
                // Task in der Datenbank persistieren
                todoTaskRepository.save(todoTask);
            }
            if (action.equals("archive")) {
                ArchivedTodoTask archivedTodoTask = new ArchivedTodoTask(todoTask);
                archivedTodoTaskRepository.save(archivedTodoTask);
                todoTaskRepository.delete(todoTask);
            }


        } else {
            LOGGER.warn ("Falscher Nutzer {} versucht auf Task {} zuzugreifen. Abgelehnt.", ownerEmail, id);
        }

        return "redirect:index";
    }
}