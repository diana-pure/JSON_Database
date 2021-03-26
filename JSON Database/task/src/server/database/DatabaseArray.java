package server.database;

import java.util.Map;

public class DatabaseArray implements Database<Integer> {
    private static final Integer DB_SIZE = 1000;
    private String[] database = new String[DB_SIZE];

    @Override
    public Response set(Index<Integer> index, String value) {
        if (!isInRange(index)) {
            return new Response("ERROR", "No such key");
        } else {
            database[index.get()] = value;
            return new Response("OK", "");
        }
    }

    @Override
    public Response get(Index<Integer> index) {
        if (!isInRange(index) || database[index.get()] == null) {
            return new Response("ERROR", "No such key");
        } else {
            return new Response("OK", database[index.get()]);
        }
    }

    @Override
    public Response delete(Index<Integer> index) {
        if (!isInRange(index)) {
            return new Response("ERROR", "No such key");
        } else {
            database[index.get()] = null;
            return new Response("OK", "");
        }
    }

    private static boolean isInRange(Index<Integer> index) {
        return index.get() >= 0 && index.get() < DB_SIZE;
    }
}
