package assignment.validators;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class ValidationException extends Throwable {
    private String context;

    public ValidationException(String message, String context) {
        super(message);
        this.context = context;
    }

    public String getContext() {
        return context;
    }
}
