package org.todolistwebapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.todolistwebapp.model.TodoTask;
import org.todolistwebapp.repository.TodoTaskRepository;
import org.todolistwebapp.repository.UserInfoRepository;

import java.util.List;
import java.util.Map;

/**
 * Der Startseiten-Controller
 */
@Controller
public class IndexController {

    @Autowired
    TodoTaskRepository todoTaskRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {

        Map<String,String> userAttributes = userInfoRepository.getUserInfo();

        model.addAttribute("name", " für " + userAttributes.get("name"));
        List<TodoTask> todoTaskList = todoTaskRepository.findByOwner((String) userAttributes.get("email"));
        model.addAttribute("tasks", todoTaskList);
        return "index";
    }
}
