package com.cleancode.app.auth.domain.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ConstantsTest {

    @Test
    void testConstantsValues() {
        assertEquals("Usuario no autenticado o proveedor no válido.", Constants.ERROR_USER_AUTH);
        assertEquals("Authentication Error", Constants.ERROR_AUTH);
        assertEquals("Hubo un problema con la autenticación.", Constants.PROBLEM_AUTH);
    }

    @Test
    void testPrivateConstructorThrowsException() throws Exception {
        Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();
        constructor.setAccessible(true); // permite acceso al constructor privado

        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertInstanceOf(UnsupportedOperationException.class, exception.getCause());
        assertEquals("Utility class cannot be instantiated", exception.getCause().getMessage());
    }
}
