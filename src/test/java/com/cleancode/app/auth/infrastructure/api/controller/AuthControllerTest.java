package com.cleancode.app.auth.infrastructure.api.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.cleancode.app.auth.application.service.AuthenticatedService;
import com.cleancode.app.auth.domain.model.AuthenticatedUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class AuthControllerTest {

    @Mock
    private AuthenticatedService authenticatedService;

    @Mock
    private OAuth2User principal;

    @Mock
    private OAuth2AuthenticationToken token;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loginSuccess_ReturnsAuthenticatedUser() {
        String provider = "google";
        AuthenticatedUser expectedUser = new AuthenticatedUser("login", "name", "picture", "email");

        when(token.getAuthorizedClientRegistrationId()).thenReturn(provider);
        when(authenticatedService.getUserInfo(principal, provider)).thenReturn(expectedUser);

        ResponseEntity<AuthenticatedUser> response = authController.loginSuccess(principal, token);

        assertNotNull(response);
        assertEquals(expectedUser, response.getBody());
    }

    @Test
    public void logout_CallsServiceLogout() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        authController.logout(request, response);

        verify(authenticatedService).logout(request, response);
    }

    @Test
    public void authError_ReturnsErrorResponse() {
        ResponseEntity<Map<String, Object>> expectedResponse = ResponseEntity.badRequest().build();

        when(authenticatedService.buildAuthErrorResponse()).thenReturn(expectedResponse);

        ResponseEntity<Map<String, Object>> response = authController.authError();

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }
}
