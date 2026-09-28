package com.bifani.taskmanagerjava.service;

import com.bifani.taskmanagerjava.database.model.User;
import com.bifani.taskmanagerjava.database.repository.IUserRepository;
import com.bifani.taskmanagerjava.dto.RegisterRequest;
import com.bifani.taskmanagerjava.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    private IUserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthorizationService service;

    @Test
    void registerUser_deveSalvarUsuarioComSenhaCriptografada() {
        var request = new RegisterRequest("Guilherme", "gui@email.com", "123456");
        when(repository.existsByEmail(request.email())).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("hash-bcrypt");

        service.registerUser(request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("gui@email.com");
        assertThat(captor.getValue().getPassword()).isEqualTo("hash-bcrypt");
    }

    @Test
    void registerUser_deveLancarExcecaoQuandoEmailJaCadastrado() {
        var request = new RegisterRequest("Guilherme", "gui@email.com", "123456");
        when(repository.existsByEmail(request.email())).thenReturn(true);

        assertThatThrownBy(() -> service.registerUser(request))
                .isInstanceOf(UserAlreadyExistsException.class);
        verify(repository, never()).save(any());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void loadUserByUsername_deveRetornarUsuarioExistente() {
        var user = User.builder().email("gui@email.com").password("hash").build();
        when(repository.findByEmail("gui@email.com")).thenReturn(user);

        assertThat(service.loadUserByUsername("gui@email.com")).isEqualTo(user);
    }

    @Test
    void loadUserByUsername_deveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(repository.findByEmail("x@email.com")).thenReturn(null);

        assertThatThrownBy(() -> service.loadUserByUsername("x@email.com"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
