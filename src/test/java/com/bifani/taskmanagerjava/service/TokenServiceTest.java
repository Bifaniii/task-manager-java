package com.bifani.taskmanagerjava.service;

import com.bifani.taskmanagerjava.database.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class TokenServiceTest {

    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secret", "segredo-de-teste");
    }

    @Test
    void generateToken_deveGerarTokenCujoSubjectEhOEmail() {
        var user = User.builder().email("gui@email.com").build();

        String token = tokenService.generateToken(user);

        assertThat(tokenService.validateToken(token)).isEqualTo("gui@email.com");
    }

    @Test
    void validateToken_deveRetornarVazioParaTokenInvalido() {
        assertThat(tokenService.validateToken("token.invalido.qualquer")).isEmpty();
    }

    @Test
    void validateToken_deveRejeitarTokenAssinadoComOutroSegredo() {
        var outro = new TokenService();
        ReflectionTestUtils.setField(outro, "secret", "outro-segredo");
        String token = outro.generateToken(User.builder().email("gui@email.com").build());

        assertThat(tokenService.validateToken(token)).isEmpty();
    }

    @Test
    void genExpirationDate_deveExpirarEmDuasHoras() {
        Instant expiration = tokenService.genExpirationDate();

        assertThat(expiration).isBetween(Instant.now().plusSeconds(7190), Instant.now().plusSeconds(7210));
    }
}
