package com.cleancode.app.auth.infrastructure.api.controller;

import com.cleancode.app.auth.application.service.AuthenticatedService;
import com.cleancode.app.auth.domain.model.AuthenticatedUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticatedService authenticatedService;

    @GetMapping("/success")
    public ResponseEntity<AuthenticatedUser> loginSuccess(
            @AuthenticationPrincipal OAuth2User principal,
            OAuth2AuthenticationToken token
    ) {
        String provider = token.getAuthorizedClientRegistrationId();
        return ResponseEntity.ok(authenticatedService.getUserInfo(principal, provider));
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        authenticatedService.logout(request, response);
    }

    @GetMapping("/error")
    public ResponseEntity<Map<String, Object>> authError() {
        return authenticatedService.buildAuthErrorResponse();
    }

}
