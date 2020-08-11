package org.todolistwebapp.model;

import org.junit.jupiter.api.Test;
import org.todolistwebapp.model.TodoTask;
import org.todolistwebapp.model.TodoTaskState;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTaskTest {

    @Test
    public void getNextAction() {

        TodoTask todoTask = new TodoTask("taskname");
        assertEquals("start", todoTask.getNextAction());
        todoTask.setState(TodoTaskState.IN_PROGRESS);
        assertEquals("close", todoTask.getNextAction());
        todoTask.setState(TodoTaskState.FINISHED);
        assertEquals("archive", todoTask.getNextAction());

    }

    @Test
    public void getNextActionText() {

        TodoTask todoTask = new TodoTask("taskname");
        assertEquals("Starten", todoTask.getNextActionText());
        todoTask.setState(TodoTaskState.IN_PROGRESS);
        assertEquals("Schließen", todoTask.getNextActionText());
        todoTask.setState(TodoTaskState.FINISHED);
        assertEquals("Archivieren", todoTask.getNextActionText());
    }
}