package validator.validators;

import game.model.Area;
import game.model.Item;
import game.model.ItemList;
import game.model.Model;
import validator.IValidator;
import validator.ValidationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AreaItemIDsAreInItemListValidator implements IValidator {

    @Override
    public void validate(Model model) throws ValidationException {
        ItemList items = model.getItemList();
        List<Area> areas = model.getListOfAreas().getAreas();

        Set<String> itemIDs = areas.stream().map(Area::getItemIds).collect(HashSet::new, Set::addAll, Set::addAll);
        Set<String> badItemIDs = new HashSet<>();

        for(String itemID : itemIDs) {
            if(items.getItem(itemID) == null) {
                badItemIDs.add(itemID);
            }
        }

        if(badItemIDs.size() > 0) {
            throw new ValidationException(String.format("The following itemIDs are in the areas file, but not items: %s", String.join(", ", badItemIDs)));
        }
    }
}
