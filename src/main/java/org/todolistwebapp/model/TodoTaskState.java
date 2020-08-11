package org.todolistwebapp.model;

public enum TodoTaskState {
    CREATED("Noch nicht begonnen"),
    IN_PROGRESS("In Arbeit"),
    FINISHED("Abgeschlossen");

    private final String displayValue;

    private TodoTaskState(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

}
