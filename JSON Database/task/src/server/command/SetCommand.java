package server.command;

import server.database.Database;

public class SetCommand implements Command {
    private Database database;
    private Integer index;
    private String value;

    public SetCommand(Database database, Integer index, String value) {
        this.database = database;
        this.index = index;
        this.value = value;
    }

    @Override
    public String execute() {
        return database.set(index, value);
    }
}
