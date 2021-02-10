package server.command;

public class CommandExecutor {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public String executeCommand() {
        return command.execute();
    }
}
