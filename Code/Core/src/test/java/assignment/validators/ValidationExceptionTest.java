package assignment.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
class ValidationExceptionTest {
    private ValidationException validationException;
    @BeforeEach
    void setUp() {
        validationException = new ValidationException("A", "B");
    }

    @Test
    void getContext() {
        assertEquals("B", validationException.getContext());
    }

    @Test
    void toStringTest() {
        assertEquals("A B", validationException.toString());
    }

}