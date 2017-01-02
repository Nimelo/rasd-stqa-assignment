package assignment.configurations.simulation.exceptions;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class ValidationException extends Exception {
    private Class clazz;
    public ValidationException(String msg, Class clazz) {
        super(msg);
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
