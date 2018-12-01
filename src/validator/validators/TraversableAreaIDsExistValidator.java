package validator.validators;

import game.model.Area;
import game.model.Connection;
import game.model.Model;
import game.model.Traversable;
import validator.IValidator;
import validator.ValidationException;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class TraversableAreaIDsExistValidator implements IValidator {
    @Override
    public void validate(Model model) throws ValidationException {
        Set<String> traversableAreaIDs = model.getMap().getConnectionList().stream()
            .map(Connection::getTraversables)
            .flatMap(Collection::stream)
            .map(Traversable::getToAreaId)
            .collect(Collectors.toSet());

        Set<String> areaIDs = model.getListOfAreas().getAreas().stream()
            .map(Area::getId)
            .collect(Collectors.toSet());

        traversableAreaIDs.removeAll(areaIDs);

        if(traversableAreaIDs.size() > 0) {
            throw new ValidationException(String.format("The following traversable `toAreaId`s don't exist in the areas file: %s", String.join(", ", traversableAreaIDs)));
        }
    }
}
