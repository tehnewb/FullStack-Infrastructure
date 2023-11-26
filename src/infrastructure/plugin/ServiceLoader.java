package infrastructure.plugin;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * A generic service loader for dynamically loading and instantiating classes from a specified package
 * that implement a given interface or extend a specified class.
 *
 * @param <T> The type of the service interface or base class that loaded classes should implement/extend.
 */
public class ServiceLoader<T> implements Iterable<T> {

    // List to store instances of loaded services.
    private final List<T> serviceInstances = new ArrayList<>();

    /**
     * Constructs a ServiceLoader for loading classes from a specific package that implement or extend the
     * specified service type.
     *
     * @param packageName The name of the package to scan for classes.
     * @param type        The interface or base class type that loaded classes should implement or extend.
     */
    public ServiceLoader(String packageName, Class<T> type) {
        try {
            // Get a list of classes in the specified package.
            List<Class<?>> classes = getClassesInPackage(packageName);

            for (Class<?> clazz : classes) {
                // Check if the class is assignable to the specified service type and not an interface.
                if (type.isAssignableFrom(clazz) && !clazz.isInterface()) {
                    try {
                        // Attempt to create an instance of the class using its default constructor.
                        Constructor<?> constructor = clazz.getDeclaredConstructor();
                        T serviceInstance = type.cast(constructor.newInstance());
                        serviceInstances.add(serviceInstance);
                    } catch (NoSuchMethodException | InstantiationException |
                             IllegalAccessException | InvocationTargetException e) {
                        // If instantiation fails, log an error.
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            // If any exceptions occur during class loading, log an error.
            e.printStackTrace();
        }
    }

    /**
     * Retrieve a list of classes within the specified package.
     *
     * @param packageName The name of the package to scan for classes.
     * @return A list of classes found in the package.
     */
    private List<Class<?>> getClassesInPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {
            // Locate the directory containing class files for the specified package.
            File packageDirectory = new File(Objects.requireNonNull(classLoader.getResource(packagePath)).toURI());
            if (packageDirectory.isDirectory()) {
                for (File file : Objects.requireNonNull(packageDirectory.listFiles())) {
                    // Check if the file is a class file.
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        // Build the fully-qualified class name and load the class.
                        String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                        Class<?> clazz = Class.forName(className);
                        classes.add(clazz);
                    }
                }
            }
        } catch (Exception e) {
            // If any exceptions occur during class discovery, log an error.
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * Provides an iterator for iterating over loaded service instances.
     *
     * @return An iterator for the loaded service instances.
     */
    @Override
    public @NotNull Iterator<T> iterator() {
        return serviceInstances.iterator();
    }
}