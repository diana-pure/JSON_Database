package server.command;

import server.database.Response;

public class ExitCommand implements Command {

    @Override
    public Response execute() {
        return new Response("OK", "");
    }
}
