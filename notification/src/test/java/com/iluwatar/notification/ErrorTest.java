package com.iluwatar.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorTest {

    @Test
    void testGetErrorMessage() {
        Error error = new Error("hello this is a test message");
        String output = error.getErrorMessage();
        assertEquals("hello this is a test message", output);
    }

    @Test
    void testEmptyError() {
        Error error = new Error("");
        String output = error.getErrorMessage();
        assertEquals("", output);
    }
}
