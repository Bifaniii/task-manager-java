package com.bifani.taskmanagerjava.controller;

import com.bifani.taskmanagerjava.dto.TaskRequest;
import com.bifani.taskmanagerjava.dto.TaskResponse;
import com.bifani.taskmanagerjava.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        var resp = service.getAllTasks().stream()
                .map(TaskResponse::new)
                .toList();

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<TaskResponse> getTaskByTitle(@RequestParam String title) {
        var task = service.getTaskByTitle(title);

        return ResponseEntity.ok(new TaskResponse(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        var task = service.getTaskById(id);

        return ResponseEntity.ok(new TaskResponse(task));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskRequest request) {
        var task = service.createTask(request);
        var response = new TaskResponse(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                   @RequestBody @Valid TaskRequest request) {
        var updatedEntity = service.updateTask(request, id);
        var response = new TaskResponse(updatedEntity);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/done")
    public ResponseEntity<TaskResponse> completeTask(@PathVariable Long id) {
        var updated = service.updateTaskStatus(id);
        return ResponseEntity.ok(new TaskResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        service.deleteTaskById(id);

        return ResponseEntity.noContent().build();
    }
}
