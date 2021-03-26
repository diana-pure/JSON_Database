package server.command;

import server.database.Database;
import server.database.Index;
import server.database.Response;

public class GetCommand<T> implements Command {
    private Database<T> database;
    private Index<T> index;

    public GetCommand(Database<T> database, Index<T> index) {
        this.database = database;
        this.index = index;
    }

    @Override
    public Response execute() {
        return database.get(index);
    }
}
