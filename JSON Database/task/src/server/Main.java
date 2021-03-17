package server;

import com.google.gson.Gson;
import server.command.CommandExecutor;
import server.command.DeleteCommand;
import server.command.GetCommand;
import server.command.SetCommand;
import server.database.Database;
import server.database.DatabaseIndex;
import server.database.DatabaseMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    public static void main(String[] args) {
        Database<String> database = new DatabaseMap();
        CommandExecutor executor = new CommandExecutor();

        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            print("Server started!");

            do {
                Socket socket = server.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                String messageReceived = input.readUTF();
                Map<String, String> requestData = new Gson().fromJson(messageReceived, Map.class);
                print("Received: " + messageReceived);

                String command = requestData.get("type");
                DatabaseIndex<String> index = new DatabaseIndex<>(requestData.getOrDefault("key", null));
                String value = requestData.getOrDefault("value", null);
                String messageToSend;

                switch (command) {
                    case "set":
                        executor.setCommand(new SetCommand<>(database, index, value));
                        break;
                    case "get":
                        executor.setCommand(new GetCommand<>(database, index));
                        break;
                    case "delete":
                        executor.setCommand(new DeleteCommand<>(database, index));
                        break;
                    case "exit":
                        messageToSend = new Gson().toJson(Map.of("response", "OK"));
                        send(output, messageToSend);
                        print("Sent: " + messageToSend);
                        return;
                }
                Map<String, String> respData = executor.executeCommand();
                Map<String, String> r = new LinkedHashMap<>();
                if (respData.containsKey("ERROR")) {
                    r.put("response", "ERROR");
                    r.put("reason", respData.get("ERROR"));
                } else if (respData.containsKey("OK")) {
                    r.put("response", "OK");
                    if (!respData.get("OK").isEmpty()) {
                        r.put("value", respData.get("OK"));
                    }
                }
                messageToSend = new Gson().toJson(r);
                send(output, messageToSend);
                print("Sent: " + messageToSend);
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void print(String s) {
        System.out.println(s);
    }

    private static void send(DataOutputStream output, String s) {
        try {
            output.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
