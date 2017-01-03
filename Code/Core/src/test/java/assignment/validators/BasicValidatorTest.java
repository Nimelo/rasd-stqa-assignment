package assignment.validators;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
class BasicValidatorTest {
    @Test
    void shouldBeGreaterThan() throws ValidationException {
        Long value = 120L;
        Long limit = 10L;

        BasicValidator.shouldBeGreaterThan(limit, value, "");

        Throwable throwable = assertThrows(ValidationException.class, () -> {
            BasicValidator.shouldBeGreaterThan(value + 1, value, "");
        });
    }

    @Test
    void shouldBeTrue() throws ValidationException {
        BasicValidator.shouldBeTrue(true, "");
        Throwable throwable = assertThrows(ValidationException.class, () -> {
            BasicValidator.shouldBeTrue(false, "");
        });
    }

    @Test
    void shouldBeEqual() throws ValidationException {
        Long value = 13L;
        BasicValidator.shouldBeEqual(value, value, "");

        assertThrows(ValidationException.class, () -> {
            BasicValidator.shouldBeEqual(value + 1, value, "");
        });
    }

    @Test
    void shouldBeEqual1() throws ValidationException {
        Double value = 13.4D;
        BasicValidator.shouldBeEqual(value - 0.001, value, 0.01, "");

        assertThrows(ValidationException.class, () -> {
            BasicValidator.shouldBeEqual(value + 1, value, "");
        });
    }

    @Test
    void shouldBeInRange() throws ValidationException {
        Long min = -10L;
        Long max = 10L;

        BasicValidator.shouldBeInRange(min, max, 0L, "");
        BasicValidator.shouldBeInRange(min, max, min, "");
        BasicValidator.shouldBeInRange(min, max, max, "");

        assertThrows(ValidationException.class, () -> {
            BasicValidator.shouldBeInRange(min, max, min - 1, "");
        });

        assertThrows(ValidationException.class, () -> {
            BasicValidator.shouldBeInRange(min, max, max + 1, "");
        });
    }

    @Test
    void shouldBeInRangeBoundryless() throws ValidationException {
        Long min = -10L;
        Long max = 10L;

        BasicValidator.shouldBeInRangeBoundryless(min, max, 0L, "");

        assertThrows(ValidationException.class, () -> {
            BasicValidator.shouldBeInRangeBoundryless(min, max, min - 1, "");
        });

        assertThrows(ValidationException.class, () -> {
            BasicValidator.shouldBeInRangeBoundryless(min, max, min, "");
        });

        assertThrows(ValidationException.class, () -> {
            BasicValidator.shouldBeInRangeBoundryless(min, max, max, "");
        });

        assertThrows(ValidationException.class, () -> {
            BasicValidator.shouldBeInRangeBoundryless(min, max, max + 1, "");
        });
    }

    @Test
    void shouldNotBeNull() throws ValidationException {
        BasicValidator.shouldNotBeNull(1L, "");
        assertThrows(ValidationException.class, () -> {
            BasicValidator.shouldNotBeNull(null, "");
        });
    }

    @Test
    void shouldContainElements() throws ValidationException {
        ArrayList<Long> longs = new ArrayList<Long>() {{
            add(new Long(1L));
        }};

        BasicValidator.shouldContainElements(longs, "");

        assertThrows(ValidationException.class, () -> {
            BasicValidator.shouldContainElements(new ArrayList<Long>(), "");
        });
    }

    @Test
    void shouldBeOneOf() throws ValidationException {
        ArrayList<String> strings = new ArrayList<String>() {{
            add("A");
            add("B");
        }};

        BasicValidator.shouldBeOneOf(strings, "A", "");

        assertThrows(ValidationException.class, () -> {
           BasicValidator.shouldBeOneOf(strings, "C", "");
        });
    }

    @Test
    void shouldBeUnique() throws ValidationException {
        ArrayList<String> strings = new ArrayList<String>() {{
            add("A");
            add("B");
        }};

        BasicValidator.shouldBeUnique(strings, "");

        assertThrows(ValidationException.class, () -> {
            strings.add("A");
            BasicValidator.shouldBeUnique(strings, "");
        });
    }

}