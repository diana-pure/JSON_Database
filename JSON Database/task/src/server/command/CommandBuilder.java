package server.command;

import server.database.Database;
import server.database.Index;

import java.util.Map;

public class CommandBuilder {
    public static Command get(Map<String, String> requestData, Database database) {
        String command = requestData.get("type");
        Index<String> index = new Index<>(requestData.getOrDefault("key", null));
        String value = requestData.getOrDefault("value", null);

        switch (command) {
            case "set":
                return new SetCommand<>(database, index, value);
            case "get":
                return new GetCommand<>(database, index);
            case "delete":
                return new DeleteCommand<>(database, index);
            case "exit":
                return new ExitCommand();
        }

        return new ExitCommand();
    }
}
