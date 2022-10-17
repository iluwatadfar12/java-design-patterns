package com.iluwatar.daofactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppTest {
    @Test
    void shouldExecuteDaoFactoryWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}