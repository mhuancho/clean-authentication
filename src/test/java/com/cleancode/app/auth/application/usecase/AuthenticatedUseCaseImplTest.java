package com.cleancode.app.auth.application.usecase;

import com.cleancode.app.auth.application.usecase.AuthenticatedUseCaseImpl;
import com.cleancode.app.auth.domain.model.AuthenticatedUser;
import com.cleancode.app.auth.domain.port.out.AuthenticatedRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.OAuth2User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

class AuthenticatedUseCaseImplTest {

    private AuthenticatedRepositoryPort authenticatedRepositoryPort;
    private AuthenticatedUseCaseImpl authenticatedUseCase;

    @BeforeEach
    void setUp() {
        authenticatedRepositoryPort = mock(AuthenticatedRepositoryPort.class);
        authenticatedUseCase = new AuthenticatedUseCaseImpl(authenticatedRepositoryPort);
    }

    @Test
    void testGetUserInfo() {
        // Arrange
        OAuth2User principal = mock(OAuth2User.class);
        String provider = "google";

        AuthenticatedUser expectedUser = new AuthenticatedUser(
                "login123", "John Doe", "http://image.url", "john@example.com"
        );

        when(authenticatedRepositoryPort.getUserInfo(principal, provider)).thenReturn(expectedUser);

        // Act
        AuthenticatedUser result = authenticatedUseCase.getUserInfo(principal, provider);

        // Assert
        assertEquals(expectedUser, result);
        verify(authenticatedRepositoryPort, times(1)).getUserInfo(principal, provider);
    }
}
