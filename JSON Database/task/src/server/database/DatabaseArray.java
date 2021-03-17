package server.database;

import java.util.Map;

public class DatabaseArray implements Database<Integer> {
    private static final Integer DB_SIZE = 1000;
    private String[] database = new String[DB_SIZE];

    @Override
    public Map<String, String> set(DatabaseIndex<Integer> index, String value) {
        if (!isInRange(index)) {
            return Map.of("ERROR", "No such key");
        } else {
            database[index.get()] = value;
            return Map.of("OK", "");
        }
    }

    @Override
    public Map<String, String> get(DatabaseIndex<Integer> index) {
        if (!isInRange(index) || database[index.get()] == null) {
            return Map.of("ERROR", "No such key");
        } else {
            return Map.of("OK", database[index.get()]);
        }
    }

    @Override
    public Map<String, String> delete(DatabaseIndex<Integer> index) {
        if (!isInRange(index)) {
            return Map.of("ERROR", "No such key");
        } else {
            database[index.get()] = null;
            return Map.of("OK", "");
        }
    }

    private static boolean isInRange(DatabaseIndex<Integer> index) {
        return index.get() >= 0 && index.get() < DB_SIZE;
    }
}
