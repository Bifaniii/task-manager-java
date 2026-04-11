package com.bifani.taskmanagerjava.service;

import com.bifani.taskmanagerjava.database.model.TaskEntity;
import com.bifani.taskmanagerjava.database.model.TaskEnum;
import com.bifani.taskmanagerjava.database.model.User;
import com.bifani.taskmanagerjava.database.repository.ITaskRepository;
import com.bifani.taskmanagerjava.database.repository.IUserRepository;
import com.bifani.taskmanagerjava.dto.TaskRequest;
import com.bifani.taskmanagerjava.exception.TaskAlreadyFinishedException;
import com.bifani.taskmanagerjava.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final ITaskRepository repository;
    private final IUserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return (User) userRepository.findByEmail(email);
    }

    @Transactional
    public TaskEntity createTask(TaskRequest request) {
        User user = getCurrentUser();
        var task = TaskEntity.builder()
                .title(request.title())
                .description(request.description())
                .status(TaskEnum.PENDING)
                .user(user)
                .build();

        return repository.save(task);
    }

    @Transactional(readOnly = true)
    public List<TaskEntity> getAllTasks() {
        User user = getCurrentUser();
        return repository.findByUserId(user.getId());
    }

    @Transactional(readOnly = true)
    public TaskEntity getTaskByTitle(String title) {
        User user = getCurrentUser();
        return repository.findByTitleAndUserId(title, user.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task com título '" + title + "' não encontrada!"));
    }

    @Transactional(readOnly = true)
    public TaskEntity getTaskById(Long id) {
        User user = getCurrentUser();
        return repository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task com ID " + id + " não encontrada!"));
    }

    @Transactional
    public TaskEntity updateTask(TaskRequest request, Long id) {
        User user = getCurrentUser();
        var task = repository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task com ID " + id + " não encontrada!"));

        task.setTitle(request.title());
        task.setDescription(request.description());

        return repository.save(task);
    }

    @Transactional
    public TaskEntity updateTaskStatus(Long id) {
        User user = getCurrentUser();
        var task = repository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task com ID " + id + " não encontrada!"));

        if (task.getStatus() == TaskEnum.DONE) {
            throw new TaskAlreadyFinishedException("Erro! Tarefa já finalizada anteriormente");
        }

        task.setStatus(TaskEnum.DONE);
        return repository.save(task);
    }

    @Transactional
    public void deleteTaskById(Long id) {
        User user = getCurrentUser();
        var task = repository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task com ID " + id + " não encontrada!"));
        repository.delete(task);
    }
}
