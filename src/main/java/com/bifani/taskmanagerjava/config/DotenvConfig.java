package com.bifani.taskmanagerjava.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DotenvConfig {

    @EventListener
    public void loadEnvFile(ApplicationContextInitializedEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();

        String envPath = ".env";
        File envFile = new File(envPath);

        if (envFile.exists()) {
            try {
                Dotenv dotenv = Dotenv.configure()
                        .directory(".")
                        .filename(".env")
                        .load();

                // Set all .env variables as system properties
                dotenv.entries().forEach(entry -> {
                    System.setProperty(entry.getKey(), entry.getValue());
                });
            } catch (Exception e) {
                System.err.println("Erro ao carregar arquivo .env: " + e.getMessage());
            }
        }
    }
}

