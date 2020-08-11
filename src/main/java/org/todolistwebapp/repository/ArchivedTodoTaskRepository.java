package org.todolistwebapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.todolistwebapp.model.ArchivedTodoTask;

public interface ArchivedTodoTaskRepository extends MongoRepository<ArchivedTodoTask, Long> {
}
