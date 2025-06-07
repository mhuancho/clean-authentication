package com.cleancode.app.auth.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticatedUserTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String login = "user123";
        String name = "John Doe";
        String picture = "http://example.com/image.jpg";
        String email = "john.doe@example.com";

        // Act
        AuthenticatedUser user = new AuthenticatedUser(login, name, picture, email);

        // Assert
        assertEquals(login, user.getLogin());
        assertEquals(name, user.getName());
        assertEquals(picture, user.getPicture());
        assertEquals(email, user.getEmail());
    }

    @Test
    void testSetters() {
        // Arrange
        AuthenticatedUser user = new AuthenticatedUser(null, null, null, null);

        // Act
        user.setLogin("newLogin");
        user.setName("New Name");
        user.setPicture("http://new.url");
        user.setEmail("new@example.com");

        // Assert
        assertEquals("newLogin", user.getLogin());
        assertEquals("New Name", user.getName());
        assertEquals("http://new.url", user.getPicture());
        assertEquals("new@example.com", user.getEmail());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthenticatedUser user1 = new AuthenticatedUser("u1", "User One", "pic1", "u1@example.com");
        AuthenticatedUser user2 = new AuthenticatedUser("u1", "User One", "pic1", "u1@example.com");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testToString() {
        AuthenticatedUser user = new AuthenticatedUser("u2", "User Two", "pic2", "u2@example.com");
        String result = user.toString();

        assertTrue(result.contains("u2"));
        assertTrue(result.contains("User Two"));
        assertTrue(result.contains("pic2"));
        assertTrue(result.contains("u2@example.com"));
    }
}
