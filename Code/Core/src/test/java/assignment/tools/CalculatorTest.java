package assignment.tools;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by mrnim on 23-Dec-16.
 */
class CalculatorTest {
    @Test
    void test1() {
        Calculator.test();
    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void divide() {
        Integer divide = Calculator.divide(10, 1);

        assertEquals(new Integer(10), divide);
    }

    @Test
    void divide2() {
        assertThrows(ArithmeticException.class, () ->
        {
            Calculator.divide(10, 0);
        });
    }
}