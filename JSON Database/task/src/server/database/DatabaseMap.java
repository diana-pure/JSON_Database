package server.database;

import java.util.HashMap;
import java.util.Map;

public class DatabaseMap implements Database<String> {
    private Map<String, String> database = new HashMap<>();

    @Override
    public Response set(Index<String> index, String value) {
        database.put(index.get(), value);
        return new Response("OK", "");
    }

    @Override
    public Response get(Index<String> index) {
        if (database.containsKey(index.get())) {
            return new Response("OK", database.get(index.get()));
        }
        return new Response("ERROR", "No such key");
    }

    @Override
    public Response delete(Index<String> index) {
        if (database.containsKey(index.get())) {
            database.remove(index.get());
            return new Response("OK", "");
        }
        return new Response("ERROR", "No such key");
    }
}
