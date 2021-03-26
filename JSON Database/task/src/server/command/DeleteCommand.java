package server.command;

import server.database.Database;
import server.database.Index;
import server.database.Response;

import java.util.Map;

public class DeleteCommand<T> implements Command {
    private Database<T> database;
    private Index<T> index;

    public DeleteCommand(Database<T> database, Index<T> index) {
        this.database = database;
        this.index = index;
    }

    @Override
    public Response execute() {
        return database.delete(index);
    }
}
