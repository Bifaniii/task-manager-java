package com.bifani.taskmanagerjava.database.repository;

import com.bifani.taskmanagerjava.database.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskEntity, Long> {
    Optional<TaskEntity> findByTitle(String task);
    List<TaskEntity> findByUserId(UUID userId);
    Optional<TaskEntity> findByIdAndUserId(Long id, UUID userId);
    Optional<TaskEntity> findByTitleAndUserId(String title, UUID userId);
}
