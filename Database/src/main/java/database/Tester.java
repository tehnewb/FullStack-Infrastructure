package database;

import database.server.DatabaseServer;

public class Tester {

    public static void start() {
        DatabaseServer database = new DatabaseServer();
        database.start(8888);
    }
}
