package server.database;

import java.util.HashMap;
import java.util.Map;

public class DatabaseMap implements Database<String> {
    private Map<String, String> database = new HashMap<>();

    @Override
    public Map<String, String> set(DatabaseIndex<String> index, String value) {
        database.put(index.get(), value);
        return Map.of("OK", "");
    }

    @Override
    public Map<String, String> get(DatabaseIndex<String> index) {
        if (database.containsKey(index.get())) {
            return Map.of("OK", database.get(index.get()));
        }
        return Map.of("ERROR", "No such key");
    }

    @Override
    public Map<String, String> delete(DatabaseIndex<String> index) {
        if (database.containsKey(index.get())) {
            database.remove(index.get());
            return Map.of("OK", "");
        }
        return Map.of("ERROR", "No such key");
    }
}
