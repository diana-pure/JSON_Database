package server;

import com.google.gson.Gson;
import server.command.*;
import server.database.Database;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class RequestHandler implements Runnable {
    private final Database<String> database;
    private final Socket socket;
    private final CommandExecutor executor = new CommandExecutor();
    private DataInputStream input = null;
    private DataOutputStream output = null;

    RequestHandler(Socket socket, Database<String> database) {
        this.socket = socket;
        this.database = database;
    }

    public void run() {
        Map<String, String> requestData;
        try {
            input = new DataInputStream(socket.getInputStream());
            String request = input.readUTF();
            log("Received: " + request);

            requestData = new Gson().fromJson(request, Map.class);
            executor.setCommand(CommandBuilder.get(requestData, database));
            String response = executor.executeCommand().toJsonString();

            output = new DataOutputStream(socket.getOutputStream());
            send(output, response);
            log("Sent: " + response);

            if (requestData.get("type").equals("exit")) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void log(String s) {
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