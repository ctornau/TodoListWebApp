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

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateControllerTest {

    @Mock
    private TodoTaskRepository todoTaskRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Test
    void create() {
        CreateController controller = new CreateController();

        Model model = new ConcurrentModel();
        String value = controller.create(model);

        assertEquals("create", value);
        assertNotNull(model.getAttribute("todoTask"));
    }

    @Test
    void save() {

        CreateController controller = new CreateController();

        TodoTask task = new TodoTask("name");
        when(todoTaskRepository.save(task)).thenReturn(task);
        controller.todoTaskRepository = todoTaskRepository;

        Map<String,String> userInfo = new LinkedHashMap<>();
        userInfo.put("name", "Mustermann");
        userInfo.put("email", "muster@example.com");

        when(userInfoRepository.getUserInfo()).thenReturn(userInfo);
        controller.userInfoRepository = userInfoRepository;

        Model model = new ConcurrentModel();
        String value = controller.save(task);

        assertEquals("redirect:index", value);

        verify(todoTaskRepository, times(1)).save(task);

    }
}