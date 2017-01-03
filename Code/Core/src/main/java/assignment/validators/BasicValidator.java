package assignment.validators;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public final class BasicValidator {
    public static <T extends Number & Comparable<T>> void shouldBeGreaterThan(T limit, T value, String context) throws ValidationException {
        BasicValidator.shouldNotBeNull(value, context);
        if (limit.compareTo(value) >= 0) {
            throw new ValidationException(String.format("Value should be greater than limit."), context);
        }
    }

    public static void shouldBeTrue(Boolean value, String context) throws ValidationException {
        if (!value) {
            throw new ValidationException(String.format("Value should be true."), context);
        }
    }

    public static <T> void shouldBeEqual(T expected, T value, String context) throws ValidationException {
        if (!expected.equals(value)) {
            throw new ValidationException(String.format("Values are not equal."), context);
        }
    }

    public static void shouldBeEqual(Double expected, Double value, Double delta, String context) throws ValidationException {
        if (Math.abs(expected - value) > delta) {
            throw new ValidationException(String.format("Values are not equal. Expected %f, actual %f, delta %f.", expected, value, delta), context);
        }
    }

    public static <T extends Number & Comparable<T>> void shouldBeInRange(T min, T max, T value, String context) throws ValidationException {
        BasicValidator.shouldNotBeNull(value, context);
        if (value.compareTo(min) < 0 || value.compareTo(max) > 0) {
            throw new ValidationException(String.format("Should be in range <%d,%d>. Value: %d", min, max, value), context);
        }
    }

    public static <T extends Number & Comparable<T>> void shouldBeInRangeBoundryless(T min, T max, T value, String context) throws ValidationException {
        BasicValidator.shouldNotBeNull(value, context);
        if (value.compareTo(min) <= 0 || value.compareTo(max) >= 0) {
            throw new ValidationException(String.format("Should be in range (%d,%d). Value: %d", min, max, value), context);
        }
    }

    public static void shouldNotBeNull(Object obj, String context) throws ValidationException {
        if (obj == null) {
            throw new ValidationException("Should not be null.", context);
        }
    }

    public static void shouldContainElements(List list, String context) throws ValidationException {
        BasicValidator.shouldNotBeNull(list, context);
        if (list.size() == 0) {
            throw new ValidationException(String.format("List should contain elements."), context);
        }
    }

    public static <T> void shouldBeOneOf(List<T> set, T value, String context) throws ValidationException {
        BasicValidator.shouldNotBeNull(value, context);
        if (!set.stream().anyMatch(x -> x.equals(value))) {

            String msg = "Value " + value + "should be in a set: ";
            for (int i = 0; i < set.size() - 1; i++) {
                msg += set.get(i) + ",";
            }
            msg += set.get(set.size() - 1) + ".";

            throw new ValidationException(msg, context);
        }
    }

    public static <T> void shouldBeUnique(List<T> list, String context) throws ValidationException {
        shouldNotBeNull(list, context);
        Set<T> set = new HashSet<T>(list);
        if (list.size() > set.size()) {
            throw new ValidationException(String.format("List should be unique."), context);
        }
    }
}
