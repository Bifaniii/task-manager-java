package com.bifani.taskmanagerjava.database.repository;

import com.bifani.taskmanagerjava.database.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITaskRepository extends JpaRepository<TaskEntity, Long> {
    Optional<TaskEntity> findByTitle(String task);
}
