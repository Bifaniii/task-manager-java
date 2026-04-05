package com.bifani.taskmanagerjava.service;

import com.bifani.taskmanagerjava.database.model.User;
import com.bifani.taskmanagerjava.database.repository.IUserRepository;
import com.bifani.taskmanagerjava.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }

    @Transactional
    public void registerUser(RegisterRequest data) {
        if (repository.findByEmail(data.email()) != null) {
            throw new RuntimeException("Usuário já cadastrado!");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());

        var newUser = User.builder()
                .name(data.name())
                .email(data.email())
                .password((encryptedPassword))
                .build();

        repository.save(newUser);
    }
}
