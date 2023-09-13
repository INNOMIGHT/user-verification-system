package com.mini_assignment.user_verification.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnglishAlphabetsValidatorTest {

    @Test
    public void itShouldReturnFalseIfTheInputIsNotAlphabetic() {
        Validator validator = new EnglishAlphabetsValidator();
        var output = validator.validate("123456");

        assertFalse(output);
    }

    @Test
    public void itShouldReturnTrueIfInputIsAlphabetic() {
        Validator validator = new EnglishAlphabetsValidator();
        var output = validator.validate("abcd");

        assertTrue(output);
    }

}
