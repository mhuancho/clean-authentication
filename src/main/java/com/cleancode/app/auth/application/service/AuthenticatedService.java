package com.cleancode.app.auth.application.service;

import com.cleancode.app.auth.domain.model.AuthenticatedUser;
import com.cleancode.app.auth.domain.port.in.GetAuthenticatedUserUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.cleancode.app.auth.domain.constants.Constants.ERROR_AUTH;
import static com.cleancode.app.auth.domain.constants.Constants.ERROR_USER_AUTH;
import static com.cleancode.app.auth.domain.constants.Constants.PROBLEM_AUTH;

@Service
public class AuthenticatedService {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final String loginRedirectUrl;

    public AuthenticatedService(GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
                                @Value("${app.oauth2.login-redirect-url}") String loginRedirectUrl) {
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.loginRedirectUrl = loginRedirectUrl;
    }
    public AuthenticatedUser getUserInfo(OAuth2User principal, String provider) {
        if (principal == null || provider == null) {
            throw new AccessDeniedException(ERROR_USER_AUTH);
        }
        return getAuthenticatedUserUseCase.getUserInfo(principal, provider);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().invalidate();
        request.logout();
        response.sendRedirect(loginRedirectUrl);
    }

    public ResponseEntity<Map<String, Object>> buildAuthErrorResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ERROR_AUTH);
        response.put("message", PROBLEM_AUTH);
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.UNAUTHORIZED.value());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
