package server.database;

public class Row {
    private final Index<String> key;
    private final String value;

    public Row(Index<String> key, String value) {
        this.key = key;
        this.value = value;
    }

    public Index<String> getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
