package plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.jar.JarFile;

/**
 * The PluginLoader class is responsible for loading external plugins from JAR files.
 * It uses a separate thread pool to load plugins concurrently.
 *
 * @author Albert Beaupre
 */
public class PluginLoader {

    // The folder where plugin JAR files are located
    private final File folder;

    // ExecutorService for managing plugin loading tasks
    private final ExecutorService executor;

    /**
     * Constructs a PluginLoader with the specified folder as the plugin directory.
     *
     * @param folder The folder where plugin JAR files are located.
     */
    public PluginLoader(File folder) {
        this.folder = folder;
        // Create a cached thread pool for loading plugins concurrently
        this.executor = Executors.newCachedThreadPool();
    }

    /**
     * Loads all plugin JAR files found in the specified folder.
     * Each plugin is loaded in a separate thread using the executor.
     */
    public void load() {
        // List all files in the folder that have a .jar extension
        File[] jarFiles = folder.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        if (jarFiles == null) {
            return;
        }

        // Submit a task to load each plugin JAR file
        for (File file : jarFiles) {
            executor.submit(() -> loadPlugin(file));
        }

        // Shutdown the executor after all tasks are submitted
        executor.shutdown();
    }

    /**
     * Loads a plugin JAR file by creating a separate class loader and loading its classes.
     *
     * @param file The plugin JAR file to load.
     */
    private void loadPlugin(File file) {
        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, ClassLoader.getSystemClassLoader())) {
            try (JarFile jar = new JarFile(file)) {
                // Iterate through all entries in the JAR file
                jar.stream()
                        .filter(entry -> entry.getName().endsWith(".class"))
                        .map(entry -> entry.getName().replace("/", ".").replace(".class", ""))
                        .forEach(className -> {
                            try {
                                // Load each class using the plugin's class loader
                                classLoader.loadClass(className);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        });
            }
        } catch (Exception e) {
            // Handle exceptions that may occur during plugin loading
            e.printStackTrace();
        }
    }
}
