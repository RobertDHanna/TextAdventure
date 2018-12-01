package validator.validators;

import game.model.Connection;
import game.model.Item;
import game.model.Model;
import game.model.Traversable;
import validator.IValidator;
import validator.ValidationException;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class RequiredItemIDsExistValidator implements IValidator {
    @Override
    public void validate(Model model) throws ValidationException {
        Set<String> requiredItemIds = model.getMap().getConnectionList().stream()
            .map(Connection::getTraversables)
            .flatMap(Collection::stream)
            .map(Traversable::getRequiredItemIds)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        System.out.println(String.join(", ", requiredItemIds));

        Set<String> allItemIds = model.getItemList().getItems().stream().map(Item::getId).collect(Collectors.toSet());

        requiredItemIds.removeAll(allItemIds);

        if(requiredItemIds.size() > 0) {
            throw new ValidationException(String.format("The following item ids are required by traversables, but don't exist in the items list: %s", String.join(", ", requiredItemIds)));
        }
    }
}
