package validator;

import game.model.Model;

public interface IValidator {
    void validate(Model model) throws ValidationException;
}
