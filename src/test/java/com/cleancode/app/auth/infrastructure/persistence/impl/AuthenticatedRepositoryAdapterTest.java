package com.cleancode.app.auth.infrastructure.persistence.impl;

import com.cleancode.app.auth.domain.model.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuthenticatedRepositoryAdapterTest {

    private AuthenticatedRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new AuthenticatedRepositoryAdapter();
    }

    @Test
    void getUserInfo_ShouldReturnGithubUser() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("login", "devGit");
        attributes.put("name", "Dev Git");
        attributes.put("avatar_url", "https://github.com/avatar.png");
        attributes.put("email", "devgit@example.com");

        OAuth2User principal = mockOAuth2User(attributes);

        AuthenticatedUser user = adapter.getUserInfo(principal, "github");

        assertEquals("devGit", user.getLogin());
        assertEquals("Dev Git", user.getName());
        assertEquals("https://github.com/avatar.png", user.getPicture());
        assertEquals("devgit@example.com", user.getEmail());
    }

    @Test
    void getUserInfo_ShouldReturnGoogleUser() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("email", "googleuser@example.com");
        attributes.put("name", "Google User");
        attributes.put("picture", "https://google.com/picture.jpg");

        OAuth2User principal = mockOAuth2User(attributes);

        AuthenticatedUser user = adapter.getUserInfo(principal, "google");

        assertEquals("googleuser@example.com", user.getLogin());
        assertEquals("Google User", user.getName());
        assertEquals("https://google.com/picture.jpg", user.getPicture());
        assertEquals("googleuser@example.com", user.getEmail());
    }

    @Test
    void getUserInfo_ShouldReturnFallbackUser() {
        Map<String, Object> attributes = new HashMap<>();

        OAuth2User principal = mockOAuth2User(attributes);

        AuthenticatedUser user = adapter.getUserInfo(principal, "facebook");

        assertEquals("unknown", user.getLogin());
        assertEquals("unknown", user.getName());
        assertNull(user.getPicture());
        assertNull(user.getEmail());
    }

    private OAuth2User mockOAuth2User(Map<String, Object> attributes) {
        OAuth2User principal = Mockito.mock(OAuth2User.class);
        Mockito.when(principal.getAttributes()).thenReturn(attributes);
        return principal;
    }
}
