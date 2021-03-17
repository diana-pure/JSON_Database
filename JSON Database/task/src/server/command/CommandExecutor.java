package server.command;

import java.util.Map;

public class CommandExecutor {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public Map<String, String> executeCommand() {
        return command.execute();
    }
}
