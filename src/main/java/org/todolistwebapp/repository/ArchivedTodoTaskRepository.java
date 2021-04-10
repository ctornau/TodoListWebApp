package org.todolistwebapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.todolistwebapp.model.ArchivedTodoTask;

@Repository
public interface ArchivedTodoTaskRepository extends MongoRepository<ArchivedTodoTask, Long> {
}
