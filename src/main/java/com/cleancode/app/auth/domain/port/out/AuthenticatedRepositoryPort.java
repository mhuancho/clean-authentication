package com.cleancode.app.auth.domain.port.out;

import com.cleancode.app.auth.domain.model.AuthenticatedUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticatedRepositoryPort {
    AuthenticatedUser getUserInfo(OAuth2User principal, String provider);
}