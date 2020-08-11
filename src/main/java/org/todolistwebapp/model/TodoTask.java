package org.todolistwebapp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Die Entity des Models
 */
@Entity
public class TodoTask {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private int version;

    private String name;
    private Date creationTime;
    private Date startTime;
    private Date finishTime;

    @Enumerated(EnumType.STRING)
    private TodoTaskState state;

    private String owner;

    public TodoTask() {
        super();
    }

    public TodoTask(String name) {
        this.name = name;
        this.creationTime = new Date();
        this.state = TodoTaskState.CREATED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public TodoTaskState getState() {
        return state;
    }

    public void setState(TodoTaskState state) {
        this.state = state;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNextAction() {
        if (getState().equals(TodoTaskState.CREATED)) {
            return "start";
        }
        if (getState().equals(TodoTaskState.IN_PROGRESS)) {
            return "close";
        }
        if (getState().equals(TodoTaskState.FINISHED)) {
            return "archive";
        }
        // else
        return "noAction";
    }
    public String getNextActionText() {
        if (getState().equals(TodoTaskState.CREATED)) {
            return "Starten";
        }
        if (getState().equals(TodoTaskState.IN_PROGRESS)) {
            return "Schließen";
        }
        if (getState().equals(TodoTaskState.FINISHED)) {
            return "Archivieren";
        }
        // else
        return "";
    }


}
