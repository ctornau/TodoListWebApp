package org.todolistwebapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.todolistwebapp.model.ArchivedTodoTask;
import org.todolistwebapp.model.TodoTask;
import org.todolistwebapp.model.TodoTaskState;
import org.todolistwebapp.repository.ArchivedTodoTaskRepository;
import org.todolistwebapp.repository.TodoTaskRepository;
import org.todolistwebapp.repository.UserInfoRepository;
import org.todolistwebapp.service.NotificationService;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActionControllerTest {

    @Mock
    private TodoTaskRepository todoTaskRepository;

    @Mock
    ArchivedTodoTaskRepository archivedTodoTaskRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    NotificationService notificationService;

    @Test
    void doActionStart() {

        ActionController controller = new ActionController();

        TodoTask task = new TodoTask("name");
        task.setOwner("muster@example.com");
        when(todoTaskRepository.getOne(anyLong())).thenReturn(task);
        when(todoTaskRepository.save(task)).thenReturn(task);
        controller.todoTaskRepository = todoTaskRepository;

        Map<String,String> userInfo = new LinkedHashMap<>();
        userInfo.put("name", "Mustermann");
        userInfo.put("email", "muster@example.com");

        when(userInfoRepository.getUserInfo()).thenReturn(userInfo);
        controller.userInfoRepository = userInfoRepository;

        controller.notificationService = notificationService;

        Model model = new ConcurrentModel();
        String value = controller.doAction(model, 1L, "start");

        assertEquals("redirect:index", value);

        verify(todoTaskRepository, times(1)).getOne(1L);
        verify(todoTaskRepository, times(1)).save(task);
        verify(notificationService, times(1)).sendNotification(task);

        assertEquals(TodoTaskState.IN_PROGRESS, task.getState());

    }


    @Test
    void doActionFinish() {

        ActionController controller = new ActionController();

        TodoTask task = new TodoTask("name");
        task.setOwner("muster@example.com");
        task.setState(TodoTaskState.IN_PROGRESS);
        when(todoTaskRepository.getOne(anyLong())).thenReturn(task);
        when(todoTaskRepository.save(task)).thenReturn(task);
        controller.todoTaskRepository = todoTaskRepository;

        Map<String,String> userInfo = new LinkedHashMap<>();
        userInfo.put("name", "Mustermann");
        userInfo.put("email", "muster@example.com");

        when(userInfoRepository.getUserInfo()).thenReturn(userInfo);
        controller.userInfoRepository = userInfoRepository;

        controller.notificationService = notificationService;

        Model model = new ConcurrentModel();
        String value = controller.doAction(model, 1L, "close");

        assertEquals("redirect:index", value);

        verify(todoTaskRepository, times(1)).getOne(1L);
        verify(todoTaskRepository, times(1)).save(task);
        verify(notificationService, times(1)).sendNotification(task);


        assertEquals(TodoTaskState.FINISHED, task.getState());

    }

    @Test
    void doActionArchive() {

        ActionController controller = new ActionController();

        TodoTask task = new TodoTask("name");
        task.setOwner("muster@example.com");
        task.setState(TodoTaskState.FINISHED);
        when(todoTaskRepository.getOne(anyLong())).thenReturn(task);
        controller.todoTaskRepository = todoTaskRepository;

        Map<String,String> userInfo = new LinkedHashMap<>();
        userInfo.put("name", "Mustermann");
        userInfo.put("email", "muster@example.com");

        when(userInfoRepository.getUserInfo()).thenReturn(userInfo);
        controller.userInfoRepository = userInfoRepository;

        ArchivedTodoTask archivedTodoTask = new ArchivedTodoTask(task);
        when(archivedTodoTaskRepository.save(any(ArchivedTodoTask.class))).thenReturn(archivedTodoTask);
        controller.archivedTodoTaskRepository = archivedTodoTaskRepository;

        Model model = new ConcurrentModel();
        String value = controller.doAction(model, 1L, "archive");

        assertEquals("redirect:index", value);

        verify(todoTaskRepository, times(1)).getOne(1L);
        verify(todoTaskRepository, times(1)).delete(task);
        verify(archivedTodoTaskRepository, times(1)).save(any(ArchivedTodoTask.class));
    }

    @Test
    void doActionNothing() {

        ActionController controller = new ActionController();

        TodoTask task = new TodoTask("name");
        task.setOwner("muster@example.com");
        when(todoTaskRepository.getOne(anyLong())).thenReturn(task);
        controller.todoTaskRepository = todoTaskRepository;

        Map<String,String> userInfo = new LinkedHashMap<>();
        userInfo.put("name", "Mustermann");
        userInfo.put("email", "muster@example.com");

        when(userInfoRepository.getUserInfo()).thenReturn(userInfo);
        controller.userInfoRepository = userInfoRepository;

        Model model = new ConcurrentModel();
        String value = controller.doAction(model, 1L, "NOTHING");

        assertEquals("redirect:index", value);

        verify(todoTaskRepository, times(1)).getOne(1L);

        assertEquals(TodoTaskState.CREATED, task.getState());

    }
}