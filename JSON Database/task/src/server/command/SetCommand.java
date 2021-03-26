package server.command;

import server.database.Database;
import server.database.Index;
import server.database.Response;

public class SetCommand<T> implements Command {
    private Database<T> database;
    private Index<T> index;
    private String value;

    public SetCommand(Database<T> database, Index<T> index, String value) {
        this.database = database;
        this.index = index;
        this.value = value;
    }

    @Override
    public Response execute() {
        return database.set(index, value);
    }
}
