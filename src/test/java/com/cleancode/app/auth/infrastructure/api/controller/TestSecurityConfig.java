package com.cleancode.app.auth.infrastructure.api.controller;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**") // Aplica a todos los endpoints
                .authorizeHttpRequests(authz -> authz.anyRequest().permitAll()) // Permite todo
                .securityContext((context) -> context.requireExplicitSave(false))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .anonymous(Customizer.withDefaults()) // Habilita usuarios anónimos
                .httpBasic(Customizer.withDefaults()); // Opcional, por compatibilidad

        return http.build();
    }
}
