package assignment.configurations.simulation.interfaces;

import assignment.configurations.simulation.exceptions.ValidationException;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public interface IValidate {
    void validate() throws ValidationException;
}
