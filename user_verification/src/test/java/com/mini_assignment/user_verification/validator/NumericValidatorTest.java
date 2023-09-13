package com.mini_assignment.user_verification.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumericValidatorTest {

    @Test
    public void itShouldReturnTrueIfValueIsNumeric() {
        Validator validator = new NumericValidator();
        var output = validator.validate("12345567");

        assertTrue(output);
    }

    @Test
    public void itShouldReturnFalseIfValueIsNonNumeric() {
        Validator validator = new NumericValidator();
        var output = validator.validate("1234A5567");

        assertFalse(output);
    }

}
