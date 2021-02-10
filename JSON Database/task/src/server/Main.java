package server;

import server.command.CommandExecutor;
import server.command.DeleteCommand;
import server.command.GetCommand;
import server.command.SetCommand;
import server.database.ArrayDatabase;
import server.database.Database;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    public static void main(String[] args) {
        Database database = new ArrayDatabase();
        CommandExecutor executor = new CommandExecutor();

        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            print("Server started!");

            do {
                Socket socket = server.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                String messageReceived = input.readUTF();
                print("Received: " + messageReceived);
                String[] commandAndParams = messageReceived.split(" ");

                String command = commandAndParams[0];
                Integer index = commandAndParams.length > 1 ? Integer.valueOf(commandAndParams[1]) : null;
                String value = commandAndParams.length > 2 ? Arrays.stream(commandAndParams).skip(2L).collect(Collectors.joining(" ")) : null;
                String messageToSend;

                switch (command) {
                    case "set":
                        executor.setCommand(new SetCommand(database, index, value));
                        break;
                    case "get":
                        executor.setCommand(new GetCommand(database, index));
                        break;
                    case "delete":
                        executor.setCommand(new DeleteCommand(database, index));
                        break;
                    case "exit":
                        messageToSend = "OK";
                        send(output, messageToSend);
                        print("Sent: " + messageToSend);
                        return;
                }
                messageToSend = executor.executeCommand();
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
