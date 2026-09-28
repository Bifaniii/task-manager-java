package com.bifani.taskmanagerjava.controller;

import com.bifani.taskmanagerjava.database.model.TaskEntity;
import com.bifani.taskmanagerjava.database.model.TaskEnum;
import com.bifani.taskmanagerjava.dto.TaskRequest;
import com.bifani.taskmanagerjava.exception.TaskAlreadyFinishedException;
import com.bifani.taskmanagerjava.exception.TaskNotFoundException;
import com.bifani.taskmanagerjava.handler.RestExceptionHandler;
import com.bifani.taskmanagerjava.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService service;

    @InjectMocks
    private TaskController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    private TaskEntity task(TaskEnum status) {
        return TaskEntity.builder()
                .id(1L)
                .title("Estudar Spring")
                .description("Revisar Spring Security")
                .status(status)
                .build();
    }

    @Test
    void getAllTasks_deveRetornar200ComListaDeTasks() throws Exception {
        when(service.getAllTasks()).thenReturn(List.of(task(TaskEnum.PENDING)));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Estudar Spring"));
    }

    @Test
    void createTask_deveRetornar201QuandoRequestValido() throws Exception {
        when(service.createTask(any(TaskRequest.class))).thenReturn(task(TaskEnum.PENDING));

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title": "Estudar Spring", "description": "Revisar Spring Security"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void createTask_deveRetornar400QuandoTituloEmBranco() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title": "", "description": "Revisar Spring Security"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        verifyNoInteractions(service);
    }

    @Test
    void getTaskById_deveRetornar404QuandoTaskNaoExiste() throws Exception {
        when(service.getTaskById(99L)).thenThrow(new TaskNotFoundException("Task com ID 99 não encontrada!"));

        mockMvc.perform(get("/tasks/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task com ID 99 não encontrada!"));
    }

    @Test
    void updateTask_deveRetornar200ComTaskAtualizada() throws Exception {
        when(service.updateTask(any(TaskRequest.class), eq(1L))).thenReturn(task(TaskEnum.PENDING));

        mockMvc.perform(patch("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title": "Estudar Spring", "description": "Revisar Spring Security"}
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void completeTask_deveRetornar400QuandoTaskJaFinalizada() throws Exception {
        when(service.updateTaskStatus(1L)).thenThrow(new TaskAlreadyFinishedException("Erro! Tarefa já finalizada anteriormente"));

        mockMvc.perform(patch("/tasks/1/done"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTaskById_deveRetornar204() throws Exception {
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());

        verify(service).deleteTaskById(1L);
    }
}
