package project;

import files.parsers.FlatYaml;
import plugin.PluginLoader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class Initializer {


    public static void main(String[] args) {
        FlatYaml properties = new FlatYaml(getResource("Properties.yml"));

        PluginLoader.loadFromFolder(getResource(properties.getString("Plugins Folder")));
    }

    private static File getResource(String name) {
        try {
            URL url = ClassLoader.getSystemClassLoader().getResource(name);
            Objects.requireNonNull(url, "Resource " + name + " does not exist");
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
