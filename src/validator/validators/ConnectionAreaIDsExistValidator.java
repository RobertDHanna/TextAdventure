package validator.validators;

import game.model.Area;
import game.model.Connection;
import game.model.Model;
import game.model.Traversable;
import validator.IValidator;
import validator.ValidationException;

import java.util.Set;
import java.util.stream.Collectors;

public class ConnectionAreaIDsExistValidator implements IValidator {
    @Override
    public void validate(Model model) throws ValidationException {
        Set<String> connectionAreaIds = model.getMap().getConnectionList().stream()
            .map(Connection::getAreaId)
            .collect(Collectors.toSet());

        Set<String> areaIds = model.getListOfAreas().getAreas().stream()
            .map(Area::getId)
            .collect(Collectors.toSet());

        connectionAreaIds.removeAll(areaIds);

        if(connectionAreaIds.size() > 0) {
            throw new ValidationException(String.format("The following area IDs are in the connection list, but do not exist in the area list: %s", String.join(", ", connectionAreaIds)));
        }
    }
}
