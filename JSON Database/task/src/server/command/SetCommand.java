package server.command;

import server.database.Database;
import server.database.DatabaseIndex;

import java.util.Map;

public class SetCommand<T> implements Command {
    private Database<T> database;
    private DatabaseIndex<T> index;
    private String value;

    public SetCommand(Database<T> database, DatabaseIndex<T> index, String value) {
        this.database = database;
        this.index = index;
        this.value = value;
    }

    @Override
    public Map<String, String> execute() {
        return database.set(index, value);
    }
}
