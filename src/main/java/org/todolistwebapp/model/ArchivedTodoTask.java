package org.todolistwebapp.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class ArchivedTodoTask {

    @Id
    public String id;

    private Long originalId;
    private String name;
    private Date creationTime;
    private Date startTime;
    private Date finishTime;
    private TodoTaskState state;

    public ArchivedTodoTask() {}

    public ArchivedTodoTask(TodoTask todoTask) {
        this.originalId = todoTask.getId();
        this.name = todoTask.getName();
        this.creationTime = todoTask.getCreationTime();
        this.startTime = todoTask.getStartTime();
        this.finishTime = todoTask.getFinishTime();
        this.state = todoTask.getState();
    }

    @Override
    public String toString() {
        return "ArchivedTodoTask{" +
                "id='" + id + '\'' +
                ", originalId=" + originalId +
                ", name='" + name + '\'' +
                ", creationTime=" + creationTime +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", state=" + state +
                '}';
    }
}
