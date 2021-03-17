package server.command;

import server.database.Database;
import server.database.DatabaseIndex;

import java.util.Map;

public class DeleteCommand<T> implements Command {
    private Database<T> database;
    private DatabaseIndex<T> index;

    public DeleteCommand(Database<T> database, DatabaseIndex<T> index) {
        this.database = database;
        this.index = index;
    }

    @Override
    public Map<String, String> execute() {
        return database.delete(index);
    }
}
