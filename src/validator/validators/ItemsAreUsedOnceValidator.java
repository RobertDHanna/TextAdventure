package validator.validators;

import game.model.Area;
import game.model.Model;
import validator.IValidator;
import validator.ValidationException;

import java.util.HashSet;
import java.util.Set;

// Right now this gives an error because of "drink" being repeated.  Since I'm not sure if this
// is a problem, so I'll leave it for now....
public class ItemsAreUsedOnceValidator implements IValidator {

    @Override
    public void validate(Model model) throws ValidationException {
        Set<String> usedOnce = new HashSet<>();
        Set<String> usedTwice = new HashSet<>();

        for(Area area : model.getListOfAreas().getAreas()) {
            for(String itemId : area.getItemIds()) {
                if(usedOnce.contains(itemId)) {
                    usedTwice.add(itemId);
                }
                usedOnce.add(itemId);
            }
        }

        if(usedTwice.size() > 0) {
            throw new ValidationException(String.format("The following item ids are used more than once in the areas: %s", String.join(", ", usedTwice)));
        }
    }
}
