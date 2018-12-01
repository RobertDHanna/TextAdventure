package validator.validators;

import game.model.Model;
import validator.IValidator;
import validator.ValidationException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// This is somewhat facetious :), but it will also make sure that all of the validators are actually used...
public class AllValidatorsAreUsedValidator implements IValidator {
    private List<IValidator> mValidators;

    public AllValidatorsAreUsedValidator(List<IValidator> validators) {
        mValidators = validators;
    }

    @Override
    public void validate(Model model) throws ValidationException {
        Set<Class> classesUsed = mValidators.stream().map(Object::getClass).collect(Collectors.toSet());

        Class[] allClasses;
        try {
            allClasses = getClasses("validator.validators");
        } catch (IOException | ClassNotFoundException e) {
            throw new ValidationException("Error getting clases in validators package");
        }

        Set<Class> allClassesSet = new HashSet<>(Arrays.asList(allClasses));
        allClassesSet.removeAll(classesUsed);

        if(allClassesSet.size() > 0) {
            throw new ValidationException(String.format("The following validator classes are not used: %s", String.join(", ", allClassesSet.stream().map(Class::getSimpleName).collect(Collectors.toSet()))));
        }
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
