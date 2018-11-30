package validator.validators;

import game.model.Area;
import game.model.Model;
import validator.IValidator;
import validator.ValidationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AreaIDUniquenessValidator implements IValidator {
    @Override
    public void validate(Model model) throws ValidationException {
        List<Area> areas = model.getListOfAreas().getAreas();

        Set<String> areaIDs = new HashSet<>();
        Set<String> duplicates = new HashSet<>();

        for(Area area : areas) {
            if(areaIDs.contains(area.getId())) {
                duplicates.add(area.getId());
            }
            areaIDs.add(area.getId());
        }

        if(duplicates.size() > 0) {
            String duplicatesString = String.join(", ", duplicates);
            throw new ValidationException(String.format("The following area ids are not unique: %s", duplicatesString));
        }
    }
}
