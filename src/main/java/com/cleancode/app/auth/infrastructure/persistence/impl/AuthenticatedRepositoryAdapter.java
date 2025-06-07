package com.cleancode.app.auth.infrastructure.persistence.impl;

import com.cleancode.app.auth.domain.model.AuthenticatedUser;
import com.cleancode.app.auth.domain.port.out.AuthenticatedRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthenticatedRepositoryAdapter implements AuthenticatedRepositoryPort {

    @Override
    public AuthenticatedUser getUserInfo(OAuth2User principal, String provider) {
        Map<String, Object> attributes = principal.getAttributes();

        if ("github".equals(provider)) {
            return new AuthenticatedUser(
                    (String) attributes.get("login"),
                    (String) attributes.get("name"),
                    (String) attributes.get("avatar_url"),
                    (String) attributes.get("email")
            );
        } else if ("google".equals(provider)) {
            return new AuthenticatedUser(
                    (String) attributes.get("email"),
                    (String) attributes.get("name"),
                    (String) attributes.get("picture"),
                    (String) attributes.get("email")
            );
        }
        else {
            // fallback
            return new AuthenticatedUser("unknown", "unknown", null, null);
        }
    }
}
