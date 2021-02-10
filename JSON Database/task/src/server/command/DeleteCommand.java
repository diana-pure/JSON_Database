package server.command;

import server.database.Database;

public class DeleteCommand implements Command {
    private Database database;
    private Integer index;

    public DeleteCommand(Database database, Integer index) {
        this.database = database;
        this.index = index;
    }

    @Override
    public String execute() {
        return database.delete(index);
    }
}
