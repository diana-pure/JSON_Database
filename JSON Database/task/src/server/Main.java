package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String[] database = new String[1000];
        String address = "127.0.0.1";
        int port = 23456;

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
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
                        if (!isInRange(index)) {
                            messageToSend = "ERROR";
                        } else {
                            database[index] = value;
                            messageToSend = "OK";
                        }
                        send(output, messageToSend);
                        print("Sent: " + messageToSend);
                        break;
                    case "get":
                        if (!isInRange(index) || database[index] == null) {
                            messageToSend = "ERROR";
                        } else {
                            messageToSend = database[index];
                        }
                        send(output, messageToSend);
                        print("Sent: " + messageToSend);
                        break;
                    case "delete":
                        if (!isInRange(index)) {
                            messageToSend = "ERROR";
                        } else {
                            database[index] = null;
                            messageToSend = "OK";
                        }
                        send(output, messageToSend);
                        print("Sent: " + messageToSend);
                        break;
                    case "exit":
                        messageToSend = "OK";
                        send(output, messageToSend);
                        print("Sent: " + messageToSend);
                        return;
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isInRange(Integer index) {
        return index >= 0 && index < 1000;
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
