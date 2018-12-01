package validator.validators;

import game.model.Area;
import game.model.Model;
import validator.IValidator;
import validator.ValidationException;

import java.util.Set;
import java.util.stream.Collectors;

public class BeginAndEndAreaIDsExistValidator implements IValidator {
    @Override
    public void validate(Model model) throws ValidationException {
        Set<String> areaIds = model.getListOfAreas().getAreas().stream().map(Area::getId).collect(Collectors.toSet());

        if(!areaIds.contains(model.getMap().getStartAreaId()) && !areaIds.contains(model.getMap().getEndAreaId())) {
            throw new ValidationException(String.format("Start area id '%s' and end area id '%s' don't exist in the areas file.", model.getMap().getStartAreaId(), model.getMap().getEndAreaId()));
        }

        if(!areaIds.contains(model.getMap().getStartAreaId())) {
            throw new ValidationException(String.format("Start area id '%s' doesn't exist in the areas file.", model.getMap().getStartAreaId()));
        }

        if(!areaIds.contains(model.getMap().getEndAreaId())) {
            throw new ValidationException(String.format("End area id '%s' doesn't exist in the areas file.", model.getMap().getEndAreaId()));
        }
    }
}
