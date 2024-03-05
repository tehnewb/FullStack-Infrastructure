package database.resource.strategies;

import database.resource.DatabaseStrategy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class AccountDatabaseStrategy implements DatabaseStrategy<String, Account> {

    private static final String PATH = "./accounts/%s.yml";
    private final HashMap<String, Account> accounts = new HashMap<>();

    @Override
    public Account load(String key) {
        try {
            return new Account(Files.readString(Paths.get(PATH.formatted(key))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(String key) {
        accounts.get(key).save(new File(PATH.formatted(key)));
    }

    @Override
    public Account get(String key) {
        return accounts.get(key);
    }

    @Override
    public Account release(String key) {
        return accounts.put(key, null);
    }
}
