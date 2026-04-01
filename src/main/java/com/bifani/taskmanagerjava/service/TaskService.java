package com.bifani.taskmanagerjava.service;

import com.bifani.taskmanagerjava.database.model.TaskEntity;
import com.bifani.taskmanagerjava.database.model.TaskEnum;
import com.bifani.taskmanagerjava.database.repository.ITaskRepository;
import com.bifani.taskmanagerjava.dto.TaskRequest;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final ITaskRepository repository;

    @Transactional
    public TaskEntity createTask(TaskRequest request) {
        var task = TaskEntity.builder()
                .title(request.title())
                .description(request.description())
                .status(TaskEnum.PENDING)
                .build();

        return repository.save(task);
    }

    @Transactional(readOnly = true)
    public List<TaskEntity> getAllTasks() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public TaskEntity getTaskByTitle(String title) {
        return repository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Task com título '" + title + "' não encontrada!"));
    }

    @Transactional
    public TaskEntity getTaskById(Long id) {
        return findByIdOrThrow(id);
    }

    @Transactional
    public TaskEntity updateTask(TaskRequest request, Long id) {
        var task = findByIdOrThrow(id);

        task.setTitle(request.title());
        task.setDescription(request.description());

        return repository.save(task);
    }

    @Transactional
    public TaskEntity updateTaskStatus(Long id) {
        var task = findByIdOrThrow(id);

        if (task.getStatus() == TaskEnum.DONE) {
            throw new RuntimeException("Erro! Tarefa já finalizada anteriormente");
        }

        task.setStatus(TaskEnum.DONE);
        return repository.save(task);
    }

    @Transactional
    public void deleteTaskById(Long id) {
        var task = findByIdOrThrow(id);
        repository.delete(task);
    }

    private TaskEntity findByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task com ID " + id + " não encontrada!"));
    }
}
