package server.database;

public class ArrayDatabase implements Database {
    private static final Integer DB_SIZE = 1000;
    private String[] database = new String[DB_SIZE];

    @Override
    public String set(Integer index, String value) {
        if (!isInRange(index)) {
            return "ERROR";
        } else {
            database[index] = value;
            return "OK";
        }
    }

    @Override
    public String get(Integer index) {
        if (!isInRange(index) || database[index] == null) {
            return "ERROR";
        } else {
            return database[index];
        }
    }

    @Override
    public String delete(Integer index) {
        if (!isInRange(index)) {
            return "ERROR";
        } else {
            database[index] = null;
            return "OK";
        }
    }

    private static boolean isInRange(Integer index) {
        return index >= 0 && index < DB_SIZE;
    }
}
