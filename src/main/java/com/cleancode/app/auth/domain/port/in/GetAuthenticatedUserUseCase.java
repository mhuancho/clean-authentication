package com.cleancode.app.auth.domain.port.in;

import com.cleancode.app.auth.domain.model.AuthenticatedUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public interface GetAuthenticatedUserUseCase {
    AuthenticatedUser getUserInfo(OAuth2User principal, String provider);
}