package server.command;

import server.database.Response;

public interface Command {
    Response execute();
}
