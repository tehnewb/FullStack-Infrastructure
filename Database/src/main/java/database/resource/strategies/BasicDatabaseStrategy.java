package database.resource.strategies;

import database.resource.DatabaseStrategy;
import files.parsers.FlatYaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BasicDatabaseStrategy implements DatabaseStrategy<String, Object> {

    private static final String PATH = "./basic/database.yml";
    private FlatYaml data;

    private void ensureLoaded() {
        if (data == null) {
            try {
                data = new FlatYaml(Files.readString(Paths.get(PATH)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Object load(String key) {
        ensureLoaded();
        return data.get(key);
    }

    @Override
    public void save(String key) {
        data.save(new File(PATH));
    }

    @Override
    public Object get(String key) {
        ensureLoaded();
        return data.get(key);
    }

    @Override
    public Object release(String key) {
        return data.put(key, null);
    }
}
