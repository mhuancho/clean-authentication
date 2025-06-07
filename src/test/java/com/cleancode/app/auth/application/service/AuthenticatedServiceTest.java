package com.cleancode.app.auth.application.service;

import com.cleancode.app.auth.application.service.AuthenticatedService;
import com.cleancode.app.auth.domain.model.AuthenticatedUser;
import com.cleancode.app.auth.domain.port.in.GetAuthenticatedUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class AuthenticatedServiceTest {

    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private AuthenticatedService authenticatedService;

    private final String loginRedirectUrl = "http://localhost:8080/login";

    @BeforeEach
    void setUp() {
        getAuthenticatedUserUseCase = mock(GetAuthenticatedUserUseCase.class);
        authenticatedService = new AuthenticatedService(getAuthenticatedUserUseCase, loginRedirectUrl);
    }

    @Test
    void getUserInfo_ShouldReturnAuthenticatedUser() {
        OAuth2User principal = mock(OAuth2User.class);
        String provider = "google";

        AuthenticatedUser expectedUser = new AuthenticatedUser("john123", "John", "img.jpg", "john@example.com");
        when(getAuthenticatedUserUseCase.getUserInfo(principal, provider)).thenReturn(expectedUser);

        AuthenticatedUser result = authenticatedService.getUserInfo(principal, provider);

        assertEquals(expectedUser, result);
        verify(getAuthenticatedUserUseCase).getUserInfo(principal, provider);
    }

    @Test
    void getUserInfo_ShouldThrowAccessDeniedException_WhenPrincipalOrProviderIsNull() {
        assertThrows(AccessDeniedException.class, () -> authenticatedService.getUserInfo(null, "google"));
        assertThrows(AccessDeniedException.class, () -> authenticatedService.getUserInfo(mock(OAuth2User.class), null));
    }

    @Test
    void logout_ShouldInvalidateSessionAndRedirect() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getSession()).thenReturn(mock(jakarta.servlet.http.HttpSession.class));

        authenticatedService.logout(request, response);

        verify(request).getSession();
        verify(request).logout();
        verify(response).sendRedirect(loginRedirectUrl);
    }

    @Test
    void buildAuthErrorResponse_ShouldReturnExpectedMap() {
        ResponseEntity<Map<String, Object>> response = authenticatedService.buildAuthErrorResponse();

        assertEquals(UNAUTHORIZED, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("Authentication Error", body.get("error"));
        assertEquals("Hubo un problema con la autenticación.", body.get("message"));
        assertEquals(HttpStatus.UNAUTHORIZED.value(), body.get("status"));
        assertNotNull(body.get("timestamp"));
    }
}
