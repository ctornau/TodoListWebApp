package org.todolistwebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.todolistwebapp.model.TodoTask;

import java.util.List;

/**
 * Handelt den Zugriff auf die Tasks als Data Access Object (DAO)
 */
@Repository
public interface TodoTaskRepository extends JpaRepository<TodoTask, Long> {

    /**
     * Findet alle Tasks für einen Benutzer
     * @param owner der Benutzerstring, die Emailadresse des Benutzers.
     * @return Die Liste an Tasks
     */
    List<TodoTask> findByOwner(String owner);
}
