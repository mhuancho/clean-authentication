package com.cleancode.app.auth.domain.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotAuthenticatedExceptionTest {

    @Test
    void testConstructorStoresMessage() {
        String expectedMessage = "Usuario no autenticado";
        NotAuthenticatedException exception = new NotAuthenticatedException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testIsInstanceOfRuntimeException() {
        NotAuthenticatedException exception = new NotAuthenticatedException("Error");
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testThrowingException() {
        String message = "No estás autenticado";

        RuntimeException thrown = assertThrows(
                NotAuthenticatedException.class,
                () -> { throw new NotAuthenticatedException(message); }
        );

        assertEquals(message, thrown.getMessage());
    }
}

