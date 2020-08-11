package org.todolistwebapp.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.todolistwebapp.model.TodoTask;
import org.todolistwebapp.repository.TodoTaskRepository;
import org.todolistwebapp.repository.UserInfoRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IndexControllerTest {

    @Mock
    private TodoTaskRepository todoTaskRepository;

    @Mock
    private UserInfoRepository userInfoRepository;


    @Test
    public void invocationTest() {

        IndexController controller = new IndexController();

        ArrayList<TodoTask> list = new ArrayList<>();

        when(todoTaskRepository.findByOwner(anyString())).thenReturn(list);
        controller.todoTaskRepository = todoTaskRepository;

        Map<String,String> userInfo = new LinkedHashMap<>();
        userInfo.put("name", "Mustermann");
        userInfo.put("email", "muster@example.com");

        when(userInfoRepository.getUserInfo()).thenReturn(userInfo);
        controller.userInfoRepository = userInfoRepository;

        Model model = new ConcurrentModel();
        String value = controller.index(model);

        assertEquals("index", value);
        assertEquals(list, model.getAttribute("tasks"));

    }

}