package com.bifani.taskmanagerjava.service;

import com.bifani.taskmanagerjava.database.model.TaskEntity;
import com.bifani.taskmanagerjava.database.model.TaskEnum;
import com.bifani.taskmanagerjava.database.model.User;
import com.bifani.taskmanagerjava.database.repository.ITaskRepository;
import com.bifani.taskmanagerjava.database.repository.IUserRepository;
import com.bifani.taskmanagerjava.dto.TaskRequest;
import com.bifani.taskmanagerjava.exception.TaskAlreadyFinishedException;
import com.bifani.taskmanagerjava.exception.TaskNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private ITaskRepository repository;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private TaskService service;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(UUID.randomUUID())
                .name("Guilherme")
                .email("gui@email.com")
                .password("hash")
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities()));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private TaskEntity task(Long id, TaskEnum status) {
        return TaskEntity.builder()
                .id(id)
                .title("Estudar Spring")
                .description("Revisar Spring Security")
                .status(status)
                .user(user)
                .build();
    }

    @Test
    void createTask_deveSalvarComoPendenteVinculadaAoUsuarioLogado() {
        when(repository.save(any(TaskEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        TaskEntity created = service.createTask(new TaskRequest("Estudar Spring", "Revisar Spring Security"));

        ArgumentCaptor<TaskEntity> captor = ArgumentCaptor.forClass(TaskEntity.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(TaskEnum.PENDING);
        assertThat(captor.getValue().getUser()).isEqualTo(user);
        assertThat(created.getTitle()).isEqualTo("Estudar Spring");
    }

    @Test
    void getAllTasks_deveBuscarSomenteTasksDoUsuarioLogado() {
        when(repository.findByUserId(user.getId())).thenReturn(List.of(task(1L, TaskEnum.PENDING)));

        List<TaskEntity> tasks = service.getAllTasks();

        assertThat(tasks).hasSize(1);
        verify(repository).findByUserId(user.getId());
    }

    @Test
    void getTaskById_deveLancarExcecaoQuandoTaskNaoPertenceAoUsuario() {
        when(repository.findByIdAndUserId(99L, user.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getTaskById(99L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void getTaskByTitle_deveRetornarTaskEncontrada() {
        when(repository.findByTitleAndUserId("Estudar Spring", user.getId()))
                .thenReturn(Optional.of(task(1L, TaskEnum.PENDING)));

        assertThat(service.getTaskByTitle("Estudar Spring").getId()).isEqualTo(1L);
    }

    @Test
    void updateTask_deveAlterarTituloEDescricao() {
        TaskEntity existing = task(1L, TaskEnum.PENDING);
        when(repository.findByIdAndUserId(1L, user.getId())).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        TaskEntity updated = service.updateTask(new TaskRequest("Novo título", "Nova descrição"), 1L);

        assertThat(updated.getTitle()).isEqualTo("Novo título");
        assertThat(updated.getDescription()).isEqualTo("Nova descrição");
    }

    @Test
    void updateTaskStatus_deveMarcarTaskPendenteComoConcluida() {
        TaskEntity existing = task(1L, TaskEnum.PENDING);
        when(repository.findByIdAndUserId(1L, user.getId())).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        assertThat(service.updateTaskStatus(1L).getStatus()).isEqualTo(TaskEnum.DONE);
    }

    @Test
    void updateTaskStatus_deveLancarExcecaoQuandoTaskJaFinalizada() {
        when(repository.findByIdAndUserId(1L, user.getId())).thenReturn(Optional.of(task(1L, TaskEnum.DONE)));

        assertThatThrownBy(() -> service.updateTaskStatus(1L))
                .isInstanceOf(TaskAlreadyFinishedException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteTaskById_deveRemoverTaskDoUsuario() {
        TaskEntity existing = task(1L, TaskEnum.PENDING);
        when(repository.findByIdAndUserId(1L, user.getId())).thenReturn(Optional.of(existing));

        service.deleteTaskById(1L);

        verify(repository).delete(existing);
    }

    @Test
    void deleteTaskById_naoDeveRemoverQuandoTaskNaoExiste() {
        when(repository.findByIdAndUserId(1L, user.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteTaskById(1L)).isInstanceOf(TaskNotFoundException.class);
        verify(repository, never()).delete(any());
    }
}
