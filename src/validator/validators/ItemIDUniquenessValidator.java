package validator.validators;

import game.model.Item;
import game.model.Model;
import validator.IValidator;
import validator.ValidationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemIDUniquenessValidator implements IValidator {
    @Override
    public void validate(Model model) throws ValidationException {
        List<Item> items = model.getItemList().getItems();

        Set<String> itemIDs = new HashSet<>();
        Set<String> duplicates = new HashSet<>();

        for(Item item : items) {
            if(itemIDs.contains(item.getId())) {
                duplicates.add(item.getId());
            }
            itemIDs.add(item.getId());
        }

        if(duplicates.size() > 0) {
            String duplicatesString = String.join(", ", duplicates);
            throw new ValidationException(String.format("The following item ids are not unique: %s", duplicatesString));
        }
    }
}
