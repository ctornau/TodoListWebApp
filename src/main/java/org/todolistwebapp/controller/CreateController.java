package org.todolistwebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.todolistwebapp.model.TodoTask;
import org.todolistwebapp.model.TodoTaskState;
import org.todolistwebapp.repository.TodoTaskRepository;
import org.todolistwebapp.repository.UserInfoRepository;

import java.util.Date;
import java.util.Map;

/**
 * Dieser Controller dient dazu, ein neues Item anzulegen.
 */
@Controller
public class CreateController {

    @Autowired
    TodoTaskRepository todoTaskRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public String create(Model model) {
        TodoTask todoTask = new TodoTask();
        model.addAttribute("todoTask", todoTask);
        return "create";
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String save(@ModelAttribute("todoTask") TodoTask todoTask) {

        Map<String,String> userAttributes = userInfoRepository.getUserInfo();
        todoTask.setOwner(userAttributes.get("email"));

        todoTask.setState(TodoTaskState.CREATED);
        todoTask.setCreationTime(new Date());
        todoTaskRepository.save(todoTask);
        return "redirect:index";
    }
}
