package server.command;

import server.database.Response;

public class CommandExecutor {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public Response executeCommand() {
        return command.execute();
    }
}
