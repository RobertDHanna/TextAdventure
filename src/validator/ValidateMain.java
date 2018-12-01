package validator;

import game.model.Model;
import validator.validators.*;

import java.util.*;

public class ValidateMain {
    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("map file name (including .json): ");
        String map = scanner.nextLine();
        System.out.print("areas file name (including .json): ");
        String areas = scanner.nextLine();
        System.out.print("items file name (including .json): ");
        String items = scanner.nextLine();


        List<IValidator> validators = new ArrayList<>();

        // add additional validators
        validators.add(new AreaIDUniquenessValidator());
        validators.add(new AreaItemIDsAreInItemListValidator());
        validators.add(new BeginAndEndAreaIDsExistValidator());
        validators.add(new ItemIDUniquenessValidator());
        validators.add(new ItemsAreUsedOnceValidator());

        Model model = new Model(map, areas, items);
        ValidateMain validator = new ValidateMain(validators);
        Map<Class<? extends IValidator>, ValidationException> failures = validator.run(model);

        ValidateMain.reportFailures(failures);
    }

    private List<IValidator> mValidators;
    private ValidateMain(List<IValidator> validators) {
        mValidators = validators;
    }

    private Map<Class<? extends IValidator>, ValidationException> run(Model model) {
        Map<Class<? extends IValidator>, ValidationException> failures = new HashMap<>();
        for(IValidator validator : mValidators) {
            try {
                validator.validate(model);
            } catch (ValidationException e) {
                failures.put(validator.getClass(), e);
            }
        }
        return failures;
    }

    private static void reportFailures(Map<Class<? extends IValidator>, ValidationException> failures) {
        if(failures.size() == 0) {
            System.out.println("All validators passed!");
        } else {
            for (Class c : failures.keySet()) {
                System.err.format("%s: %s%n", c.getName(), failures.get(c).getMessage());
            }
        }
    }
}
