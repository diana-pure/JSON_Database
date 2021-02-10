package server.command;

import server.database.Database;

public class GetCommand implements Command {
    private Database database;
    private Integer index;

    public GetCommand(Database database, Integer index) {
        this.database = database;
        this.index = index;
    }

    @Override
    public String execute() {
        return database.get(index);
    }
}
