package com.cleancode.app.auth.application.usecase;

import com.cleancode.app.auth.domain.model.AuthenticatedUser;
import com.cleancode.app.auth.domain.port.in.GetAuthenticatedUserUseCase;
import com.cleancode.app.auth.domain.port.out.AuthenticatedRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticatedUseCaseImpl implements GetAuthenticatedUserUseCase {

    private final AuthenticatedRepositoryPort authenticatedRepositoryPort;

    @Override
    public AuthenticatedUser getUserInfo(OAuth2User principal, String provider) {
        return authenticatedRepositoryPort.getUserInfo(principal, provider);
    }
}
