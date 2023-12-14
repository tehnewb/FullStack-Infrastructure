package service;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * A generic service loader for dynamically loading and instantiating classes from a specified package
 * that implement a given interface or extend a specified class.
 *
 * @param <T> The type of the service interface or base class that loaded classes should implement/extend.
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class ServiceLoader<T> implements Iterable<T> {

    private final List<T> serviceInstances = new ArrayList<>(); // List to store instances of loaded services.

    private ServiceLoader() {
        // Inaccessible
    }

    /**
     * Constructs a ServiceLoader for loading classes from a specific package that implement or extend the
     * specified service type.
     *
     * @param packageName The name of the package to scan for classes.
     * @param type        The interface or base class type that loaded classes should implement or extend.
     */
    public static <T> ServiceLoader<T> load(String packageName, Class<T> type) {
        ServiceLoader<T> services = new ServiceLoader<>();
        try {
            String packagePath = packageName.replace('.', '/');
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            // Locate the directory containing class files for the specified package.
            URL resource = classLoader.getResource(packagePath);
            Objects.requireNonNull(resource, "Resource " + packagePath + " does not exist");
            File packageDirectory = new File(resource.toURI());
            if (packageDirectory.isDirectory()) {
                File[] files = packageDirectory.listFiles();
                Objects.requireNonNull(files, "Package does not contain any files");
                for (File file : files) {
                    // Check if the file is a class file.
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        // Build the fully qualified class name and load the class.
                        String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                        Class<?> clazz = Class.forName(className);
                        if (type.isAssignableFrom(clazz) && !clazz.isInterface()) {
                            // Attempt to create an instance of the class using its default constructor.
                            Constructor<?> constructor = clazz.getDeclaredConstructor();
                            services.serviceInstances.add(type.cast(constructor.newInstance()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services;
    }

    /**
     * @return The number of services loaded.
     */
    public int size() {
        return serviceInstances.size();
    }

    /**
     * Provides an iterator for iterating over loaded service instances.
     *
     * @return An iterator for the loaded service instances.
     */
    @Override
    public Iterator<T> iterator() {
        return serviceInstances.iterator();
    }
}